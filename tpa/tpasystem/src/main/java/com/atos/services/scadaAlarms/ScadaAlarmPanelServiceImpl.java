package com.atos.services.scadaAlarms;

import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.atos.beans.scadaAlarms.ScadaAlarmBean;
import com.atos.mapper.scadaAlarms.ScadaAlarmMapper;

@Service("scadaAlarmPanelService")
public class ScadaAlarmPanelServiceImpl implements ScadaAlarmPanelService {

	/**
	* 
	*/
	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private ScadaAlarmMapper scadaAlarmMapper;
	public List<ScadaAlarmBean> selectScadaAlarmsPanel(TreeMap<String,String> map) {
		return scadaAlarmMapper.selectScadaAlarmsPanel(map);
	}

}
