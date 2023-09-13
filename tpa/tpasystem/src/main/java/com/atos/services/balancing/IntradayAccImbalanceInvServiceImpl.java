package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.FileBean;
import com.atos.beans.LanguageBean;
import com.atos.beans.LockBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.UserBean;
import com.atos.beans.WebServiceInputBean;
import com.atos.beans.WebServiceProcBean;
import com.atos.beans.WebserviceLogBean;
import com.atos.beans.balancing.IntradayAccImbalanceInventoryBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.WebServiceInputMapper;
import com.atos.mapper.balancing.IntradayAccImbalanceInvMapper;
import com.atos.mapper.utils.Xlsx2XmlMapper;
import com.atos.quartz.AcumInventoryAutorunClient;
import com.atos.utils.Constants;
import com.atos.utils.POIXSSFExcelUtils;
import com.atos.utils.Xlsx2XmlConverterWsIntrAccImbInv;
import com.atos.views.ChangeSystemView;

@Service("intradayAccImbInvService")
public class IntradayAccImbalanceInvServiceImpl implements IntradayAccImbalanceInvService {
	
	private static final long serialVersionUID = 5738619896981240370L;
	
	private static final String strNotifTypeCodeWsAcumInventoryError = "ACUM.INVENTORY.FILE.FINISH.ERROR";
	private static final String strNotifTypeCodeWsAcumInventoryyOK = "ACUM.INVENTORY.FILE.FINISH.OK";
	
	private static final String strUser="manager";
	private static final String strLanguage="en";
	
	private static final String strIntradayAccImbalanceInvTemplateCodeOperator = "ACC_IMBALANCE_INVENTORY_OPERATOR_REPORT";
	private static final String strIntradayAccImbalanceInvTemplateCodePttShipper = "ACC_IMBALANCE_INVENTORY_PTTSHIPPER_REPORT";
	private static final String strNotifOrigin = "BALANCING";
	private static final String strNotifSeparator = "~";
	
	private static final Logger log = LogManager.getLogger("com.atos.services.balancing.IntradayAccImbalanceInvServiceImpl");
	
	private POIXSSFExcelUtils excelUtil = new POIXSSFExcelUtils();
	
		@Autowired
		private IntradayAccImbalanceInvMapper intradayAccImbInvMapper;
		
		@Autowired
		private NotificationMapper notifMapper;	
		
		@Autowired
		@Qualifier("meteringTaskExecutor")
		private ThreadPoolTaskExecutor wsTaskExecutor;
		
		@Autowired
		private Xlsx2XmlMapper xMapper;
		
		@Autowired
		private WebServiceInputMapper wsMapper;

		@Autowired
		private AcumInventoryAutorunClient acumWS;

		@Override
		public List<IntradayAccImbalanceInventoryBean> selectIntradayAccImbalanceInv(IntradayAccImbalanceInventoryFilter filter){
			return intradayAccImbInvMapper.selectIntradayAccImbalanceInv(filter);
		}
		
		@Override
		public Map<BigDecimal, Object> selectTimestampIds(IntradayAccImbalanceInventoryFilter filters) {
			Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
			List<ComboFilterNS> list = intradayAccImbInvMapper.selectTimestampIds(filters);
			for (ComboFilterNS combo : list) {
				if (combo == null) continue;
				map.put(combo.getKey(), combo.getValue());
			}
			return map; 	
		}
		
		@Override
		public ByteArrayInputStream getReportTemplate(BigDecimal systemId, boolean isShipper) throws Exception{
			ReportTemplateBean rtbEntrada = new ReportTemplateBean();
			String template="";
			if(isShipper)
				template = strIntradayAccImbalanceInvTemplateCodePttShipper;
			else
				template = strIntradayAccImbalanceInvTemplateCodeOperator;
			rtbEntrada.setTempCode(template);
			rtbEntrada.setSystemId(systemId);
			
			List<ReportTemplateBean> lData = intradayAccImbInvMapper.selectReportTemplateFromCodeSystem(rtbEntrada);		
			if(lData == null || lData.size()==0)
				throw new Exception("Template file not found.");
			
			// Solo se va a tener un fichero por cada capacity request.
			ReportTemplateBean tmpRTBean = lData.get(0);
			if(tmpRTBean == null)
				throw new Exception("Template file not found.");		

			byte[] ba = tmpRTBean.getBinaryData();
			if(ba == null)
				throw new Exception("Template in BD with no binary data.");		
			
			ByteArrayInputStream bais = new ByteArrayInputStream(ba);
			
			return bais;
		}
		
