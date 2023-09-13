package com.atos.quartz;

import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Component;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LockBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.WebServiceInputBean;
import com.atos.beans.WebServiceProcBean;
import com.atos.beans.WebserviceLogBean;
import com.atos.client.pmisdwh.baseinventory.BaseInventoryWsClient;
import com.atos.exceptions.ValidationException;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.WebServiceInputMapper;
import com.atos.mapper.metering.MeteringManagementMapper;
import com.atos.services.CRNotificationService;
import com.atos.utils.Constants;
import com.atos.wsdl.pmisdwh.baseinventory.CdmError;
import com.atos.wsdl.pmisdwh.baseinventory.CdmRequestHeader;
import com.atos.wsdl.pmisdwh.baseinventory.CdmResponseHeader;
import com.atos.wsdl.pmisdwh.baseinventory.DateRange;
import com.atos.wsdl.pmisdwh.baseinventory.MeteringQueryRequest;
import com.atos.wsdl.pmisdwh.baseinventory.MeteringQueryResponse;
import com.atos.wsdl.pmisdwh.baseinventory.ObjectFactory;
import com.atos.wsdl.pmisdwh.baseinventory.ProcessMessage;
import com.atos.wsdl.pmisdwh.baseinventory.ProcessMessageCls;
import com.atos.wsdl.pmisdwh.baseinventory.ProcessMessageResponse;

@Component
public class BaseInventoryAutorunClient  implements Serializable {
	
	
	private static final long serialVersionUID = -9067325632463736509L;
	private static final Logger log = LogManager.getLogger(BaseInventoryAutorunClient.class);
	private static final String strNotifSeparator = "~";

	private static final String strBaseInventoryAutorun = "AUTORUN.BASE.INVENTORY";
	private static final String strNotifTypeCodeWsBaseInventoryyOK = "BASE.INVENTORY.WS.FINISH.OK";
	private static final String strNotifTypeCodeWsBaseInventoryError = "BASE.INVENTORY.WS.FINISH.ERROR";

	private static final String strNotifOrigin = "BALANCE";
	
	private  static final String[] permittedWSErrorCodes = {"ERR-PMISDWH-0000"};
	
	// Por ser un proceso automatico no va a tener usuario de acceso a la aplicacion. Le ponemos el inicial 'managerAut'.
	private static final String strUser="manager";
	private static final String strLanguage="en";
	private BigDecimal idnOnshoreSystem;
	
	@Autowired
	private NotificationMapper notifMapper;	
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private MeteringManagementMapper mmMapper;
	@Autowired
	private SystemMapper systemMapper;
	@Autowired
	private WebServiceInputMapper wsMapper;
	@Autowired
	private BaseInventoryWsClient wsClient;

	
	


	 @Autowired
	 @ManagedProperty("#{crNotificationService}")
	 transient private CRNotificationService serviceNotif;
	 
	public void setServiceNotif(CRNotificationService serviceNotif) {
		this.serviceNotif = serviceNotif;
	}
	
	public void callBaseInventoryClient() throws Exception{
		System.out.println("Job callBaseInventoryClient is runing");
		log.info("Job callBaseInventoryClient is runing", Calendar.getInstance().getTime());

		
//		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	boolean bTakeLock = false;
		
        try {
		
    	// Se comprueba si el proceso de actualizacion de medidas ya se ha lanzado
    	// actualmente por otro usuario. Para ello se consulta una tabla de bloqueos en BD.
    	bTakeLock = takeLockBaseInventory(strUser);
    	
    	// Si en esta peticion no se pudo coger el bloqueo, se lanza una excepcion para enviar un mensaje de error al usuario.
    	if(!bTakeLock)
    		throw new ValidationException("Error: There is another acum inventory process in progress. Please, try it later.");
    	
    		callBaseInventoryFromWebserviceTask();
        }   
        catch (TaskRejectedException tre) {	// Excepcion para el caso de que no se pueda generar un thread porque se ha alcanzado el maximo numero de threads.
        			// En caso de error, se ha de liberar el bloqueo.
					// En caso de ok, el bloqueo se libera en el thread.
			// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
			if(bTakeLock) {
				// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
				if(!releaseLockBaseInventory(strUser))
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
	            if(!releaseLockBaseInventory(strUser))
	    	    	log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
        	}
        	
        	throw e;
        }
		

		
	}
	
