package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.NotificationBean;
import com.atos.beans.balancing.InstructedOperationFlowShipperFileBean;
import com.atos.beans.balancing.InstructedOperationFlowShippersBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.balancing.InstructedOperationFlowShippersFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.balancing.InstructedOperationFlowShippersMapper;

@Service("InstructedOperationFlowShippersService")
public class InstructedOperationFlowShippersServiceImpl implements InstructedOperationFlowShippersService {
	
	private static final long serialVersionUID = 1257016484045583776L;
	
	@Autowired
	private InstructedOperationFlowShippersMapper mapper;
	
	@Autowired
	private NotificationMapper notifMapper;

	public Map<BigDecimal, Object> selectShipperId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public Map<BigDecimal, Object> selectZoneId(BigDecimal idn_system) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectZonesSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public List<InstructedOperationFlowShippersBean> search(InstructedOperationFlowShippersFilter filter) {
		return mapper.selectInstructedOperationFlowShippers(filter);
	}
	
	public int updateComments(InstructedOperationFlowShippersBean bean) {
		return mapper.updateComments(bean);
	}
	
	@Override
	public Map<BigDecimal, Object> selectTimestampIds(InstructedOperationFlowShippersFilter filters) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectTimestampIds(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 	
	}
	
	@Override
	public void sendNotification(String notifTypeCode, String origin, String info, BigDecimal userGroupId, BigDecimal systemId) throws Exception {		
    	// Utilizo un ResourceBundle local por si el scope fuera Session o Application. En estos casos no se actualizaria el idioma.
    	ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(),"msg");

    	int res = 0;
   	
		// Se genera la notificacion para el shipper, correspondiente a que el operador ha realizado la publicación.
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
	
	@Transactional( rollbackFor = { Throwable.class })
	public String insertFile(UploadedFile file, InstructedOperationFlowShippersBean item) throws Exception {
		InstructedOperationFlowShipperFileBean bean = new InstructedOperationFlowShipperFileBean();

		bean.setFile_name(file.getFileName());
		bean.setBinary_data(file.getContents());
		bean.setContent_type(file.getContentType());
		
		int ins = mapper.insertFileGasFlow(bean);
		if(ins != 1){
			throw new Exception("-2");
		}
		
		item.setIdn_intraday_gas_flow_file(bean.getIdn_intraday_gas_flow_file());

		return "0";

	}

	@Transactional( rollbackFor = { Throwable.class })
	public String updateFile(UploadedFile file, InstructedOperationFlowShippersBean item) throws Exception {
		
		List<InstructedOperationFlowShipperFileBean> l = mapper.selectFileGasFlow(item.getIdn_intraday_gas_flow_file());
		
		if(l.size()==0) {
			return "-1";
		}
		
		InstructedOperationFlowShipperFileBean bean = new InstructedOperationFlowShipperFileBean();
		bean.setIdn_intraday_gas_flow_file(item.getIdn_intraday_gas_flow_file());
		bean.setFile_name(file.getFileName());
		bean.setBinary_data(file.getContents());
		bean.setContent_type(file.getContentType());
		
		int ins = mapper.updateFileGasFlow(bean);
		if(ins != 1){
			throw new Exception("-2");
		}
		
		item.setIdn_intraday_gas_flow_file(bean.getIdn_intraday_gas_flow_file());

		return "0";

	}
	
	@Override
	public StreamedContent getFile(BigDecimal idn_intraday_gas_flow_file) {
		List<InstructedOperationFlowShipperFileBean> l = mapper.selectFileGasFlow(idn_intraday_gas_flow_file);
		if(l.size()==0){
			return null;
		} else {
			InstructedOperationFlowShipperFileBean bean = l.get(0);
			ByteArrayInputStream ba = new ByteArrayInputStream(bean.getBinary_data());
			return new DefaultStreamedContent(ba, bean.getContent_type(), bean.getFile_name());
		}
	}
	
}
