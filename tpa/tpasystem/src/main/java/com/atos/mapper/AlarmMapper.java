package com.atos.mapper;

import java.util.List;

import com.atos.beans.AlarmBean;
import com.atos.beans.ScadaAlarmTagNameBean;

public interface AlarmMapper {
	
	public List<ScadaAlarmTagNameBean> getScadaTagNameInfo();
	
	public int insertScadaAlarm(AlarmBean alarm);
	
	public List<String> getAlarmIdSecuential(String query);

}
