package com.atos.services.scadaAlarms;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;


import com.atos.beans.scadaAlarms.ScadaAlarmBean;

public interface ScadaAlarmPanelService extends Serializable{

	public List<ScadaAlarmBean> selectScadaAlarmsPanel(TreeMap<String,String> map);
	

}