		public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet){   
			excelUtil.copySheets(srcSheet, destSheet, true);   
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
				
	        	List<ComboFilterNS> lUserGroups = intradayAccImbInvMapper.selectUserGroupByUserId(this.userId);
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
	    private class UpdateWebserviceFromFileTask extends UpdateMeasurementsTask {
	    	FileBean file;
	    	BigDecimal systemId;		// Para saber que plantilla y configuracion de xlsx2XmlConverter hay que cargar, segun el sistema (onshore / offshore).
    	
	        public UpdateWebserviceFromFileTask(FileBean _file, String userId, String lang, ResourceBundle msgs, BigDecimal _systemId) {
	            super(userId, lang, msgs);
	            this.file = _file;
	            this.systemId = _systemId;
	        }
	
	        public void run() {
	        	WebServiceInputBean wsInBean = null;
	        	WebserviceLogBean wsLogBean = null;
		    	Date startUpdatingDate = new Date();
		    	String newMetInputCode = null;
		    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    	String notifInfo = file.getFileName() + strNotifSeparator + sdf2.format(startUpdatingDate);	
	    	
		        try {
		        	log.debug("[" + notifInfo + "] - UpdateMeasurementsFromFileTask - run() start.");
	
		        	// 2.5.- Se obtiene un nuevo codigo de metering_input, para guardarlo en la tabla y incluirlo en notificaciones.
			    	newMetInputCode = "ACUM.INVENTORY"; //getNewMeteringInputCode();
			    	log.debug("[" + notifInfo + "] - ACUM.INVENTORY finished: " + newMetInputCode );
			    	
		        	// 3.- Se obtiene el fichero xml.
			    	Xlsx2XmlConverterWsIntrAccImbInv xmlConverter = getXmlConverter(systemId);   	
		    		String xml = xmlConverter.getXMLFromExcel(file.getContents(), msgs);   	
		    		log.debug("Generated XML File: " + xml);
		    		
		        	// 4.- Se actualiza en BD.
		    		// 4.1.- Se guarda la respuesta XML.
		        	wsInBean = saveInBDResponse(wsLogBean, xml);
		        	log.debug("[" + notifInfo + "] - saveInBDResponse() finished.");
		        	
		        	// 4.2.- Se actualizan tablas.
					WebServiceProcBean wsBean = new WebServiceProcBean();
					wsBean.setWebserviceInputId(wsInBean.getWebserviceInputId());
					acumInventorySaveInBD(wsBean);
		        	log.debug("[" + notifInfo + "] - acumInventorySaveInBD(), DB procedure, finished.");
		        	
		        	// 5.- Se envia notificacion al usuario.
			    	notifInfo += strNotifSeparator + wsBean.getSavedMeasurements();
			    	notifInfo += strNotifSeparator + wsBean.getTotalMeasurements();
		        	sendNotification(strNotifTypeCodeWsAcumInventoryyOK, notifInfo);
		        	log.debug("[" + notifInfo + "] - sendNotification() OK finished.");
		
		        } 
		        catch( ValidationException ve ) {
			    	log.error(ve.getMessage(), ve);
			    	
		        	// 5.- Se envia notificacion al usuario, con el error devuelto desde la BD.
		        	try{
		        		notifInfo += strNotifSeparator + ve.getMessage();
		        		sendNotification(strNotifTypeCodeWsAcumInventoryError, notifInfo);
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
			    				msgs.getString("acc_imb_input_code") + " " + newMetInputCode;		        		
		        		sendNotification(strNotifTypeCodeWsAcumInventoryError, notifInfo);
			        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
		        	}
			        catch( Exception e2 ) {
				    	log.error(e2.getMessage(), e2);
			        }
		        }
		        finally{
		        	// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
		            if(!releaseLockMeteringQuery(this.userId))
		    	    	log.error("There has not been able to release the dababase lock on ACC. INVENTORY process.");
		        	log.debug("[" + notifInfo + "] - releaseLockAccInventoryQuery() finished.");
		        }
			}
	    	
	    	private Xlsx2XmlConverterWsIntrAccImbInv getXmlConverter(BigDecimal systemId) throws Exception {
	
	    		Xlsx2XmlConverterWsIntrAccImbInv tmpXmlConverter = new Xlsx2XmlConverterWsIntrAccImbInv();
	    		tmpXmlConverter.setxMapper(xMapper);
	    		tmpXmlConverter.init(Constants.NOT_APPLY, Constants.NOT_APPLY, systemId); 
	    	        	
	    		return tmpXmlConverter;		
	    	}
	    	
	    	private WebServiceInputBean saveInBDResponse(WebserviceLogBean logBean, String msg) throws Exception {
				
				WebServiceInputBean tmpWSInBean = new WebServiceInputBean(strUser);
				int res = -1;

				// Si se produce algun error se gestionara como error tecnico (se muestra "internal error" al usuario y se guarda log).
				tmpWSInBean.setWebservice(Constants.ACUM_WEBSERVICE);
				tmpWSInBean.setInputDate(new Date());
				tmpWSInBean.setXmlData(msg);
				tmpWSInBean.setFilename(file.getFileName());
				tmpWSInBean.setBinaryData(file.getContents());
				tmpWSInBean.setStatus(Constants.NEW_S);
				
				res = wsMapper.insertWebServiceInput(tmpWSInBean);
				if(res!=1)
		    		throw new Exception("Error inserting into WebService Input table.");  
		    		
				return tmpWSInBean;
			}
	    }	// class UpdateMeasurementsFromFileTask
		
		public void updateWsFromFile(FileBean _file, UserBean _user, LanguageBean _lang, ChangeSystemView _system) throws Exception {
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
	        		throw new ValidationException(msgs.getString("balancing_ws_lock_error"));
	        	    		
	        	// Se lanza un thread para seguir con el proceso de forma asincrona/desatendida.
	        	// Si se alcanza el numero maximo de threads concurrentes definidos en el metTaskExecutor,
	        	// el siguiente thread no se puede lanzar y se genera una org.springframework.core.task.TaskRejectedException
	        	wsTaskExecutor.execute(new UpdateWebserviceFromFileTask(_file, userId, lang, msgs, _system.getIdn_active()));
	        }   
	        catch (TaskRejectedException tre) {	// Excepcion para el caso de que no se pueda generar un thread porque se ha alcanzado el maximo numero de threads.
	        			// En caso de error, se ha de liberar el bloqueo.
						// En caso de ok, el bloqueo se libera en el thread.
				// Solo se va a intentar liberar el bloqueo si esta sesion fue en la que se cogio en un principio. 
				if(bTakeLock) {
					// Se libera el bloqueo para que se pueda volver a ejecutar el proceso mas adelante.
					if(!releaseLockMeteringQuery(userId))
						log.error("There has not been able to release the dababase lock on WS_QUERY process.");
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
		    	    	log.error("There has not been able to release the dababase lock on WS_QUERY process.");
	        	}
	        	
	        	throw e;
	        }
		}
		
		void acumInventorySaveInBD(WebServiceProcBean wsBean) throws Exception {
			wsBean.setUserId(strUser);
			wsBean.setLanguageCode(strLanguage);

			// Si hay un error en la llamada al procedimiento, se genera una excepcion y mas adelante se toma como error tecnico.
			wsMapper.webServiceAcumInventorySave(wsBean);
			
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
		
		private boolean takeLockMeteringQuery(String userId) {
	    	LockBean tmpLock = new LockBean();
	    	tmpLock.setProcessCode(Constants.ACUM_INVENTORY);
	    	tmpLock.setUserId(userId);
	    	intradayAccImbInvMapper.exclusiveLockRequest(tmpLock);
	    	return (tmpLock.getIntegerExit() == 0);
		}
		
		private boolean releaseLockMeteringQuery(String userId) {
	    	LockBean tmpLock = new LockBean();
	    	tmpLock.setProcessCode(Constants.ACUM_INVENTORY);
	    	tmpLock.setUserId(userId);
	    	intradayAccImbInvMapper.exclusiveLockRelease(tmpLock);
	    	return (tmpLock.getIntegerExit() == 0);
		}

		@Override
		public void callWS() throws Exception {
			
			acumWS.callAcumInventoryClient();
		}
}
