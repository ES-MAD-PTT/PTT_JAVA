package com.atos.quartz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atos.beans.CRNotificationBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.exceptions.ValidationException;
import com.atos.mapper.CRNotificationMapper;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.booking.CapacityRequestMapper;
import com.atos.services.CRNotificationService;

@Component
public class CapacityRequestClient  implements Serializable {
	
	
	private static final long serialVersionUID = -9067325632463736509L;
	private static final Logger log = LogManager.getLogger(CapacityRequestClient.class);
	private static final String strNotifSeparator = "~";
	private static final String strNotifContractBankDocMissing = "CONTRACT.BANK_DOC.MISSING";
	private static final String strDefOperatorParamName = "DEFAULT.OPERATOR";
	private static final String strContBankNotificationEnabled="CONTRACT.BANK.GUARANTEE.NOTIFICATION.ENABLED";
	private static final String strContBankGuaranteeDeadLine="CONTRACT.BANK.GUARANTEE.DEADLINE";
	private static final String strContBankGuaranteeNotDaysBefore="CONTRACT.BANK.GUARANTEE.NOTIFICATION.DAYS.BEFORE";
	// Por ser un proceso automatico no va a tener usuario de acceso a la aplicacion. Le ponemos el inicial 'managerAut'.
	private static final String strUser="managerAut";
	private static final String strLanguage="en";
	private static final String strBooking= "CONTRACT";
	
	
	
	@Autowired
	private NotificationMapper notifMapper;	
	@Autowired
	private CRNotificationMapper CRnotifMapper;	
	
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private CapacityRequestMapper capacityRequestMapper;
	
	private List<CRNotificationBean> items;
	
	
	
	public List<CRNotificationBean> getItems() {
		return items;
	}

	public void setItems(List<CRNotificationBean> items) {
		this.items = items;
	}



	 @Autowired
	 @ManagedProperty("#{crNotificationService}")
	 transient private CRNotificationService serviceNotif;
	 
	public void setServiceNotif(CRNotificationService serviceNotif) {
		this.serviceNotif = serviceNotif;
	}
	
	
	
	public void  callCapacityRequestClient()throws JobExecutionException {
		
		
		System.out.println("Job callCapacityRequestClient is runing");
		log.info("Job callCapacityRequestClient is runing", Calendar.getInstance().getTime());
		
		//buscamos el flag que nos indique si enviamos notificaciones o no...
		String envio="N";
		try {
			envio = getSystemParameterString(strContBankNotificationEnabled, strUser, strLanguage);
		} catch (Exception e) {
	    	log.error(e.getMessage(), e);
		}
		
		if(envio.equals("N")){
			log.info("CapacityRequestClient.  El parametro CONTRACT.BANK.GUARANTEE.NOTIFICATION.ENABLED tiene valor N. No se envian Notificaciones.", Calendar.getInstance().getTime() );
		}else if (envio.equals("Y")){
			
			//Buscamos el primer parametro, plazo maximo
			Integer valP1=0;
			try {
				valP1 = getSystemParameter(strContBankGuaranteeDeadLine, strUser, strLanguage);
			} catch (Exception e) {
		    	log.error(e.getMessage(), e);
			}
		
			
			//Buscamos el segundo parametro, dias para el aviso
			Integer valP2=0;;
			try {
				valP2 = getSystemParameter(strContBankGuaranteeNotDaysBefore, strUser, strLanguage);
			} catch (Exception e) {
		    	log.error(e.getMessage(), e);
			}
			
			//calculamos la difencia de dias
		    Integer Dif= valP1-valP2;
		    
		    //buscamos todos los que cumplen la condicion de diferencia de dias
			items = serviceNotif.selectListNotification(Dif);
			
			BigDecimal bdDefaultOperator=BigDecimal.ONE;
			try {
				bdDefaultOperator = getDefaultOperatorId(strUser,strLanguage);
			} catch (Exception e1) {
		    	log.error(e1.getMessage(), e1);
			}
			
			log.info("CapacityRequestClient. Se van a enviar: " + items.size() + " notificaciones.");
			
			for (int i=0; i<items.size(); i++ ){
				
				String notifInfo = items.get(i).getiDshipper() +
						strNotifSeparator+items.get(i).getRequestCode()+
						strNotifSeparator+items.get(i).getArrivingDate();
				
				
				     	// Se envia notificacion al usuario.
					 
				        	try {
								sendNotification(strNotifContractBankDocMissing, notifInfo,bdDefaultOperator,items.get(i).getSystemId());
								log.debug("[" + notifInfo + "] - sendNotification() OK finished.");
							} catch (Exception e) {
						    	log.error(e.getMessage(), e);
						    	// 5.- Se envia notificacion al usuario.
					        	try{
					        		notifInfo += strNotifSeparator + "has failed";
					        		sendNotification(strNotifContractBankDocMissing, notifInfo,bdDefaultOperator,items.get(i).getSystemId());
						        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
					        	}
						        catch( Exception e2 ) {
							    	log.error(e2.getMessage(), e2);
						        } 
							}  	
							
			}
			
		}//if envio=S
		
		//lanzamos el proceso que borra los log
		log.info("CapacityRequestClient. INICIO lanzamiento pro_logger_clean.", Calendar.getInstance().getTime());
			capacityRequestMapper.ejec_pro_logger_clean();
		log.info("CapacityRequestClient. FIN del proceso pro_logger_clean.", Calendar.getInstance().getTime());
	}

	
	
	private void sendNotification(String _notifTypeCode, String _info, BigDecimal bdDefaultOperator, BigDecimal systemId) throws Exception {		
    	int res = 0;
		NotificationBean notif = new NotificationBean();

		notif.setType_code(_notifTypeCode);	
		notif.setSystemId(systemId);
		notif.setOrigin(strBooking);
		notif.setInformation(_info);
		notif.setUser_id(strUser);  
		notif.setLanguage(strLanguage);
		notif.setIdn_user_group(bdDefaultOperator);
	
		
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
	
	private BigDecimal getDefaultOperatorId(String _user, String _lang) throws Exception{
		String defOperatorCode;
		BigDecimal defOperatorId = null;
		
		if(defOperatorId == null) {
			SystemParameterBean bean = new SystemParameterBean();
			bean.setDate(systemParameterMapper.getSysdate().get(0));
			bean.setParameter_name(strDefOperatorParamName);
			bean.setUser_id(_user);
			bean.setLanguage(_lang);
			systemParameterMapper.getStringSystemParameter(bean);
			if( bean==null || bean.getString_exit()==null ){
				// Se envia error tecnico para no mostrar error al usuario.
				throw new Exception("Error getting system parameter.");
			}
			defOperatorCode = bean.getString_exit();
			
			List<BigDecimal> lTmpIds = CRnotifMapper.selectGroupIdFromGroupCode(defOperatorCode);
			defOperatorId = lTmpIds.get(0);
		}
		
		return defOperatorId;
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

}

