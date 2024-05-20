package com.atos.services.metering;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.FileBean;
import com.atos.beans.LanguageBean;
import com.atos.beans.LockBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.OpTemplateBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.WebserviceLogBean;
import com.atos.beans.metering.MeasureGasQualityParamBean;
import com.atos.beans.metering.MeasurementBean;
import com.atos.beans.metering.MeteringInputBean;
import com.atos.beans.metering.MeteringProcBean;
import com.atos.beans.metering.PointDto;
import com.atos.client.pmisdwh.meteringQuery.MetQueryWsClient;
import com.atos.exceptions.ValidationException;
import com.atos.filters.metering.MeteringManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;
import com.atos.mapper.metering.MeteringManagementMapper;
import com.atos.mapper.utils.Xlsx2XmlMapper;
import com.atos.quartz.AcumInventoryAutorunClient;
import com.atos.quartz.BaseInventoryAutorunClient;
import com.atos.utils.Constants;
import com.atos.utils.Xlsx2XmlConverter2;
import com.atos.views.ChangeSystemView;
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

@Service("MetManagementService")
public class MeteringManagementServiceImpl implements MeteringManagementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1616500768028567685L;
	
	// Para validar filtros de fechas.
	private static final String strMeteringMaxDateOffset = "METERING.MAX.DATE.OFFSET";
	
	private static final String strNotifTypeCodeWsMeteringQueryOK = "METERING.WS.RETRIEVING.FINISH.OK";
	private static final String strNotifTypeCodeWsMeteringQueryError = "METERING.WS.RETRIEVING.FINISH.ERROR";
	private static final String strNotifTypeCodeFileMeteringQueryOK = "METERING.FILE.RETRIEVING.FINISH.OK";
	private static final String strNotifTypeCodeFileMeteringQueryError = "METERING.FILE.RETRIEVING.FINISH.ERROR";
	
	private static final String strNotifOrigin = "METERING";
	private static final String strNotifSeparator = "~";
	
	// Para que en la peticion al webservice no se pidan datos correspondientes a un rango de dias mayor que N. Para no sobrecargar al webservice.
	private static final int iMaxWSRequestDateRange = 5;  // Max. number of days to request to metering webservice. 
	private  static final String[] permittedWSErrorCodes = {"ERR-PMISDWH-0000"};
	
	@Autowired
	private AllocationManagementMapper amMapper;

	@Autowired
	private MeteringManagementMapper mmMapper;
	
	@Autowired
	private MetQueryWsClient wsClient;

	@Autowired
	private SystemParameterMapper sysParMapper;
	
	@Autowired
	private NotificationMapper notifMapper;	
	
	@Autowired
	private Xlsx2XmlMapper xMapper;
	
	@Autowired
	@Qualifier("allocationBalanceTaskExecutor")
	private ThreadPoolTaskExecutor allBalTaskExecutor;
	
	@Autowired
	private AcumInventoryAutorunClient acumWS;
	
	@Autowired
	private BaseInventoryAutorunClient baseWS;
	
	@Autowired
	@Qualifier("meteringTaskExecutor")
	private ThreadPoolTaskExecutor metTaskExecutor;
	
	private static final Logger log = LogManager.getLogger("com.atos.services.metering.MeteringManagementServiceImpl");

	public Date selectOpenPeriodFirstDay(){
	
		return mmMapper.selectOpenPeriodFirstDay();
	}
	
	public Date selectLastOKMeteringInputDate(){
		
		return mmMapper.selectLastOKMeteringInputDate();
	}
	
	public Map<BigDecimal, Object> selectZones(String systemCode){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mmMapper.selectZonesFromSystemCode(systemCode);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectAreas(MeteringManagementFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mmMapper.selectAreasFromZoneId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectMeteringPoints(MeteringManagementFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mmMapper.selectMeteringSystemPoints(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectMeteringInputCodes(MeteringManagementFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mmMapper.selectMeteringInputCodes(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<MeasurementBean> search(MeteringManagementFilter filter){
		return mmMapper.selectMeasurements(filter);
	}
	
	public List<MeasureGasQualityParamBean> selectGasQualityParams(MeasurementBean mBean){
		return mmMapper.selectGasQualityParametersFromMeasurementId(mBean.getMeasurementId());
	}
	
	public void updateMeasurementsFromWebservice(MeteringManagementFilter filter, UserBean _user, LanguageBean _lang,Date _startDate, Date _endDate, BigDecimal idnSystem) throws Exception {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	boolean bTakeLock = false;
    	String userId = _user.getUsername();
    	String lang = _lang.getLocale();
    	
        try {
        	// 1.- Se validan los parametros de entrada.
        	validateRequest(filter, userId, lang);
        	
        	// 2.- Se comprueba si el proceso de actualizacion de medidas ya se ha lanzado
        	// actualmente por otro usuario. Para ello se consulta una tabla de bloqueos en BD.
        	bTakeLock = takeLockMeteringQuery(userId);
        	
        	// Si en esta peticion no se pudo coger el bloqueo, se lanza una excepcion para enviar un mensaje de error al usuario.
        	if(!bTakeLock)
        		throw new ValidationException(msgs.getString("met_man_lock_error"));
        	
        	// Se lanza un thread para seguir con el proceso de forma asincrona/desatendida.
        	// Si se alcanza el numero maximo de threads concurrentes definidos en el metTaskExecutor,
        	// el siguiente thread no se puede lanzar y se genera una org.springframework.core.task.TaskRejectedException
        	metTaskExecutor.execute(new UpdateMeasurementsFromWebserviceTask(filter, userId, lang, msgs, _startDate, _endDate, idnSystem,_user,_lang));
        }   
        catch (TaskRejectedException tre) {	// Excepcion para el caso de que no se pueda generar un thread porque se ha alcanzado el maximo numero de threads.
        			// En caso de error, se ha de liberar el bloqueo.
					// En caso de ok, el bloqueo se libera en el thread.
			// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
			if(bTakeLock) {
				// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
				if(!releaseLockMeteringQuery(userId))
					log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
			}
			
			log.error(tre.getMessage(), tre);
			throw new ValidationException(msgs.getString("met_man_max_processes_reached_error"));
		}        
        catch (Exception e) {	// En caso de error, se ha de liberar el bloqueo.
        						// En caso de ok, el bloqueo se libera en el thread.
        	// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
        	if(bTakeLock) {
	        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
	            if(!releaseLockMeteringQuery(userId))
	    	    	log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
        	}
        	
        	throw e;
        }
	}
	
	public void updateMeasurementsFromFile(FileBean _file, UserBean _user, LanguageBean _lang, ChangeSystemView _system) throws Exception {
		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	boolean bTakeLock = false;
    	String userId = _user.getUsername();
    	String lang = _lang.getLocale();
    	
        try {
			// 1.- El rango de fechas se comprobará en el proc almacenado, que
			// analizara el fichero.
        	
        	// 2.- Se comprueba si el proceso de actualizacion de medidas ya se ha lanzado
        	// actualmente por otro usuario. Para ello se consulta una tabla de bloqueos en BD.
        	bTakeLock = takeLockMeteringQuery(userId);
        	
        	// Si en esta peticion no se pudo coger el bloqueo, se lanza una excepcion para enviar un mensaje de error al usuario.
        	if(!bTakeLock)
        		throw new ValidationException(msgs.getString("met_man_lock_error"));
        	    		
        	// Se lanza un thread para seguir con el proceso de forma asincrona/desatendida.
        	// Si se alcanza el numero maximo de threads concurrentes definidos en el metTaskExecutor,
        	// el siguiente thread no se puede lanzar y se genera una org.springframework.core.task.TaskRejectedException
        	metTaskExecutor.execute(new UpdateMeasurementsFromFileTask(_file, userId, lang, msgs, _system.getIdn_active()));
        }   
        catch (TaskRejectedException tre) {	// Excepcion para el caso de que no se pueda generar un thread porque se ha alcanzado el maximo numero de threads.
        			// En caso de error, se ha de liberar el bloqueo.
					// En caso de ok, el bloqueo se libera en el thread.
			// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
			if(bTakeLock) {
				// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
				if(!releaseLockMeteringQuery(userId))
					log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
			}
			
			log.error(tre.getMessage(), tre);
			throw new ValidationException(msgs.getString("met_man_max_processes_reached_error"));
		}        
        catch (Exception e) {	// En caso de error, se ha de liberar el bloqueo.
        						// En caso de ok, el bloqueo se libera en el thread.
        	// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
        	if(bTakeLock) {
	        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
	            if(!releaseLockMeteringQuery(userId))
	    	    	log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
        	}
        	
        	throw e;
        }
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
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
		
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
			throw new ValidationException(msgs.getString("met_man_required_from_to_error"));
		}
		else{ // Aqui ya se ha comprobado que ninguna de las dos fechas es nula.
			if(filter.getGasDayFrom().after(filter.getGasDayTo()))
				throw new ValidationException(msgs.getString("from_must_earlier_equal"));
			
			if(filter.getGasDayTo().compareTo(calMaxDateRangeDateTo.getTime()) >= 0) {
				String strDateRangeError = msgs.getString("met_man_date_range");
				strDateRangeError = strDateRangeError.replace("XX", String.valueOf(iMaxWSRequestDateRange));
				throw new ValidationException(strDateRangeError);
			}
			
			if(filter.getGasDayTo().compareTo(calExpiredDate.getTime())>= 0)			// Se compara la fecha To, con mayor o igual al dia en que se supone que se pasa del plazo.
				throw new ValidationException(msgs.getString("met_man_to_until") + " " + sdf.format(calExpiredDate.getTime())+ ".");
			
			Date openPeriodFistDay = selectOpenPeriodFirstDay();
			if(filter.getGasDayFrom().before(openPeriodFistDay))
				throw new ValidationException(msgs.getString("met_man_from_later") + " " + sdf.format(openPeriodFistDay)+ ".");			
		}
	}

	public int getMeteringMaxDateOffset(String userId, String lang) throws Exception {

		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(sysParMapper.getSysdate().get(0));
		bean.setParameter_name(strMeteringMaxDateOffset);
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		sysParMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting METERING.MAX.DATE.OFFSET parameter");
		
		return bean.getInteger_exit();
	}
	
	public DefaultStreamedContent selectTemplateFile(BigDecimal _systemId) throws Exception {
	   	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

		OpTemplateBean otbEntrada = new OpTemplateBean();
		otbEntrada.setOpCategoryCode(Constants.METERING);
		otbEntrada.setOpTermCode(Constants.NOT_APPLY);
		otbEntrada.setFileType(Constants.INTERMEDIATE);
		otbEntrada.setSystemId(_systemId);
	
		List<OpTemplateBean> lotbSalida = mmMapper.getOpTemplateByCatTermFiletypeSystem(otbEntrada);
		if( lotbSalida.size() == 0) {
    		throw new Exception(msgs.getString("met_man_template_file") + " " + msgs.getString("not_found"));
		}
		else {
			// Solo se va a tener un fichero por cada template.
			OpTemplateBean otbXLSTemplate = lotbSalida.get(0);
			byte[] ba = otbXLSTemplate.getBinaryData();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(ba);
			DefaultStreamedContent tmpSCont = 
					new DefaultStreamedContent(bais,
												"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
												otbXLSTemplate.getFileName());
			return tmpSCont;
		}
	}
	
	// Se define la tarea/thread como clase interna para tener acceso a las variables de la clase principal.
    private abstract class UpdateMeasurementsTask implements Runnable {

        String userId;
        String lang;
    	ResourceBundle msgs;
        
        UpdateMeasurementsTask(String userId, String lang, ResourceBundle msgs) {
            this.userId = userId;
            this.lang = lang;
            this.msgs = msgs;
        }
		
		String getNewMeteringInputCode() throws Exception {
			
			ElementIdBean tmpBean = new ElementIdBean();
			
			tmpBean.setGenerationCode(Constants.METERING_INPUT);
			// Si se deja la fecha a nulo, en BD se toma systemdate.
			tmpBean.setDate(null);
			tmpBean.setUser(this.userId);
			tmpBean.setLanguage(this.lang);
	
			sysParMapper.getElementId(tmpBean);
			
			// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
			if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
				throw new Exception(tmpBean.getErrorDesc());
			
			return tmpBean.getId();
		}
	
		void meteringSaveInBD(MeteringProcBean mpBean) throws Exception {
			mpBean.setUserId(this.userId);
			mpBean.setLanguageCode(this.lang);
	
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
			notif.setOrigin(strNotifOrigin);
			notif.setInformation(_info);

			notif.setUser_id(this.userId);
			
        	List<ComboFilterNS> lUserGroups = mmMapper.selectUserGroupByUserId(this.userId);
        	ComboFilterNS userGroup = lUserGroups.get(0);
			notif.setIdn_user_group(userGroup.getKey());
			
			notif.setLanguage(this.lang);
			
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
		
    }	// class UpdateMeasurementsTask

    
	// Se define la tarea/thread como clase interna para tener acceso a las variables de la clase principal.
    private class UpdateMeasurementsFromWebserviceTask extends UpdateMeasurementsTask {

        private MeteringManagementFilter mmFilter;
    	private Date startDate;
    	private Date endDate;
    	private BigDecimal idnSystem;
    	private UserBean user;
    	private LanguageBean lang;
      
        public UpdateMeasurementsFromWebserviceTask(MeteringManagementFilter filter, String userId, String language, ResourceBundle msgs, Date _startDate, Date _endDate, BigDecimal idnSystem, UserBean user, LanguageBean lang) {
            super(userId, language, msgs);
        	this.mmFilter = filter;
        	this.startDate = _startDate;
        	this.endDate = _endDate;
        	this.idnSystem = idnSystem;
        	this.user = user;
        	this.lang = lang;
        }

        public void run() {

	    	WebserviceLogBean wsLogBean = null;
	    	MeteringInputBean metInBean = null;
	    	Date startUpdatingDate = new Date();
	    	String newMetInputCode = null;
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	String notifInfo = sdf.format(this.mmFilter.getGasDayFrom()) + strNotifSeparator +
    				sdf.format(this.mmFilter.getGasDayTo()) + strNotifSeparator +
    				sdf2.format(startUpdatingDate);	    	
	        try {
	        	log.debug("[" + notifInfo + "] - UpdateMeasurementsFromWebserviceTask - run() start.");

	        	// 2.5.- Se obtiene un nuevo codigo de metering_input, para guardarlo en la tabla y incluirlo en notificaciones.
		    	newMetInputCode = getNewMeteringInputCode();
		    	log.debug("[" + notifInfo + "] - getNewMeteringInputCode() finished: " + newMetInputCode );
		    	
	        	// 3.- Se obtienen medidas del webservice.
	        	// 3.1.- Se prepara la peticion.
	        	ProcessMessage procMessage = prepareWsRequest(this.mmFilter);
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
			    	notifInfo += strNotifSeparator + msgs.getString("met_man_met_input_code") + " " + newMetInputCode;
			    	if(mpBean.getWarningsFlag()!=null && "Y".equalsIgnoreCase(mpBean.getWarningsFlag()))
			    		notifInfo += strNotifSeparator + msgs.getString("with_warnings");
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
	        		notifInfo += strNotifSeparator + msgs.getString("met_man_met_input_code") + " " + newMetInputCode;
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
			    	notifInfo += strNotifSeparator + msgs.getString("met_man_met_input_code") + " " + newMetInputCode;
			    	notifInfo += " - " + msgs.getString("internal_error") + ". "; 
	        		sendNotification(strNotifTypeCodeWsMeteringQueryError, notifInfo);
		        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
	        	}
		        catch( Exception e2 ) {
			    	log.error(e2.getMessage(), e2);
		        }
	        }
	        finally{
	        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
	            if(!releaseLockMeteringQuery(this.userId))
	    	    	log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
	        	log.debug("[" + notifInfo + "] - releaseLockMeteringQuery() finished.");
	        }
	        
			try {
				acumWS.callAcumInventoryClient();
				
			} catch (Exception e) {
				log.error("Error in Acum inventory WS");
				e.printStackTrace();
			}
			try {
				baseWS.callBaseInventoryClient();
				
			} catch (Exception e) {
				log.error("Error in Base inventory WS");
				e.printStackTrace();
			}

	        
		}
	
		
		private ProcessMessage prepareWsRequest(MeteringManagementFilter filter) throws Exception {
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
			gasDayFrom.setTime(filter.getGasDayFrom());
			XMLGregorianCalendar xmlFrom =
					DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(gasDayFrom.getTime()));

	        
			GregorianCalendar gasDayTo = new GregorianCalendar();
			gasDayTo.setTime(filter.getGasDayTo());
			XMLGregorianCalendar xmlTo =
					DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(gasDayTo.getTime()));
			
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
			
			WebserviceLogBean tmpWsLogBean = new WebserviceLogBean(this.userId);
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
	
	
		private void throwException(ProcessMessageResponse msg) throws Exception {
			MeteringQueryResponse msgQueryRes = msg.getProcessMessageResult();
			CdmError error = msgQueryRes.getError();
			throw new ValidationException(msgs.getString("met_man_ws_error") + " " + error.getErrorDescription());
		}
		
		private MeteringInputBean saveInBDOKResponse(String metInputCode, WebserviceLogBean logBean, ProcessMessageResponse msg) throws Exception {
			return saveInBDResponse(metInputCode, logBean, Constants.NEW_S, msg);
		}
		
		private MeteringInputBean saveInBDErrorResponse(String metInputCode, WebserviceLogBean logBean, ProcessMessageResponse msg) throws Exception {
			return saveInBDResponse(metInputCode, logBean, Constants.ERROR_S, msg);
		}

		private MeteringInputBean saveInBDResponse(String metInputCode, WebserviceLogBean logBean, String status, ProcessMessageResponse msg) throws Exception {
			
			MeteringInputBean tmpMetInBean = new MeteringInputBean(this.userId);
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
    }	// class UpdateMeasurementsFromWebserviceTask
 
	// Se define la tarea/thread como clase interna para tener acceso a las variables de la clase principal.
    private class UpdateMeasurementsFromFileTask extends UpdateMeasurementsTask {
        
    	FileBean file;
    	BigDecimal systemId;		// Para saber que plantilla y configuracion de xlsx2XmlConverter hay que cargar, segun el sistema (onshore / offshore).
    	
        public UpdateMeasurementsFromFileTask(FileBean _file, String userId, String lang, ResourceBundle msgs, BigDecimal _systemId) {
            super(userId, lang, msgs);
            this.file = _file;
            this.systemId = _systemId;
        }

        public void run() {

	    	MeteringInputBean metInBean = null;
	    	Date startUpdatingDate = new Date();
	    	String newMetInputCode = null;
	    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	String notifInfo = file.getFileName() + strNotifSeparator + sdf2.format(startUpdatingDate);	
    	
	        try {
	        	log.debug("[" + notifInfo + "] - UpdateMeasurementsFromFileTask - run() start.");

	        	// 2.5.- Se obtiene un nuevo codigo de metering_input, para guardarlo en la tabla y incluirlo en notificaciones.
		    	newMetInputCode = getNewMeteringInputCode();
		    	log.debug("[" + notifInfo + "] - getNewMeteringInputCode() finished: " + newMetInputCode );
		    	
	        	// 3.- Se obtiene el fichero xml.
	        	Xlsx2XmlConverter2 xmlConverter = getXmlConverter(systemId);   	
	    		String xml = xmlConverter.getXMLFromExcel(file.getContents(), msgs);   	
	    		log.debug("Generated XML File: " + xml);
	    		
	        	// 4.- Se actualiza en BD.
	        	// 4.1.- Se guarda la respuesta XML.
	        	metInBean = saveInBDResponse(newMetInputCode, xml);
	        	log.debug("[" + notifInfo + "] - saveInBDResponse() finished.");
	        	// 4.2.- Se actualizan tablas.
				MeteringProcBean mpBean = new MeteringProcBean();
				mpBean.setMeteringInputId(metInBean.getMeteringInputId());
	        	meteringSaveInBD(mpBean);
	        	log.debug("[" + notifInfo + "] - meteringSaveInBD(), DB procedure, finished.");
	        	
	        	// 5.- Se envia notificacion al usuario.
		    	notifInfo += strNotifSeparator + msgs.getString("met_man_met_input_code") + " " + newMetInputCode;
		    	if(mpBean.getWarningsFlag()!=null && "Y".equalsIgnoreCase(mpBean.getWarningsFlag()))
		    		notifInfo += strNotifSeparator + msgs.getString("with_warnings");
		    	else
		    		notifInfo += strNotifSeparator + " ";
		    	notifInfo += strNotifSeparator + mpBean.getSavedMeasurements();
		    	notifInfo += strNotifSeparator + mpBean.getTotalMeasurements();
	        	sendNotification(strNotifTypeCodeFileMeteringQueryOK, notifInfo);
	        	log.debug("[" + notifInfo + "] - sendNotification() OK finished.");
	
	        } 
	        catch( ValidationException ve ) {
		    	log.error(ve.getMessage(), ve);
		    	
	        	// 5.- Se envia notificacion al usuario, con el error devuelto desde la BD.
	        	try{
	        		notifInfo += strNotifSeparator + ve.getMessage();
	        		sendNotification(strNotifTypeCodeFileMeteringQueryError, notifInfo);
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
			    	notifInfo += strNotifSeparator + msgs.getString("internal_error") + ". " + 
		    				msgs.getString("met_man_met_input_code") + " " + newMetInputCode;		        		
	        		sendNotification(strNotifTypeCodeFileMeteringQueryError, notifInfo);
		        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
	        	}
		        catch( Exception e2 ) {
			    	log.error(e2.getMessage(), e2);
		        }
	        }
	        finally{
	        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
	            if(!releaseLockMeteringQuery(this.userId))
	    	    	log.error("There has not been able to release the dababase lock on METERING_QUERY process.");
	        	log.debug("[" + notifInfo + "] - releaseLockMeteringQuery() finished.");
	        }
		}
    	
    	private Xlsx2XmlConverter2 getXmlConverter(BigDecimal systemId) throws Exception {

    		Xlsx2XmlConverter2 tmpXmlConverter = new Xlsx2XmlConverter2();
    		tmpXmlConverter.setxMapper(xMapper);
    		tmpXmlConverter.init(Constants.METERING, Constants.NOT_APPLY, systemId);
    	        	
    		return tmpXmlConverter;		
    	}
   	
		private MeteringInputBean saveInBDResponse(String metInputCode, String xml) throws Exception {
			
			MeteringInputBean tmpMetInBean = new MeteringInputBean(this.userId);
			int res = -1;
	
			// Si se produce algun error se gestionara como error tecnico (se muestra "internal error" al usuario y se guarda log).
			tmpMetInBean.setInputCode(metInputCode);
			tmpMetInBean.setInputDate(new Date());
			tmpMetInBean.setFileName(file.getFileName());
			tmpMetInBean.setBinaryData(file.getContents());
			tmpMetInBean.setXmlData(xml);
			tmpMetInBean.setStatus(Constants.NEW_S);
			// En la carga desde fichero, no se genera registro en la tabla de log de webservices.
			tmpMetInBean.setWebserviceLogId(null);
			
			res = mmMapper.insertMeteringInput(tmpMetInBean);
			if(res!=1)
	    		throw new Exception("Error inserting into Metering Input table.");  
	    		
			return tmpMetInBean;
		}
    }	// class UpdateMeasurementsFromFileTask

	@Override
	public List<PointDto> getCheckedPoints(Date checkDate) {
		return mmMapper.checkPoints(checkDate);
	}

	
	public Date selectOpenPeriodFirstDay(Map<String, Object> params) {
		return amMapper.selectOpenPeriodFirstDay(params);
	}
	

}
