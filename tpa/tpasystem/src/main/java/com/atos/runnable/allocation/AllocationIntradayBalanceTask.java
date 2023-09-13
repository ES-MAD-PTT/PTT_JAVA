package com.atos.runnable.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LanguageBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.CalculateAllocationBalanceBean;
import com.atos.exceptions.ValidationException;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.allocation.AllocationIntradayMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;

public class AllocationIntradayBalanceTask implements Serializable, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2712915729871196906L;
	private static final String strNotifTypeAllocationExecutionFinishedOK = "ALLOCATION.EXECUTION.FINISHED.OK";
	private static final String strNotifTypeAllocationExecutionFinishedError = "ALLOCATION.EXECUTION.FINISHED.ERROR";
	private static final String strNotifOrigin = "ALLOCATION";
	private static final String strNotifSeparator = "~";
	private static final String strAllocationTypeCommercial = "INTRADAY";
	private static final String strBalanceClosingTypeDefinitive = "DEFINITIVE";
	private static final String strUpdateBalanceY = "Y";
	
	private static final Logger log = LogManager.getLogger("com.atos.runnable.allocation.AllocationBalanceTask");
	
	private Date startDate;
	private Date endDate;
	private UserBean user;
	private LanguageBean lang;
	
	private ResourceBundle msgs;
	private AllocationIntradayMapper amMapper;
	private NotificationMapper notifMapper;	
	private BigDecimal idnSystem;
	
    public AllocationIntradayBalanceTask(Date startDate, Date endDate, UserBean user, LanguageBean lang, ResourceBundle msgs,
    		AllocationIntradayMapper amMapper, NotificationMapper notifMapper, BigDecimal idnSystem) {
    	this.startDate = startDate;
    	this.endDate = endDate;
    	this.user = user;
        this.lang = lang;
        this.msgs = msgs;
        this.amMapper = amMapper;
        this.notifMapper = notifMapper;
		this.idnSystem = idnSystem;
    }

    public void run() {
    	
    	Date startUpdatingDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	String notifInfo = sdf.format(startDate) + strNotifSeparator +
				sdf.format(endDate) + strNotifSeparator +
				sdf2.format(startUpdatingDate);	    	
    	
    	try{
        	log.debug("[" + notifInfo + "] - AllocationBalanceTask - run() start.");  
        	
			calculateAllocationBalance(user.getUsername(), lang.getLocale(), idnSystem);
    		log.debug("[" + notifInfo + "] - calculateAllocationBalance(), DB procedure, finished.");

			sendNotif2Operator(strNotifTypeAllocationExecutionFinishedOK, notifInfo, user, lang, idnSystem);
			sendNotif2Shippers(strNotifTypeAllocationExecutionFinishedOK, notifInfo, user, lang, idnSystem);
        	log.debug("[" + notifInfo + "] - sendNotification() OK finished.");
    	}
        catch( ValidationException ve ) {
	    	log.error(ve.getMessage(), ve);
	    	
        	// 5.- Se envia notificacion al usuario, con el error devuelto desde la BD.
        	try{
        		notifInfo += strNotifSeparator + ve.getMessage();
				sendNotif2Operator(strNotifTypeAllocationExecutionFinishedError, notifInfo, user, lang, idnSystem);
				sendNotif2Shippers(strNotifTypeAllocationExecutionFinishedError, notifInfo, user, lang, idnSystem);
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
		    	notifInfo += strNotifSeparator + msgs.getString("internal_error");
				sendNotif2Operator(strNotifTypeAllocationExecutionFinishedError, notifInfo, user, lang, idnSystem);
				sendNotif2Shippers(strNotifTypeAllocationExecutionFinishedError, notifInfo, user, lang, idnSystem);
	        	log.debug("[" + notifInfo + "] - sendNotification() Error finished.");
        	}
	        catch( Exception e2 ) {
		    	log.error(e2.getMessage(), e2);
	        }
        }
    }
	
	private void calculateAllocationBalance(String userId, String lang, BigDecimal idnSystem) throws Exception {
		
    	CalculateAllocationBalanceBean cabBean = new CalculateAllocationBalanceBean();
    	cabBean.setAllocationTypeCode(strAllocationTypeCommercial);
    	cabBean.setBalanceClosingTypeCode(strBalanceClosingTypeDefinitive);
    	cabBean.setUpdateBalance(strUpdateBalanceY);
    	cabBean.setUserName(userId);
    	cabBean.setLanguage(lang);
		cabBean.setIdnSystem(idnSystem);
    	
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
    }
    
	private List<BigDecimal> getAllShipperIdsForInsert() {
		
		List<ComboFilterNS> allShippers = amMapper.selectShipperIdForInsert();
		ArrayList<BigDecimal> allShipperIds = new ArrayList<BigDecimal>();
		for(ComboFilterNS combo :allShippers)
			allShipperIds.add(combo.getKey());
		
		return allShipperIds;
	}
		
	// Se supone que el usuario que lanza el proceso de repartos y balances es operador.
	private void sendNotif2Operator(String _notifTypeCode, String _info, UserBean _user, LanguageBean _lang,
			BigDecimal systemId) throws Exception {
		
		sendNotification(_notifTypeCode, _info, _user.getIdn_user_group(), _user, _lang, systemId);
	}
	
	private void sendNotif2Shippers(String _notifTypeCode, String _info, UserBean _user, LanguageBean _lang,
			BigDecimal systemId) throws Exception {
		List<BigDecimal> lRecipients = getAllShipperIdsForInsert();
		for(BigDecimal recipient :lRecipients)
			sendNotification(_notifTypeCode, _info, recipient, _user, _lang, systemId);
	}

	private void sendNotification(String _notifTypeCode, String _info, BigDecimal _recipient, UserBean _user,
			LanguageBean _lang, BigDecimal systemId) throws Exception {
    	int res = 0;
   	
		// Se genera la notificacion para el operador comuncando que ha acabado el proceso de repartos y balances.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);	
		notif.setOrigin(strNotifOrigin);
		notif.setInformation(_info);
		notif.setUser_id(_user.getUsername());
		notif.setIdn_user_group(_recipient);
		notif.setLanguage(_lang.getLocale());
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
}
