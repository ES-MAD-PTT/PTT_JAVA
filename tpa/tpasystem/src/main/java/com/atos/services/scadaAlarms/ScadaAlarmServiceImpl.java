package com.atos.services.scadaAlarms;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.NotificationBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.scadaAlarms.ScadaAlarmBean;
import com.atos.filters.scadaAlarms.ScadaAlarmsFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.scadaAlarms.ScadaAlarmMapper;
import com.atos.utils.Constants;

@Service("scadaAlarmService")
public class ScadaAlarmServiceImpl implements ScadaAlarmService {

	/**
	* 
	*/
	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private ScadaAlarmMapper scadaAlarmMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;
	@Autowired
	private NotificationMapper notifMapper;

	@Autowired
	private SystemParameterMapper sysParMapper;
	
	public List<ScadaAlarmBean> selectScadaAlarms(ScadaAlarmsFilter filter) {
		return scadaAlarmMapper.selectScadaAlarms(filter);
	}

	// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
	@Transactional(rollbackFor = { Throwable.class })
	public String updateScadaAlarm(ScadaAlarmBean scadaAlarm, BigDecimal _systemId) throws Exception {

		int upd2 = scadaAlarmMapper.updateScadaAlarm(scadaAlarm);
		if (upd2 != 1) {
			throw new Exception("-1");
		}

		if(_systemId!=null){
			NotificationBean notif = new NotificationBean();
			notif.setType_code("SCADA.ALARM_ACTIVATED");
			notif.setSystemId(_systemId);
			//CH713
			//notif.setOrigin("ALARMS");
			notif.setOrigin("EVENTS"); // es un campo informativo solamente
			
			notif.setInformation(null);
			// Cuando user_group es null, la notificacion va a todos los usuarios que tengan permiso para recibirlo. 
			// Se tiene en cuenta tambien que el usuario tenga un rol en el sistema onshore/offshore igual a la pantalla desde la que se lanza la notificacion.
			notif.setIdn_user_group(null);
			notif.setUser_id((String) SecurityUtils.getSubject().getPrincipal());
			notif.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
	
			notifMapper.getCreateNotification(notif);
			if (notif == null || notif.getInteger_exit() == null || notif.getInteger_exit().intValue() != 0) {
				return notif.getError_desc();
			}
		}
		return "0";
	}

	//offshore
	@Override
	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = scadaAlarmMapper.selectAreasSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public String selectArea(BigDecimal idn_area) {
		List<String> list = scadaAlarmMapper.selectArea(idn_area);
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return "";
		}
	}

	public SystemParameterBean getSystemParameter(String str) {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String) SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}

	//offshore
	@Override
	public Map<BigDecimal, Object> selectAlarmNameSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = scadaAlarmMapper.selectAlarmNameSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}

	
	
	@Override
	public Map<BigDecimal, Object> selectAlarmLabel(ScadaAlarmBean it) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = scadaAlarmMapper.selectAlarmLabel(it);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}

	@Override
	public Map<BigDecimal, Object> selectAlarmMetering(ScadaAlarmBean it) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = scadaAlarmMapper.selectAlarmMetering(it);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<String, Object> selectAlarmBinary(ScadaAlarmBean it) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilter> list = scadaAlarmMapper.selectAlarmBinary(it);
		for (ComboFilter combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
			it.setBinary(combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<String, Object> selectAlarmType(ScadaAlarmBean it) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilter> list = scadaAlarmMapper.selectAlarmType(it);
		for (ComboFilter combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	

	public String insertScadaAlarms(ScadaAlarmBean scadaAlarm) throws Exception {
		
		//el origen de esta alarma es por pantalla 
		scadaAlarm.setOrigin("TPA");
		
		//obtenemos el nuevo codigo de alarma
		scadaAlarm.setAlarm_id(getNewAlarmId());
		
		
		int ins =scadaAlarmMapper.insertScadaAlarms(scadaAlarm);
		if(ins != 1){
			throw new Exception("-1");
		}
		
		
		return "0";
	}
	
	
private String getNewAlarmId() throws Exception {
		
		ElementIdBean tmpBean = new ElementIdBean();
		tmpBean.setGenerationCode(Constants.ALARM);
		// Si se deja la fecha a nulo, en BD se toma systemdate.
		tmpBean.setDate(null);
		tmpBean.setUser((String)SecurityUtils.getSubject().getPrincipal());
		tmpBean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());

		sysParMapper.getElementId(tmpBean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(tmpBean == null || (tmpBean.getIntegerExit() != 0))
			throw new Exception(tmpBean.getErrorDesc());
		
		return tmpBean.getId();
	}


}
