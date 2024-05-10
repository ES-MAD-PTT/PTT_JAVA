package com.atos.quartz;

import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Component;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.LockBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.WebserviceLogBean;
import com.atos.beans.allocation.CalculateAllocationBalanceBean;
import com.atos.beans.metering.MeteringInputBean;
import com.atos.beans.metering.MeteringProcBean;
import com.atos.client.pmisdwh.meteringQuery.MetQueryWsClient;
import com.atos.exceptions.ValidationException;
import com.atos.filters.metering.MeteringManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.WebServiceInputMapper;
import com.atos.mapper.allocation.AllocationIntradayMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.mapper.metering.MeteringManagementMapper;
import com.atos.services.CRNotificationService;
import com.atos.utils.Constants;
import com.atos.wsdl.pmisdwh.meteringQuery.CdmError;
import com.atos.wsdl.pmisdwh.meteringQuery.CdmRequestHeader;
import com.atos.wsdl.pmisdwh.meteringQuery.CdmResponseHeader;
import com.atos.wsdl.pmisdwh.meteringQuery.DateRange;
import com.atos.wsdl.pmisdwh.meteringQuery.MeteringQueryRequest;
import com.atos.wsdl.pmisdwh.meteringQuery.MeteringQueryResponse;
import com.atos.wsdl.pmisdwh.meteringQuery.ObjectFactory;
import com.atos.wsdl.pmisdwh.meteringQuery.ProcessMessage;
import com.atos.wsdl.pmisdwh.meteringQuery.ProcessMessageCls;
import com.atos.wsdl.pmisdwh.meteringQuery.ProcessMessageResponse;


@Component
public class AllocationAutorunIntradayClient  implements Serializable {
	
	
	private static final long serialVersionUID = -9067325632463736509L;
	private static final Logger log = LogManager.getLogger(AllocationAutorunIntradayClient.class);
	private static final String strNotifSeparator = "~";
	
	private static final String strMeteringMaxDateOffset = "METERING.MAX.DATE.OFFSET";

	private static final String strNotifOriginMet = "METERING";
	private static final String strNotifTypeCodeWsMeteringQueryOK = "METERING.WS.RETRIEVING.FINISH.OK";
	private static final String strNotifTypeCodeWsMeteringQueryError = "METERING.WS.RETRIEVING.FINISH.ERROR";

	private  static final String[] permittedWSErrorCodes = {"ERR-PMISDWH-0000"};
	private static final int iMaxWSRequestDateRange = 5;  // Max. number of days to request to metering webservice. 
	
	
	private static final String strNotifOriginAlloc = "ALLOCATION";
	private static final String strAllocationMaxDateOffset = "ALLOCATION.MAX.DATE.OFFSET";
	private static final String strNotifTypeAllocationExecutionFinishedOK = "ALLOCATION.EXECUTION.FINISHED.OK";
	private static final String strNotifTypeAllocationExecutionFinishedError = "ALLOCATION.EXECUTION.FINISHED.ERROR";
	
	private static final String strAllocationIntradayAllocation = "AUTORUN.INTRADAY.ALLOCATION";

	private static final String strAllocationTypeCommercial = "INTRADAY";
	private static final String strBalanceClosingTypeDefinitive = "DEFINITIVE";
	private static final String strUpdateBalanceY = "Y";

	// Por ser un proceso automatico no va a tener usuario de acceso a la aplicacion. Le ponemos el inicial 'managerAut'.
	private static final String strUser="manager";
	private static final String strLanguage="en";
	
	private Date openPeriodFirstDay = null;
	private Date responsePeriodStartDate = null;
	private Date responsePeriodEndDate = null;
	private BigDecimal idnUserGroup;
	private BigDecimal idnOnshoreSystem;
	
	@Autowired
	private AllocationIntradayMapper amMapper;
	@Autowired
	private NotificationMapper notifMapper;	
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private SystemMapper systemMapper;
	@Autowired
	private MeteringManagementMapper mmMapper;
	@Autowired
	private MetQueryWsClient wsClient;
	@Autowired
	private WebServiceInputMapper wsMapper;



	 @Autowired
	 @ManagedProperty("#{crNotificationService}")
	 transient private CRNotificationService serviceNotif;
	 
	public void setServiceNotif(CRNotificationService serviceNotif) {
		this.serviceNotif = serviceNotif;
	}
	

