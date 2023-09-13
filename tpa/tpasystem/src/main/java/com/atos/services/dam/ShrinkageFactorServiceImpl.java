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

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ShrinkageFactorBean;
import com.atos.beans.dam.StrippingFactorBean;
import com.atos.filters.dam.ShrinkageFactorFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ShrinkageFactorMapper;

@Service("shrinkageFactorService")
public class ShrinkageFactorServiceImpl implements ShrinkageFactorService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private ShrinkageFactorMapper shrinkageFactorMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<ShrinkageFactorBean> selectShrinkageFactors(ShrinkageFactorFilter filter) {
		return shrinkageFactorMapper.selectShrinkageFactors(filter);

	}

	public Map<BigDecimal, Object> selectShrinkageFactorZoneSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = shrinkageFactorMapper.selectShrinkageFactorZoneSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertShrinkageFactor(ShrinkageFactorBean shrinkageFactor) throws Exception {

		int ins = shrinkageFactorMapper.insertShrinkageFactor(shrinkageFactor);
		if (ins != 1) {
			throw new Exception("-1");
		}

		return "0";

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
	public String deleteShrinkageFactor(ShrinkageFactorBean shrinkageFactor) throws Exception {

		// We calculate the end date, which will be the startDate -1 day

		Date startDateBd = shrinkageFactorMapper.getShrinkageFactorStarDate(shrinkageFactor);
		Date endDate = restarDiasFecha(startDateBd, 1);
		shrinkageFactor.setEndDate(endDate);

		int ins = shrinkageFactorMapper.deleteShrinkageFactor(shrinkageFactor);
		if (ins != 1) {
			throw new Exception("-10");
		}

		return "0";
	}

	public Date restarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); //
		calendar.add(Calendar.DATE, -dias); // numero de días a añadir, o restar en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	}
	
	@Override
	public List<ShrinkageFactorBean> getShrinkageFactor(ShrinkageFactorBean shrinkageFactor) {

		return shrinkageFactorMapper.getShrinkageFactors(shrinkageFactor);
	}
}