	public void  callBaseInventoryFromWebserviceTask() {
		
		
		//buscamos el flag que nos indique si enviamos notificaciones o no...
		String execution_enable="N";
		try {
			execution_enable = getSystemParameterString(strBaseInventoryAutorun, strUser, strLanguage);
		} catch (Exception e) {
	    	log.error(e.getMessage(), e);
		}
		if(!execution_enable.equals("Y")) {
			System.out.println("Job callBaseInventoryFromWebserviceTask is disable");
			log.info("Job callBaseInventoryFromWebserviceTask is disable", Calendar.getInstance().getTime());
			return;
		}
		
		List<BigDecimal> list_onshore = systemMapper.getOnshoreSystem();
		if(list_onshore==null || list_onshore.size()==0) {
			log.error("Not exist idn_system associated to system ONSHORE");
			return;
		} else {
			this.idnOnshoreSystem = list_onshore.get(0);
		}
		
	//	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		
    	WebserviceLogBean wsLogBean = null;
    	WebServiceInputBean wsInBean = null;
    	Date startUpdatingDate = new Date();
    	Calendar cal_gasDayFrom = Calendar.getInstance();
    	BigDecimal count = wsMapper.checkIntradayExecution(this.idnOnshoreSystem);
    	if(count.intValue()==0) {
    		cal_gasDayFrom.add(Calendar.DAY_OF_YEAR, -1);
    	}
    	Calendar cal_gasDayTo = Calendar.getInstance();
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	String notifInfo = sdf.format(cal_gasDayFrom.getTime()) + strNotifSeparator +
				sdf.format(cal_gasDayFrom.getTime()) + strNotifSeparator +
				sdf2.format(startUpdatingDate);	    	
        try {
        	log.debug("[" + notifInfo + "] - callAcumInventoryFromWebserviceTask - run() start.");

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
	        	wsInBean = saveInBDOKResponse(wsLogBean, response);
	        	log.debug("[" + notifInfo + "] - saveInBDResponse() finished.");
	        	
	        	// 4.2.- Se actualizan tablas.
				WebServiceProcBean wsBean = new WebServiceProcBean();
				wsBean.setWebserviceInputId(wsInBean.getWebserviceInputId());
				baseInventorySaveInBD(wsBean);
	        	log.debug("[" + notifInfo + "] - meteringSaveInBD(), DB procedure, finished.");
	        	
	        	// 5.- Se envia notificacion al usuario.
		    	notifInfo += strNotifSeparator + "Webservice Input  " + Constants.BASE_WEBSERVICE;
	    		notifInfo += strNotifSeparator + " ";
	        	sendNotification(strNotifTypeCodeWsBaseInventoryyOK, notifInfo);
	        	log.debug("[" + notifInfo + "] - sendNotification() OK finished.");
        	
	        } else {
	        	// 3.4.- Se guarda log de la respuesta al webservice en BD.
	        	wsLogBean.setStatus(Constants.ERROR_S);
	        	wsLogInBDResponse(wsLogBean, response);
	        	log.debug("[" + notifInfo + "] - wsLogInBDResponse() Error finished.");
	        	// 4.- Se actualiza en BD.
	        	// 4.1.- Se guarda la respuesta XML.
	        	wsInBean = saveInBDErrorResponse(wsLogBean, response);
	        	log.debug("[" + notifInfo + "] - saveInBDResponse() finished.");
	        	
	        	// Se lanza una excepcion tecnica. Lo que venga del webservice no tiene por que tener sentido para el usuario.
	        	throwException(response);
	        }
        }
        catch( ValidationException ve ) {
	    	log.error(ve.getMessage(), ve);
	    	
        	// 5.- Se envia notificacion al usuario, con el error devuelto desde la BD.
        	try{
        		notifInfo += strNotifSeparator + "Webservice Input  " + Constants.BASE_WEBSERVICE;
        		notifInfo += " - " + ve.getMessage();
        		sendNotification(strNotifTypeCodeWsBaseInventoryError, notifInfo);
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
		    	notifInfo += strNotifSeparator + "Webservice Input  " + Constants.BASE_WEBSERVICE;
		    	notifInfo += " - Internal error. "; 
        		sendNotification(strNotifTypeCodeWsBaseInventoryError, notifInfo);
	        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
        	}
	        catch( Exception e2 ) {
		    	log.error(e2.getMessage(), e2);
	        }
        }
        finally{
        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
            if(!releaseLockBaseInventory(strUser))
    	    	log.error("There has not been able to release the dababase lock on "+ Constants.BASE_WEBSERVICE +" process.");
        	log.debug("[" + notifInfo + "] - releaseLockMeteringQuery() finished.");
        }
				

		
	}
	
	private void sendNotification(String _notifTypeCode, String _info, BigDecimal _recipient, String _lang, BigDecimal systemId) throws Exception {
    	int res = 0;
   	
		// Se genera la notificacion para el operador comuncando que ha acabado el proceso de repartos y balances.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);	
		notif.setOrigin(strNotifOrigin);
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
	
	private ProcessMessage prepareWsRequest(Date date_gasDayFrom, Date date_gasDayTo) throws Exception {
    	ObjectFactory of = new ObjectFactory();
    	
        ProcessMessage procMessage = of.createProcessMessage();
        
        MeteringQueryRequest metQueryReq = of.createMeteringQueryRequest();

        CdmRequestHeader reqHeader = of.createCdmRequestHeader();
        reqHeader.setSystem("TPA");
        GregorianCalendar today = new GregorianCalendar();
        XMLGregorianCalendar xmlToday = DatatypeFactory.newInstance().newXMLGregorianCalendar(today);
        //XMLGregorianCalendar xmlToday =	DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date_gasDayFrom));
        reqHeader.setMessageDate(xmlToday.normalize());
        metQueryReq.setHeader(reqHeader);
        
        Calendar dayFrom = Calendar.getInstance();
        dayFrom.setTime(date_gasDayFrom);
        Calendar dayTo = Calendar.getInstance();
        dayTo.setTime(date_gasDayTo);
        
        GregorianCalendar gasDayFrom = new GregorianCalendar(dayFrom.get(Calendar.YEAR),dayFrom.get(Calendar.MONTH),dayFrom.get(Calendar.DAY_OF_MONTH));
		//XMLGregorianCalendar xmlFrom =DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date_gasDayFrom));
        XMLGregorianCalendar xmlFrom = DatatypeFactory.newInstance().newXMLGregorianCalendar(gasDayFrom);
        
        GregorianCalendar gasDayTo = new GregorianCalendar(dayTo.get(Calendar.YEAR),dayTo.get(Calendar.MONTH),dayTo.get(Calendar.DAY_OF_MONTH));
		//XMLGregorianCalendar xmlTo =DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date_gasDayTo));
        XMLGregorianCalendar xmlTo = DatatypeFactory.newInstance().newXMLGregorianCalendar(gasDayTo);			
		
        DateRange range = of.createDateRange();
        range.setStartDate(xmlFrom);
        range.setEndDate(xmlTo);
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
			tmpWsLogBean.setWebserviceName(Constants.BASE_INVENTORY);
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
	
	private WebServiceInputBean saveInBDOKResponse(WebserviceLogBean logBean, ProcessMessageResponse msg) throws Exception {
		return saveInBDResponse(logBean, Constants.NEW_S, msg);
	}
	
	private WebServiceInputBean saveInBDErrorResponse(WebserviceLogBean logBean, ProcessMessageResponse msg) throws Exception {
		return saveInBDResponse(logBean, Constants.ERROR_S, msg);
	}

	private WebServiceInputBean saveInBDResponse(WebserviceLogBean logBean, String status, ProcessMessageResponse msg) throws Exception {
		
		WebServiceInputBean tmpWSInBean = new WebServiceInputBean(strUser);
		int res = -1;

		// Si se produce algun error se gestionara como error tecnico (se muestra "internal error" al usuario y se guarda log).
		tmpWSInBean.setWebservice(Constants.BASE_WEBSERVICE);
		tmpWSInBean.setInputDate(new Date());
		tmpWSInBean.setXmlData(responseToXML(msg));
		tmpWSInBean.setStatus(status);
		tmpWSInBean.setWebserviceLogId(logBean.getWebserviceLogId());
		
		res = wsMapper.insertWebServiceInput(tmpWSInBean);
		if(res!=1)
    		throw new Exception("Error inserting into WebService Input table.");  
    		
		return tmpWSInBean;
	}
	
	void baseInventorySaveInBD(WebServiceProcBean wsBean) throws Exception {
		wsBean.setUserId(strUser);
		wsBean.setLanguageCode(strLanguage);

		// Si hay un error en la llamada al procedimiento, se genera una excepcion y mas adelante se toma como error tecnico.
		wsMapper.webServiceBaseInventorySave(wsBean);
		
		// El procedimiento va a devolver 0 en caso de OK o warning. En caso de warning, se devuelve
		// un string distinto de null.
		// En caso de error funcional, el procedimiento devuelve un codigo de error mayor o igual a 1000 y 
		// se devuelve una ValidationException (funcional). Esta excepcion se pintara en la ventana de mensajes al usuario.
		// En caso de error tecnico, el procedimiento devuelve un codigo de error menor que 1000 y distinto de cero.
		// se devuelve una Exception normal (error tecnico). En la ventana de mensajes al usuario se muestra un 
		// "error interno" y los detalles se llevan al log.
		int res = wsBean.getErrorCode().intValue();
		if( res != 0) {
			if( res >= 1000 )	// Errores funcionales.
	    		throw new ValidationException(wsBean.getErrorDesc());
			else				// Errores tecnicos.
	    		throw new Exception(wsBean.getErrorDesc());
		}
	}

	void sendNotification(String _notifTypeCode, String _info) throws Exception {		
    	int res = 0;
   	
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);
		// Como se pueden recibir medidas de cualquiera de los dos sistemas, las notificaciones
		// se han de enviar a usuarios con perfiles de onshore y offshore. Se marca enviando un null.
		notif.setSystemId(null);
		notif.setOrigin(strNotifOrigin);
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
	
	private boolean takeLockBaseInventory(String userId) {
    	LockBean tmpLock = new LockBean();
    	tmpLock.setProcessCode(Constants.ACUM_INVENTORY);
    	tmpLock.setUserId(userId);
    	mmMapper.exclusiveLockRequest(tmpLock);
    	return (tmpLock.getIntegerExit() == 0);
	}

	private boolean releaseLockBaseInventory(String userId) {
    	LockBean tmpLock = new LockBean();
    	tmpLock.setProcessCode(Constants.ACUM_INVENTORY);
    	tmpLock.setUserId(userId);
    	mmMapper.exclusiveLockRelease(tmpLock);
    	return (tmpLock.getIntegerExit() == 0);
	}

	
}