	public void callMeteringIntradayManagementRequestClient() throws Exception{
		System.out.println("Job callMeteringIntradayManagementRequestClient is runing");
		log.info("Job callMeteringIntradayManagementRequestClient is runing", Calendar.getInstance().getTime());

		
//		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		MeteringManagementFilter filter= new MeteringManagementFilter();
    	boolean bTakeLock = false;
		
		Calendar hoy = Calendar.getInstance();
		filter.setGasDayFrom(hoy.getTime());
		// updateMetsfilters.setGasDayFrom(openPeriodFistDay);
		filter.setGasDayTo(Calendar.getInstance().getTime());

        try {
		
    	// 1.- Se validan los parametros de entrada.
    	validateRequest(filter, strUser, strLanguage);
    	
    	// 2.- Se comprueba si el proceso de actualizacion de medidas ya se ha lanzado
    	// actualmente por otro usuario. Para ello se consulta una tabla de bloqueos en BD.
    	bTakeLock = takeLockMeteringQuery(strUser);
    	
    	// Si en esta peticion no se pudo coger el bloqueo, se lanza una excepcion para enviar un mensaje de error al usuario.
    	if(!bTakeLock) {
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    		String notifInfo = sdf.format(hoy.getTime()) + strNotifSeparator + "Error: There is another metering process in progress. Please, try it later." + " Intraday";	  
    		sendNotification(strNotifTypeCodeWsMeteringQueryError, notifInfo);
    		throw new ValidationException("Error: There is another metering process in progress. Please, try it later.");
    	}
    	
    	updateMeasurementsIntradayFromWebserviceTask();
        }   
        catch (TaskRejectedException tre) {	// Excepcion para el caso de que no se pueda generar un thread porque se ha alcanzado el maximo numero de threads.
        			// En caso de error, se ha de liberar el bloqueo.
					// En caso de ok, el bloqueo se libera en el thread.
			// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
			if(bTakeLock) {
				// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
				if(!releaseLockMeteringQuery(strUser))
					log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
			}
			
			log.error(tre.getMessage(), tre);
			throw new ValidationException("Error: It's not possible to execute the request. The maximum number of asynchronous processes has been reached.");
		}        
        catch (Exception e) {	// En caso de error, se ha de liberar el bloqueo.
        						// En caso de ok, el bloqueo se libera en el thread.
        	// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
        	if(bTakeLock) {
	        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
	            if(!releaseLockMeteringQuery(strUser))
	    	    	log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
        	}
        	
        	throw e;
        }
	}


