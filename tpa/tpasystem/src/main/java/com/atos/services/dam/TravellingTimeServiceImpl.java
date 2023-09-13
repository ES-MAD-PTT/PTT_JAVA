package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.TravellingTimeBean;
import com.atos.filters.dam.TravellingTimeFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.TravellingTimeMapper;

@Service("travellingTimeService")
public class TravellingTimeServiceImpl implements TravellingTimeService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private TravellingTimeMapper travellingTimeMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<TravellingTimeBean> selectTravellingTimes(TravellingTimeFilter filter) {
		return travellingTimeMapper.selectTravellingTimes(filter);
	}

	public List<BigDecimal> selectTravellingTimeDays(TravellingTimeBean travellingTime) {
		return travellingTimeMapper.selectTravellingTimeDays(travellingTime);
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
	public Map<BigDecimal, Object> getIdsOriPointSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = travellingTimeMapper.getIdsOriPointSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> getIdsDestPointSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = travellingTimeMapper.getIdsDestPointSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertTravellingTime(TravellingTimeBean travellingTime) throws Exception {

		List<String> list = travellingTimeMapper.getTravellingTimeId(travellingTime);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}

		int ins = travellingTimeMapper.insertTravellingTime(travellingTime);
		if (ins != 1) {
			throw new Exception("-2");
		}

		return "0";
	}

	@Override
	public String getOriginCode(TravellingTimeBean travellingTime) {
		String originCode = travellingTimeMapper.getOriginCode(travellingTime);
		return originCode;
	}
	
	@Override
	public String getDestCode(TravellingTimeBean travellingTime) {
		String destCode = travellingTimeMapper.getDestCode(travellingTime);
		return destCode;
	}
	
}
