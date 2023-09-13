package com.atos.services.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;

import com.atos.beans.scadaAlarms.ScadaAlarmBean;
import com.atos.filters.scadaAlarms.ScadaAlarmsFilter;

public interface ScadaAlarmService extends Serializable{

	public List<ScadaAlarmBean> selectScadaAlarms(ScadaAlarmsFilter filter);
	
	public SystemParameterBean getSystemParameter(String str);

	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system);//offshore;

	public String selectArea(BigDecimal idn_area);

	// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
	public String updateScadaAlarm(ScadaAlarmBean scadaAlarm, BigDecimal _systemId) throws Exception;
	public String insertScadaAlarms(ScadaAlarmBean scadaAlarm) throws Exception;
	
	
	public Map<BigDecimal, Object> selectAlarmNameSystem(BigDecimal idn_system);//offshore;
	
	public Map<BigDecimal, Object> selectAlarmLabel(ScadaAlarmBean it);
	public Map<BigDecimal, Object> selectAlarmMetering(ScadaAlarmBean it);
	public Map<String, Object> selectAlarmBinary(ScadaAlarmBean it);
	public Map<String, Object> selectAlarmType(ScadaAlarmBean it);
	

}
