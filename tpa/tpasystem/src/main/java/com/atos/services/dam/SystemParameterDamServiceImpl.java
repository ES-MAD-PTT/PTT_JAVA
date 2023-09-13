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
import com.atos.beans.dam.SystemParameterDamBean;
import com.atos.filters.dam.SystemParameterDamFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.SystemParameterDamMapper;

@Service("systemParameterDamService")
public class SystemParameterDamServiceImpl implements SystemParameterDamService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private SystemParameterDamMapper systemParameterDamMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<SystemParameterDamBean> selectSystemParameters(SystemParameterDamFilter filter) {
		return systemParameterDamMapper.selectSystemParameters(filter);

	}

	public Map<BigDecimal, Object> selectSystemParameterParameters(BigDecimal idn_module) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = systemParameterDamMapper.selectSystemParameterParameters(idn_module);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	public Map<BigDecimal, Object> selectSystemParameterModules() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = systemParameterDamMapper.selectSystemParameterModules();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	@Override
	public String getCheckValueSystemParameter(SystemParameterDamBean systemParameter) throws Exception {

		String parameter_code = systemParameterDamMapper.getParameterCode(systemParameter);
		systemParameter.setParameter_code(parameter_code);

		String parameter_desc = systemParameterDamMapper.getParameterDesc(systemParameter);
		systemParameter.setParameter_desc(parameter_desc);

		systemParameterDamMapper.getCheckValueSystemParameter(systemParameter);
		if (systemParameter.getValid() != 0) {
			throw new Exception(systemParameter.getError_desc());
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertSystemParameter(SystemParameterDamBean systemParameter) throws Exception {

		int ins = systemParameterDamMapper.insertSystemParameter(systemParameter);
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
	public String deleteSystemParameter(SystemParameterDamBean systemParameter) throws Exception {

		// We calculate the end date, which will be the startDate -1 day
		Date startDateBd = systemParameterDamMapper.getSystemParameterStarDate(systemParameter);

		// Date endDate = restarDiasFecha(shrinkageFactor.getStartDate(),1);
		Date endDate = restarDiasFecha(startDateBd, 1);
		systemParameter.setEndDate(endDate);

		int ins = systemParameterDamMapper.deleteSystemParameter(systemParameter);
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
	public BigDecimal obtenerModuloInicial() {

		BigDecimal idnModulo = systemParameterDamMapper.obtenerModuloInicial();
		return idnModulo;
	}

	@Override
	public BigDecimal obtenerModuloTariff() {

		BigDecimal idnModulo = systemParameterDamMapper.obtenerModuloTariff();
		return idnModulo;
	}

}
