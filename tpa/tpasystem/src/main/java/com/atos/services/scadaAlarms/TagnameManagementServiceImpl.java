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
import com.atos.beans.scadaAlarms.ScadaLabelBean;
import com.atos.beans.scadaAlarms.ScadaPointBean;
import com.atos.beans.scadaAlarms.TagnameManagementBean;
import com.atos.filters.scadaAlarms.TagnameManagementFilter;
import com.atos.mapper.NotificationMapper;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.scadaAlarms.TagnameManagementMapper;
import com.atos.utils.Constants;

@Service("tagnameManagementService")
public class TagnameManagementServiceImpl implements TagnameManagementService {

	/**
	* 
	*/
	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private TagnameManagementMapper tagnameManagementMapper;
	@Autowired
	private SystemParameterMapper systemParameterMapper;

	@Override
	public Map<BigDecimal, Object> selectScadaTagName(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = tagnameManagementMapper.selectScadaTagName(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}
	
	@Override
	public Map<BigDecimal, Object> selectPoints(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = tagnameManagementMapper.selectPoints(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}
	@Override
	public Map<BigDecimal, Object> selectLabels() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = tagnameManagementMapper.selectLabels();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}

	public boolean selectPermiso(TagnameManagementFilter filter){
		
		BigDecimal contador = tagnameManagementMapper.selectPermiso(filter);
		if (contador.compareTo(BigDecimal.ZERO)==0 ) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public List<TagnameManagementBean> selectTagnameManagement(TagnameManagementFilter filter) {
		return tagnameManagementMapper.selectTagnameManagement(filter);
	}
	
	

	// En caso de que se quiera enviar una notificacion al usuario, se ha de indicar un systemId distinto de null.
	@Transactional(rollbackFor = { Throwable.class })
	public String updateTagnameManagement(TagnameManagementBean tagnameManagement) throws Exception {

		if (!tagnameManagement.isIs_binary()){
			tagnameManagement.setOn_off_alarm_threshold(null);
		}else{
			tagnameManagement.setMax_alarm_threshold(null);
			tagnameManagement.setMin_alarm_threshold(null);
		}
		
		int upd2 = tagnameManagementMapper.updateTagnameManagement(tagnameManagement);
		if (upd2 != 1) {
			throw new Exception("-1");
		
		}
		return "0";
	}


/*	public SystemParameterBean getSystemParameter(String str) {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(systemParameterMapper.getSysdate().get(0));
		bean.setParameter_name(str);
		bean.setUser_id((String) SecurityUtils.getSubject().getPrincipal());
		bean.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		systemParameterMapper.getIntegerSystemParameter(bean);
		return bean;
	}
*/
	public String insertScadaLabel(ScadaLabelBean label) throws Exception{
		
		List<String> list = tagnameManagementMapper.getLabelId(label);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		
		
		int ins =tagnameManagementMapper.insertScadaLabel(label);
		if(ins != 1){
			throw new Exception("-2");
		}
		
		return "0";
	}
	
	public String insertScadaPoint(ScadaPointBean point) throws Exception{
		
		List<String> list = tagnameManagementMapper.getPointId(point);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		
		
		int ins =tagnameManagementMapper.insertScadaPoint(point);
		if(ins != 1){
			throw new Exception("-2");
		}
		
		return "0";
	}
	
	public String insertTagnameManagement(TagnameManagementBean tagnameManagement) throws Exception {
		
	List<String> list = tagnameManagementMapper.getTagnameId(tagnameManagement);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		
		//en el alta siempre es enabled Yes
		tagnameManagement.setEnabled("Y");
		
		int ins =tagnameManagementMapper.insertTagnameManagements(tagnameManagement);
		if(ins != 1){
			throw new Exception("-2");
		}
		
		
		return "0";
	}



}