	public void  updateMeasurementsIntradayFromWebserviceTask() {
		
		
		//buscamos el flag que nos indique si enviamos notificaciones o no...
/*		String execution_enable="N";
		try {
			execution_enable = getSystemParameterString(strNotifTypeCodeWsMeteringManagementAutorun, strUser, strLanguage);
		} catch (Exception e) {
	    	log.error(e.getMessage(), e);
		}
		if(!execution_enable.equals("Y")) {
			System.out.println("Job callMeteringManagementRequestClient is disable");
			log.info("Job callMeteringManagementRequestClient is disable", Calendar.getInstance().getTime());
			return;
		}
		
	*/	
		
	//	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		
    	WebserviceLogBean wsLogBean = null;
    	MeteringInputBean metInBean = null;
    	Date startUpdatingDate = new Date();
    	String newMetInputCode = null;
    	Calendar cal_gasDayFrom = Calendar.getInstance();
    	BigDecimal count = wsMapper.checkIntradayExecution(this.idnOnshoreSystem);
    	if(count.intValue()==0) {
    		cal_gasDayFrom.add(Calendar.DAY_OF_YEAR, -1);
    	}
    	
    	
    	Calendar cal_gasDayTo = Calendar.getInstance();

    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	String notifInfo = sdf.format(cal_gasDayFrom.getTime()) + strNotifSeparator +
				sdf.format(cal_gasDayTo.getTime()) + strNotifSeparator +
				sdf2.format(startUpdatingDate);	    	
        try {
        	log.debug("[" + notifInfo + "] - UpdateMeasurementsFromWebserviceTask - run() start.");

        	// 2.5.- Se obtiene un nuevo codigo de metering_input, para guardarlo en la tabla y incluirlo en notificaciones.
	    	newMetInputCode = getNewMeteringInputCode();
	    	log.debug("[" + notifInfo + "] - getNewMeteringInputCode() finished: " + newMetInputCode );
	    	
        	// 3.- Se obtienen medidas del webservice.
        	// 3.1.- Se prepara la peticion.
        	ProcessMessage procMessage = prepareWsRequest(cal_gasDayFrom.getTime(),cal_gasDayTo.getTime());
        	log.debug("[" + notifInfo + "] - prepareWsRequest() finished.");
        	// 3.2.- Se guarda log de la peticion al webservice en BD.
        	wsLogBean = wsLogInBDRequest(startUpdatingDate, procMessage);
        	log.debug("[" + notifInfo + "] - wsLogInBDRequest() finished.");
        	// 3.3.- Se invoca al webservice.
	        ProcessMessageResponse response = wsClient.getData(procMessage);
        	log.debug("[" + notifInfo + "] - wsClient.getData() finished.");
	        
	        if(isOKResult(response)) {
	        	// 3.4.- Se guarda log de la respuesta al webservice en BD.
	        	wsLogBean.setStatus(Constants.OK_S);
	        	wsLogInBDResponse(wsLogBean, response);
	        	log.debug("[" + notifInfo + "] - wsLogInBDResponse() OK finished.");
	        	// 4.- Se actualiza en BD.
	        	// 4.1.- Se guarda la respuesta XML.
	        	metInBean = saveInBDOKResponse(newMetInputCode, wsLogBean, response);
	        	log.debug("[" + notifInfo + "] - saveInBDResponse() finished.");
	        	// 4.2.- Se actualizan tablas.
				MeteringProcBean mpBean = new MeteringProcBean();
				mpBean.setMeteringInputId(metInBean.getMeteringInputId());
	        	meteringSaveInBD(mpBean);
	        	log.debug("[" + notifInfo + "] - meteringSaveInBD(), DB procedure, finished.");
	        	
	        	// 5.- Se envia notificacion al usuario.
		    	notifInfo += strNotifSeparator + "Metering Input Code" + " " + newMetInputCode + " Intraday";
		    	if(mpBean.getWarningsFlag()!=null && "Y".equalsIgnoreCase(mpBean.getWarningsFlag()))
		    		notifInfo += strNotifSeparator + "with warnings";
		    	else
		    		notifInfo += strNotifSeparator + " ";
		    	notifInfo += strNotifSeparator + mpBean.getSavedMeasurements();
		    	notifInfo += strNotifSeparator + mpBean.getTotalMeasurements();
	        	sendNotification(strNotifTypeCodeWsMeteringQueryOK, notifInfo);
	        	log.debug("[" + notifInfo + "] - sendNotification() OK finished.");
        	
	        } else {
	        	// 3.4.- Se guarda log de la respuesta al webservice en BD.
	        	wsLogBean.setStatus(Constants.ERROR_S);
	        	wsLogInBDResponse(wsLogBean, response);
	        	log.debug("[" + notifInfo + "] - wsLogInBDResponse() Error finished.");
	        	// 4.- Se actualiza en BD.
	        	// 4.1.- Se guarda la respuesta XML.
	        	metInBean = saveInBDErrorResponse(newMetInputCode, wsLogBean, response);
	        	log.debug("[" + notifInfo + "] - saveInBDResponse() finished.");
	        	
	        	// Se lanza una excepcion tecnica. Lo que venga del webservice no tiene por que tener sentido para el usuario.
	        	throwException(response);
	        }
        }
        catch( ValidationException ve ) {
	    	log.error(ve.getMessage(), ve);
	    	
        	// 5.- Se envia notificacion al usuario, con el error devuelto desde la BD.
        	try{
        		notifInfo += strNotifSeparator + "Metering Input Code" + " " + newMetInputCode;
        		notifInfo += " - " + ve.getMessage();
        		sendNotification(strNotifTypeCodeWsMeteringQueryError, notifInfo);
	        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
        	}
	        catch( Exception e3 ) {
		    	log.error(e3.getMessage(), e3);
	        }
        }
        catch( Exception e ) {
	    	log.error(e.getMessage(), e);
	    	
        	// 5.- Se envia notificacion al usuario. Con mensaje de error generico.
        	try{
		    	notifInfo += strNotifSeparator + "Metering Input Code" + " " + newMetInputCode;
		    	notifInfo += " - " + "Internal error." + ". "; 
        		sendNotification(strNotifTypeCodeWsMeteringQueryError, notifInfo);
	        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
        	}
	        catch( Exception e2 ) {
		    	log.error(e2.getMessage(), e2);
	        }
        }
        finally{
        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
            if(!releaseLockMeteringQuery(strUser))
    	    	log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
        	log.debug("[" + notifInfo + "] - releaseLockMeteringQuery() finished.");
        }
				

		
	}
	

	
	
