package com.atos.services.balancing;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilter;
import com.atos.beans.NotificationBean;
import com.atos.beans.UserBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.BalanceManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.balancing.BalanceManagementMapper;
import com.atos.utils.Constants;

@Service("BalanceManagService")
public class BalanceManagementServiceImpl implements BalanceManagementService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2908909901514312100L;
	private static final String strNotifTypeBalanceClosingFinishedOK = "BALANCE.CLOSING.FINISHED.OK";
	private static final String strNotifTypeBalanceClosingFinishedError = "BALANCE.CLOSING.FINISHED.ERROR";
	private static final String strNotifOrigin = "BALANCING";
	private static final String strNotifSeparator = "~";
	
	private static final Logger log = LogManager.getLogger("com.atos.services.balancing.BalanceManagementServiceImpl");
	
	@Autowired
	private BalanceManagementMapper bmMapper;
	
	@Autowired
	private NotificationMapper notifMapper;
	
	public Date selectOpenPeriodFirstDay(Map<String, Object> params) {

		return bmMapper.selectOpenPeriodFirstDay(params);
	}
	
	public Map<String, Object> selectBalanceClosingTypeCodes(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ComboFilter> list = bmMapper.selectBalanceClosingTypeCodes();
		for (ComboFilter combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public void closeBalance(BalanceManagementFilter _bean, UserBean _user) throws Exception {
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");
    	Date startUpdatingDate = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("MMMM/yyyy");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String notifInfo = sdf.format(_bean.getClosingDate()) + strNotifSeparator + sdf2.format(startUpdatingDate)
				+ strNotifSeparator + _bean.getCodSystem();
    	
    	try{
			// Primero se valida el tipo de usuario.
			if(_user.isUser_type(Constants.SHIPPER))
				throw new ValidationException(msgs.getString("bal_man_shipper_not_allowed_error"));
			
			closeBalanceInBD(_bean);
			
			sendNotification(strNotifTypeBalanceClosingFinishedOK, notifInfo, _user, _bean.getLang(),
					_bean.getIdnSystem());
			
    	} catch(Exception e){
        	try{
				sendNotification(strNotifTypeBalanceClosingFinishedError, notifInfo, _user, _bean.getLang(),
						_bean.getIdnSystem());
        	}
	        catch( Exception e3 ) {
		    	log.error(e3.getMessage(), e3);
	        }
    		throw e;
    	}
	}
	
	public void closeBalanceInBD(BalanceManagementFilter _bean) throws Exception {

		String errorMsg = null;
		String errorDetails = null;
		bmMapper.closeBalance(_bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(_bean == null || _bean.getErrorCode()==null)
			throw new Exception("Error closing balance.");
		
		int res = _bean.getErrorCode().intValue();
		if( res != 0) {
			errorMsg = _bean.getErrorDesc();
			errorDetails = _bean.getErrorDetail();
			if(errorDetails != null)
				errorMsg += ":" + System.getProperty("line.separator") + errorDetails; 
					
			if( res >= 1000 )	// Errores funcionales.
	    		throw new ValidationException(errorMsg);
			else				// Errores tecnicos.
	    		throw new Exception(errorMsg);
		}
	}
	
	private void sendNotification(String _notifTypeCode, String _info, UserBean _user, String _lang,
			BigDecimal systemId) throws Exception {
    	int res = 0;
   	
		// Se genera la notificacion para el operador comuncando que ha acabado el proceso de repartos y balances.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(_notifTypeCode);	
		notif.setOrigin(strNotifOrigin);
		notif.setSystemId(systemId);
		notif.setInformation(_info);
		notif.setUser_id(_user.getUsername());
		notif.setIdn_user_group(_user.getIdn_user_group());
		notif.setLanguage(_lang);
		
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
