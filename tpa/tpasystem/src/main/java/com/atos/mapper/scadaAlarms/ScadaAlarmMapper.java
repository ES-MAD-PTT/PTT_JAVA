package com.atos.mapper.scadaAlarms;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.scadaAlarms.ScadaAlarmBean;
import com.atos.filters.scadaAlarms.ScadaAlarmsFilter;

public interface ScadaAlarmMapper {

	public List<ScadaAlarmBean> selectScadaAlarms(ScadaAlarmsFilter filters);

	public List<ScadaAlarmBean> selectScadaAlarmsPanel(TreeMap<String,String> map);

	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system);//offshore
	
	
	public List<String> selectArea(BigDecimal idn_area);
	public int updateScadaAlarm(ScadaAlarmBean scadaAlarm);
	
	public int insertScadaAlarms(ScadaAlarmBean scadaAlarm) throws Exception;

	public List<ComboFilterNS> selectAlarmNameSystem(BigDecimal idn_system); //offshore
	
	
	public List<ComboFilterNS> selectAlarmLabel(ScadaAlarmBean it);
	public List<ComboFilterNS> selectAlarmMetering(ScadaAlarmBean it);
	public List<ComboFilter> selectAlarmBinary(ScadaAlarmBean it);
	public List<ComboFilter> selectAlarmType(ScadaAlarmBean it);
	
	
}