	public void  callAllocationIntradayRequestClient(boolean execute_ws_metering)throws JobExecutionException {
		
	
		if(execute_ws_metering) {
		
			System.out.println("Job callAllocationIntradayRequestClient is runing");
			log.info("Job callAllocationIntradayRequestClient is runing", Calendar.getInstance().getTime());
	
			//buscamos el flag que nos indique si enviamos notificaciones o no...
			String execution_enable="N";
			try {
				execution_enable = getSystemParameterString(strAllocationIntradayAllocation, strUser, strLanguage);
			} catch (Exception e) {
		    	log.error(e.getMessage(), e);
			}
			if(!execution_enable.equals("Y")) {
				System.out.println("Job callAllocationIntradayRequestClient is disable");
				log.info("Job callAllocationIntradayRequestClient is disable", Calendar.getInstance().getTime());
				return;
			}
			try {
				// llamamos al metodo para obtener las medidas
				callMeteringIntradayManagementRequestClient();
			} catch(Exception e) {
				log.error(e.getMessage(),e);
				System.out.println("Exception: " + e.getMessage());
				e.printStackTrace();
				
			}
		}
				
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			this.idnOnshoreSystem = list_onshore.get(0);
		}
		
		//buscamos el flag que nos indique si enviamos notificaciones o no...
/*		Integer allocationMaxDateOffset=null;
		try {
			allocationMaxDateOffset = getSystemParameter(strAllocationMaxDateOffset, strUser, strLanguage);
		} catch (Exception e) {
	    	log.error(e.getMessage(), e);
		}
		setResponsePeriod(allocationMaxDateOffset);*/
	
		List<BigDecimal> list_user_group = systemMapper.getIdnUserGroupOperator();
		if(list_user_group==null || list_user_group.size()==0) {
			log.error("Not exist idn_user_group associated to group OPERATOR");
			return;
		} else {
			this.idnUserGroup = list_user_group.get(0);
		}

		
    	Calendar cal_gasDayFrom = Calendar.getInstance();
    	BigDecimal count = wsMapper.checkIntradayExecution(this.idnOnshoreSystem);
    	if(count.intValue()==0) {
    		cal_gasDayFrom.add(Calendar.DAY_OF_YEAR, -1);
    	}
    	Calendar cal_gasDayTo = Calendar.getInstance();
		
