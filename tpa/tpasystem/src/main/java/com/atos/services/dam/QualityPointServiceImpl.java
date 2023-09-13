package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
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
import com.atos.beans.LanguageBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.UserBean;
import com.atos.beans.dam.QualityPointBean;
import com.atos.exceptions.ValidationException;
import com.atos.filters.dam.QualityPointFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.QualityPointMapper;

@Service("qualityPointService")
public class QualityPointServiceImpl implements QualityPointService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private QualityPointMapper qualityPointMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<QualityPointBean> selectQualityPoints(QualityPointFilter filter) {
		return qualityPointMapper.selectQualityPoints(filter);
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

	
	@Override
	public Map<BigDecimal, Object> selectPointTypes() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = qualityPointMapper.selectPointTypes();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectSystems() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = qualityPointMapper.selectSystem();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertQualityPoint(QualityPointBean qualityPoint) throws Exception {

		List<String> list = qualityPointMapper.getQualityPointCode(qualityPoint);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		
		
		int ins = qualityPointMapper.insertQualityPoint(qualityPoint);
		if (ins != 1) {
			throw new Exception("-2");
		}
		
		//No estoy segura de que haya que insertar aqui para los Quality ¡¡¡
		int ins2 = qualityPointMapper.insertQualityPointParam(qualityPoint);
		if (ins2 != 1) {
			throw new Exception("-3");

		}

		
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateQualityPoint(QualityPointBean qualityPoint) throws Exception {

		Date endDatePantalla = qualityPoint.getEndDate();

		int upd = qualityPointMapper.updateQualityPoint(qualityPoint);
		if (upd != 1) {
			throw new Exception("-1");
		}

		
/*		
		// We calculate the end date, which will be the startDate -1 day
		Date startDateBd = qualityPointMapper.getQualityPointParamStarDate(qualityPoint);
		Date endDate = restarDiasFecha(startDateBd, 1);
		qualityPoint.setEndDate(endDate);

		int ins = qualityPointMapper.deleteQualityPointParam(qualityPoint);
		if (ins != 1) {
			throw new Exception("-10");
		}
		
		qualityPoint.setEndDate(endDatePantalla);
		int ins2 = qualityPointMapper.insertQualityPointParam(qualityPoint);
		if (ins2 != 1) {
			throw new Exception("-2");
		}
*/
		return "0";
	}

	public Date restarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); //
		calendar.add(Calendar.DATE, -dias); // numero de días a añadir, o restar
											// en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	}

	// offshore
	@Override
	public Map<BigDecimal, Object> selectQualityPointSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = qualityPointMapper.selectQualityPointSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
}
