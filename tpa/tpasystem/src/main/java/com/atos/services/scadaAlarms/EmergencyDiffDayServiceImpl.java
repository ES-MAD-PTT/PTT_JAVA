package com.atos.services.scadaAlarms;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.FileBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayDetailsBean;
import com.atos.beans.scadaAlarms.EmergencyDiffDayIsShipperBean;
import com.atos.beans.scadaAlarms.IdEventBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.scadaAlarms.EmergencyDifficultDayFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.scadaAlarms.EmergencyDiffDayMapper;

@Service("emergencyDiffDayService")
public class EmergencyDiffDayServiceImpl implements EmergencyDiffDayService {

	/**
	* 
	*/
	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private EmergencyDiffDayMapper emergencyDiffDayMapper;
	
	@Autowired
	private NotificationMapper notifMapper;
	
	private static final Logger log = LogManager.getLogger(EmergencyDiffDayServiceImpl.class);
	

	
	@Override
	public Map<BigDecimal, Object> selectEventType() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = emergencyDiffDayMapper.selectEventTypeCombo();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}
	
	@Override
	public Map<BigDecimal, Object> selectZone(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = emergencyDiffDayMapper.selectZoneCombo(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}
	
	@Override
	public List<EmergencyDiffDayBean> selectEmergencyDiffDay(EmergencyDifficultDayFilter filter) {
		return emergencyDiffDayMapper.selectEmergencyDiffDay(filter);
	}
	
	@Override
	public List<EmergencyDiffDayDetailsBean> selectEmergencyDiffDayDetails(EmergencyDiffDayIsShipperBean filter) {
		return emergencyDiffDayMapper.selectEmergencyDiffDayDetails(filter);
	}
	
	@Override
	public List<EmergencyDiffDayDetailsBean> selectEmergencyDiffDayAllDetails(EmergencyDiffDayIsShipperBean filter) {
		return emergencyDiffDayMapper.selectEmergencyDiffDayAllDetails(filter);
	}
	
	@Override
	public EmergencyDiffDayBean selectEmergencyDiffDayParent(BigDecimal idn_tso_event) {
		return emergencyDiffDayMapper.selectEmergencyDiffDayParent(idn_tso_event);
	}
	
	@Override
	public List<String> selectShippers() {
		return emergencyDiffDayMapper.selectShippers();
	}
	
	@Transactional(rollbackFor = { Throwable.class }) 
	public String insertEvent(EmergencyDiffDayBean emergencyDiffDayBean, FileBean file) throws Exception {
		
		if(file!=null){
			emergencyDiffDayBean.setOpening_file_name(file.getFileName());
			emergencyDiffDayBean.setOpening_binary_data(file.getContents());
		
			int ins1 = emergencyDiffDayMapper.insertEvent(emergencyDiffDayBean);
			if (ins1 != 1) {
				throw new Exception("-1");
			}
		}
		
		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class }) 
	public String insertEventShipper(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean) throws Exception {
		
		int ins1 = emergencyDiffDayMapper.insertEventShipper(emergencyDiffDayDetailsBean);
		if (ins1 != 1) {
			throw new Exception("-1");
		}
		
		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class }) 
	public String updateEvent(EmergencyDiffDayBean emergencyDiffDayBean) throws Exception {
		int ins1 = emergencyDiffDayMapper.updateEvent(emergencyDiffDayBean);
		if (ins1 != 1) {
			throw new Exception("-1");
		}
		
		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class }) 
	public String updateStatusEvent(EmergencyDiffDayBean emergencyDiffDayBean) throws Exception {
		int ins1 = emergencyDiffDayMapper.updateStatusEvent(emergencyDiffDayBean);
		if (ins1 != 1) {
			throw new Exception("-1");
		}
		
		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class }) 
	public String updateEventShipper(EmergencyDiffDayDetailsBean emergencyDiffDayDetailsBean) throws Exception {
		int ins1 = emergencyDiffDayMapper.updateEventShipper(emergencyDiffDayDetailsBean);
		if (ins1 != 1) {
			throw new Exception("-1");
		}
		
		return "0";
	}
	
	public IdEventBean getId(){
		IdEventBean bean = new IdEventBean();
		bean.setDate(emergencyDiffDayMapper.getSysdate());
		bean.setType("TSO.EVENT");
		emergencyDiffDayMapper.getIdEvent(bean);
		return bean;
	}
	
	@Override
	public BigDecimal selectIdnUserGroup(String shipper) {
		return emergencyDiffDayMapper.selectIdnUserGroup(shipper);
	}
	
	@Override
	public void sendNotification(String notifTypeCode, String origin, String info, BigDecimal userGroupId, BigDecimal systemId) throws Exception {		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	int res = 0;
   	
		// Se genera la notificacion para el shipper, correspondiente a que el operador ha generado un evento.
		NotificationBean notif = new NotificationBean();
		notif.setType_code(notifTypeCode);
		notif.setSystemId(systemId);
		notif.setOrigin(origin);
		notif.setInformation(info);
		notif.setIdn_user_group(userGroupId);
		notif.setUser_id((String)SecurityUtils.getSubject().getPrincipal());
		notif.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		
		notifMapper.getCreateNotification(notif);
		if( notif==null || notif.getInteger_exit()==null ){
			throw new Exception(msgs.getString("error_sending_notification_to_shipper"));
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
	
	@Override
	public String selectEventTypeFromId(String idn_tso_event_tpe) {
		return emergencyDiffDayMapper.selectEventTypeFromId(idn_tso_event_tpe);
	}
	
	@Override
	public StreamedContent getOpeningOperatorFile(EmergencyDiffDayBean bean) throws Exception {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		EmergencyDiffDayBean b = emergencyDiffDayMapper.selectOpenOperatorFile(bean);

		
		if (b == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));

		if(b.getOpening_binary_data()!=null) {
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getOpening_binary_data());
			return new DefaultStreamedContent(ba, "", b.getOpening_file_name());
		}
		return null;
	}
	
	@Override
	public StreamedContent getClosingOperatorFile(EmergencyDiffDayBean bean) throws Exception {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		EmergencyDiffDayBean b = emergencyDiffDayMapper.selectCloseOperatorFile(bean);

		
		if (b == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));

		if(b.getClosing_binary_data()!=null) {
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getClosing_binary_data());
			return new DefaultStreamedContent(ba, "", b.getClosing_file_name());
		}
		return null;
	}
	
	@Override
	public StreamedContent getOpeningShipperFile(EmergencyDiffDayDetailsBean bean) throws Exception {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		EmergencyDiffDayDetailsBean b = emergencyDiffDayMapper.selectOpenShipperFile(bean);

		
		if (b == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));

		if(b.getOpening_binary_data()!=null) {
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getOpening_binary_data());
			return new DefaultStreamedContent(ba, "", b.getOpening_file_name());
		}
		return null;
	}
	
	@Override
	public StreamedContent getClosingShipperFile(EmergencyDiffDayDetailsBean bean) throws Exception {

		ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msg");

		EmergencyDiffDayDetailsBean b = emergencyDiffDayMapper.selectCloseShipperFile(bean);

		
		if (b == null)
			throw new ValidationException(msgs.getString("no_file_to_download"));

		if(b.getClosing_binary_data()!=null) {
			ByteArrayInputStream ba = new ByteArrayInputStream(b.getClosing_binary_data());
			return new DefaultStreamedContent(ba, "", b.getClosing_file_name());
		}
		return null;
	}
	
	public String getZoneDesc(BigDecimal zone) {
		return emergencyDiffDayMapper.getZoneDesc(zone);
	}
}