    	Date startUpdatingDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	String notifInfo = sdf.format(cal_gasDayFrom.getTime()) + strNotifSeparator +
				sdf.format(cal_gasDayTo.getTime()) + strNotifSeparator +
				sdf2.format(startUpdatingDate) + " Intraday";	    	
    	
    	
		try {
	
	    	CalculateAllocationBalanceBean cabBean = new CalculateAllocationBalanceBean();
	    	cabBean.setAllocationTypeCode(strAllocationTypeCommercial);
	    	cabBean.setBalanceClosingTypeCode(strBalanceClosingTypeDefinitive);
	    	cabBean.setUpdateBalance(strUpdateBalanceY);
	    	cabBean.setUserName(strUser);
	    	cabBean.setLanguage(strLanguage);
			cabBean.setIdnSystem(this.idnOnshoreSystem);
	    	
	    	amMapper.calculateAllocationBalance(cabBean);

			// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
			if(cabBean == null || cabBean.getErrorCode()==null)
				throw new Exception("Error calculating allocation and balance.");
	    	
			int res = cabBean.getErrorCode().intValue();
			if( res != 0) {
				if( res >= 1000 )	// Errores funcionales.
		    		throw new ValidationException(cabBean.getErrorDesc());
				else				// Errores tecnicos.
		    		throw new Exception(cabBean.getErrorDesc());
			}
	    	
    		log.debug("[" + notifInfo + "] - calculateAllocationBalance(), DB procedure, finished.");

			sendNotif2Operator(strNotifTypeAllocationExecutionFinishedOK, notifInfo, strLanguage, this.idnOnshoreSystem);
			sendNotif2Shippers(strNotifTypeAllocationExecutionFinishedOK, notifInfo, strLanguage, this.idnOnshoreSystem);
        	log.debug("[" + notifInfo + "] - sendNotification() OK finished.");
		} 
        catch( ValidationException ve ) {
	    	log.error(ve.getMessage(), ve);
	    	
        	// 5.- Se envia notificacion al usuario, con el error devuelto desde la BD.
        	try{
        		notifInfo += strNotifSeparator + ve.getMessage();
				sendNotif2Operator(strNotifTypeAllocationExecutionFinishedError, notifInfo, strLanguage, this.idnOnshoreSystem);
				sendNotif2Shippers(strNotifTypeAllocationExecutionFinishedError, notifInfo, strLanguage, this.idnOnshoreSystem);
	        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
        	}
	        catch( Exception e3 ) {
		    	log.error(e3.getMessage(), e3);
	        }
        }
        catch( Exception e ) {
	    	log.error(e.getMessage(), e);
	    	
        	// 5.- Se envia notificacion al usuario. Con mensaje de error generico.
        	try{
		    	notifInfo += strNotifSeparator + "Internal error.";
				sendNotif2Operator(strNotifTypeAllocationExecutionFinishedError, notifInfo, strLanguage, this.idnOnshoreSystem);
				sendNotif2Shippers(strNotifTypeAllocationExecutionFinishedError, notifInfo, strLanguage, this.idnOnshoreSystem);
	        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
        	}
	        catch( Exception e2 ) {
		    	log.error(e2.getMessage(), e2);
	        }
        }
	}
	

	// periodo [primer_dia_abierto; Hoy - allocationMaxDateOffset] 
	private void setResponsePeriod(Integer _allocationMaxDateOffset){
		
		// Primer dia de balance abierto.

		HashMap<String, Object> params = new HashMap<>();
		params.put("closingTypeCode", "DEFINITIVE");
		params.put("idnSystem", this.idnOnshoreSystem);
		params.put("sysCode", "ONSHORE");
		openPeriodFirstDay = amMapper.selectOpenPeriodFirstDay(params);

		Calendar tmpEndDate = Calendar.getInstance();
		tmpEndDate.set(Calendar.HOUR_OF_DAY, 0);
		tmpEndDate.set(Calendar.MINUTE, 0);
		tmpEndDate.set(Calendar.SECOND, 0);
		tmpEndDate.set(Calendar.MILLISECOND, 0);
		tmpEndDate.add(Calendar.DAY_OF_MONTH, _allocationMaxDateOffset * (-1));
		
		responsePeriodStartDate = openPeriodFirstDay;
		responsePeriodEndDate = tmpEndDate.getTime();
	}
	
	
	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(new Date());	// Now
		bean.setParameter_name(strAllocationMaxDateOffset);
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		systemParameterMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting ALLOCATION.MAX.DATE.OFFSET parameter");
		
		return bean.getInteger_exit();
	}

	// Se supone que el usuario que lanza el proceso de repartos y balances es operador.
	private void sendNotif2Operator(String _notifTypeCode, String _info, String _lang,
			BigDecimal systemId) throws Exception {
		
		sendNotification(_notifTypeCode, _info, this.idnUserGroup, _lang, systemId);
	}

	private List<BigDecimal> getAllShipperIdsForInsert() {
		
		List<ComboFilterNS> allShippers = amMapper.selectShipperIdForInsert();
		ArrayList<BigDecimal> allShipperIds = new ArrayList<BigDecimal>();
		for(ComboFilterNS combo :allShippers)
			allShipperIds.add(combo.getKey());
		
		return allShipperIds;
	}
		
	private void sendNotif2Shippers(String _notifTypeCode, String _info, String _lang,
			BigDecimal systemId) throws Exception {
		List<BigDecimal> lRecipients = getAllShipperIdsForInsert();
		for(BigDecimal recipient :lRecipients)
			sendNotification(_notifTypeCode, _info, recipient, _lang, systemId);
	}
	
	private void sendNotification(String _notifTypeCode, String _info, BigDecimal _recipient, String _lang, BigDecimal systemId) throws Exception {
    	int res = 0;
   	
		// Se genera la notificacion para el operador comuncando que ha acabado el proceso de repartos y balances.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);	
		notif.setOrigin(strNotifOriginAlloc);
		notif.setInformation(_info);
		notif.setUser_id(strUser);
		notif.setIdn_user_group(_recipient);
		notif.setLanguage(_lang);
		notif.setSystemId(systemId);
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			throw new Exception("Error sending notification.");
		}
		else {
			// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
			// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
			// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
			// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
			// "error interno" y los detalles se llevan al log.
			res = notif.getInteger_exit().intValue();
			if( res != 0) {
				if( res >= 1000 )	// Errores funcionales.
		    		throw new ValidationException(notif.getError_desc());
				else				// Errores tecnicos.
		    		throw new Exception(notif.getError_desc());
			}				
		}
	}	
	
	private String getSystemParameterString(String str, String _user, String _lang) throws Exception{
		String resParam = null;
		
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id(_user);
		bean.setLanguage(_lang);
		systemParameterMapper.getStringSystemParameter(bean);
		if( bean==null || bean.getString_exit()==null ){
			// Se envia error tecnico para no mostrar error al usuario.
			throw new Exception("Error getting system parameter.");
		}
		resParam = bean.getString_exit();
	
		return resParam;
	}

	private Integer getSystemParameter(String str, String _user, String _lang) throws Exception{
		Integer resParam = null;
		
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id(_user);
		bean.setLanguage(_lang);
		systemParameterMapper.getIntegerSystemParameter(bean);
		if( bean==null || bean.getInteger_exit()==null ){
			// Se envia error tecnico para no mostrar error al usuario.
			throw new Exception("Error getting system parameter.");
		}
		resParam = bean.getInteger_exit();
	
		return resParam;
	}
	
	private String getNewMeteringInputCode() throws Exception {
		
		ElementIdBean tmpBean = new ElementIdBean();
		
		tmpBean.setGenerationCode(Constants.METERING_INPUT);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		tmpBean.setUser(strUser);
		tmpBean.setLanguage(strLanguage);

		systemParameterMapper.getElementId(tmpBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());
		
		return tmpBean.getId();
	}
	
	private ProcessMessage prepareWsRequest(Date date_gasDayFrom, Date date_gasDayTo) throws Exception {
    	ObjectFactory of = new ObjectFactory();
    	
        ProcessMessage procMessage = of.createProcessMessage();
        
        MeteringQueryRequest metQueryReq = of.createMeteringQueryRequest();
        
        String FORMATER = "yyyy-MM-dd";
		
		SimpleDateFormat format = new SimpleDateFormat(FORMATER);

        CdmRequestHeader reqHeader = of.createCdmRequestHeader();
        reqHeader.setSystem(Constants.TPA);
        GregorianCalendar today = new GregorianCalendar();
        XMLGregorianCalendar xmlToday = DatatypeFactory.newInstance().newXMLGregorianCalendar(today);
        reqHeader.setMessageDate(xmlToday.normalize());
        metQueryReq.setHeader(reqHeader);
  
		GregorianCalendar gasDayFrom = new GregorianCalendar();
		gasDayFrom.setTime(date_gasDayFrom);
		XMLGregorianCalendar xmlFrom =
				DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date_gasDayFrom));
        
		GregorianCalendar gasDayTo = new GregorianCalendar();
		gasDayTo.setTime(date_gasDayTo);
		XMLGregorianCalendar xmlTo =
				DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date_gasDayTo));
		
        DateRange range = of.createDateRange();
        range.setFromDay(xmlFrom);
        range.setToDay(xmlTo);
        metQueryReq.setGasDayRange(range);
        
        ProcessMessageCls pmc = of.createProcessMessageCls();
        pmc.setMeteringQueryRequest(metQueryReq);
        procMessage.setRequest(pmc);
        
        return procMessage;
	}

	private WebserviceLogBean wsLogInBDRequest(Date startDate, ProcessMessage msg) {
		
		WebserviceLogBean tmpWsLogBean = new WebserviceLogBean(strUser);
		int res = -1;

		try {
			tmpWsLogBean.setWebserviceName(Constants.METERING_QUERY);
			tmpWsLogBean.setCallDate(startDate);
			tmpWsLogBean.setCallXml(requestToXML(msg));
			tmpWsLogBean.setStatus(Constants.RUNNING_S);
			
			res = mmMapper.insertRequestWebserviceLog(tmpWsLogBean);
		}
		catch(Exception e) {
			// Ante cualquier error al escribir el log en BD, no se corta la ejecucion, solo se guarda un log java.
        	log.error(e.getMessage(), e);
		}

		return tmpWsLogBean;
	}

	private String requestToXML(ProcessMessage msg) throws JAXBException{
		
		//MeteringQueryRequest msgQueryReq = msg.getRequest().getMeteringQueryRequest();
	
		JAXBContext jaxbContext = JAXBContext.newInstance(ProcessMessage.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed - No se modifica el mensaje a enviar; no se anaden retornos de carro.
		//jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(msg, sw);
		String xmlString = sw.toString();
		
		return xmlString;
	}

	private boolean isOKResult(ProcessMessageResponse msg) throws Exception {
		boolean bIsOKResult = false;
		
		MeteringQueryResponse msgQueryRes = msg.getProcessMessageResult();
		CdmResponseHeader resHeader = msgQueryRes.getHeader();
		bIsOKResult = resHeader.isOkResult();
		
		if(!bIsOKResult) {						// Se comprueban errores permitidos.
			CdmError error = msgQueryRes.getError();
			String errorCode = error.getErrorCode();
			for(int i=0; i<permittedWSErrorCodes.length; i++) {
				if(errorCode.equalsIgnoreCase(permittedWSErrorCodes[i])) {
					bIsOKResult = true;
					break;
				}
			}
		}
		
		return bIsOKResult; 
	}

	private void wsLogInBDResponse(WebserviceLogBean bean, ProcessMessageResponse msg) {

		int res = -1;

		try {
			bean.setResponseDate(new Date());
			bean.setResponseXml(responseToXML(msg));
			
			res = mmMapper.updateResponseWebserviceLog(bean);
		}
		catch(Exception e) {
			// Ante cualquier error al escribir el log en BD, no se corta la ejecucion, solo se guarda un log java.
        	log.error(e.getMessage(), e);
		}
	}

	private String responseToXML(ProcessMessageResponse msg) throws JAXBException{
		
		//MeteringQueryResponse msgQueryRes = msg.getProcessMessageResult();
		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ProcessMessageResponse.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed - No se modifica la respuesta recibida; no se anaden retornos de carro.
		//jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(msg, sw);
		String xmlString = sw.toString();
		
		return xmlString;
	}
	
	private MeteringInputBean saveInBDOKResponse(String metInputCode, WebserviceLogBean logBean, ProcessMessageResponse msg) throws Exception {
		return saveInBDResponse(metInputCode, logBean, Constants.NEW_S, msg);
	}
	
	private MeteringInputBean saveInBDErrorResponse(String metInputCode, WebserviceLogBean logBean, ProcessMessageResponse msg) throws Exception {
		return saveInBDResponse(metInputCode, logBean, Constants.ERROR_S, msg);
	}

	private MeteringInputBean saveInBDResponse(String metInputCode, WebserviceLogBean logBean, String status, ProcessMessageResponse msg) throws Exception {
		
		MeteringInputBean tmpMetInBean = new MeteringInputBean(strUser);
		int res = -1;

		// Si se produce algun error se gestionara como error tecnico (se muestra "internal error" al usuario y se guarda log).
		tmpMetInBean.setInputCode(metInputCode);
		tmpMetInBean.setInputDate(new Date());
		tmpMetInBean.setXmlData(responseToXML(msg));
		tmpMetInBean.setStatus(status);
		tmpMetInBean.setWebserviceLogId(logBean.getWebserviceLogId());
		
		res = mmMapper.insertMeteringInput(tmpMetInBean);
		if(res!=1)
    		throw new Exception("Error inserting into Metering Input table.");  
    		
		return tmpMetInBean;
	}
	
	void meteringSaveInBD(MeteringProcBean mpBean) throws Exception {
		mpBean.setUserId(strUser);
		mpBean.setLanguageCode(strLanguage);

		// Si hay un error en la llamada al procedimiento, se genera una excepcion y mas adelante se toma como error tecnico.
		mmMapper.meteringSave(mpBean);
		
		// El procedimiento va a devolver 0 en caso de OK o warning. En caso de warning, se devuelve
		// un string distinto de null.
		// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
		// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
		// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
		// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
		// "error interno" y los detalles se llevan al log.
		int res = mpBean.getErrorCode().intValue();
		if( res != 0) {
			if( res >= 1000 )	// Errores funcionales.
	    		throw new ValidationException(mpBean.getErrorDesc());
			else				// Errores tecnicos.
	    		throw new Exception(mpBean.getErrorDesc());
		}
	}

	void sendNotification(String _notifTypeCode, String _info) throws Exception {		
    	int res = 0;
   	
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);
		// Como se pueden recibir medidas de cualquiera de los dos sistemas, las notificaciones
		// se han de enviar a usuarios con perfiles de onshore y offshore. Se marca enviando un null.
		notif.setSystemId(null);
		notif.setOrigin(strNotifOriginMet);
		notif.setInformation(_info);

		notif.setUser_id(strUser);
		
    	List<ComboFilterNS> lUserGroups = mmMapper.selectUserGroupByUserId(strUser);
    	ComboFilterNS userGroup = lUserGroups.get(0);
		notif.setIdn_user_group(userGroup.getKey());
		
		notif.setLanguage(strLanguage);
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			throw new Exception("Error sending notification to shipper.");
		}
		else {
			// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
			// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
			// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
			// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
			// "error interno" y los detalles se llevan al log.
			res = notif.getInteger_exit().intValue();
			if( res != 0) {
				if( res >= 1000 )	// Errores funcionales.
		    		throw new ValidationException(notif.getError_desc());
				else				// Errores tecnicos.
		    		throw new Exception(notif.getError_desc());
			}				
		}
	}
	
	private void throwException(ProcessMessageResponse msg) throws Exception {
		MeteringQueryResponse msgQueryRes = msg.getProcessMessageResult();
		CdmError error = msgQueryRes.getError();
		throw new ValidationException(/*msgs.getString("met_man_ws_error") + */" " + error.getErrorDescription());
	}
	
	private boolean takeLockMeteringQuery(String userId) {
    	LockBean tmpLock = new LockBean();
    	tmpLock.setProcessCode(Constants.METERING_QUERY);
    	tmpLock.setUserId(userId);
    	mmMapper.exclusiveLockRequest(tmpLock);
    	return (tmpLock.getIntegerExit() == 0);
	}

	private boolean releaseLockMeteringQuery(String userId) {
    	LockBean tmpLock = new LockBean();
    	tmpLock.setProcessCode(Constants.METERING_QUERY);
    	tmpLock.setUserId(userId);
    	mmMapper.exclusiveLockRelease(tmpLock);
    	return (tmpLock.getIntegerExit() == 0);
	}

	private void validateRequest(MeteringManagementFilter filter, String userId, String lang) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
//    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	Calendar calExpiredDate = Calendar.getInstance(); 
    	calExpiredDate.add(Calendar.DAY_OF_MONTH, 1);
    	calExpiredDate.set(Calendar.HOUR_OF_DAY, 0);
    	calExpiredDate.set(Calendar.MINUTE, 0);
    	calExpiredDate.set(Calendar.SECOND, 0);
    	calExpiredDate.set(Calendar.MILLISECOND, 0);
    	// Se descuenta el offset, si lo hubiera.
    	calExpiredDate.add(Calendar.DAY_OF_MONTH, (-1)*getMeteringMaxDateOffset(userId, lang));
        
    	// Para que en la peticion al webservice no se pidan datos correspondientes a un rango de dias mayor que N. Para no sobrecargar al webservice.
    	Calendar calMaxDateRangeDateTo = Calendar.getInstance();
    	calMaxDateRangeDateTo.setTime(filter.getGasDayFrom());
    	calMaxDateRangeDateTo.add(Calendar.DAY_OF_MONTH, iMaxWSRequestDateRange);
    	
		if((filter.getGasDayFrom()==null) || (filter.getGasDayTo()==null)) { 
			throw new ValidationException("From and To dates must be informed.");
		}
		else{ // Aqui ya se ha comprobado que ninguna de las dos fechas es nula.
			if(filter.getGasDayFrom().after(filter.getGasDayTo()))
				throw new ValidationException("From Date must be earlier or equal to To Date");
			
			if(filter.getGasDayTo().compareTo(calMaxDateRangeDateTo.getTime()) >= 0) {
				String strDateRangeError = "A date range of more than XX days is not accepted.";
				strDateRangeError = strDateRangeError.replace("XX", String.valueOf(iMaxWSRequestDateRange));
				throw new ValidationException(strDateRangeError);
			}
			
			if(filter.getGasDayTo().compareTo(calExpiredDate.getTime())>= 0)			// Se compara la fecha To, con mayor o igual al dia en que se supone que se pasa del plazo.
				throw new ValidationException("To date should be previous to" + " " + sdf.format(calExpiredDate.getTime())+ ".");
			
			Date openPeriodFistDay = selectOpenPeriodFirstDay();
			if(filter.getGasDayFrom().before(openPeriodFistDay))
				throw new ValidationException(sdf.format(filter.getGasDayFrom()) + " " +  "From date can not be previous to" + " " + sdf.format(openPeriodFistDay)+ ".");			
		}
	}

	public int getMeteringMaxDateOffset(String userId, String lang) throws Exception {

		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(strMeteringMaxDateOffset);
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		systemParameterMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting METERING.MAX.DATE.OFFSET parameter");
		
		return bean.getInteger_exit();
	}
	
	public Date selectOpenPeriodFirstDay(){
		
		return mmMapper.selectOpenPeriodFirstDay();
	}

	
	
}

