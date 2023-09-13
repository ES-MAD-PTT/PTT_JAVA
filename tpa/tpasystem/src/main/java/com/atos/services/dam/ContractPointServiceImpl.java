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
import com.atos.beans.dam.ContractPointBean;
import com.atos.filters.dam.ContractPointFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.ContractPointMapper;

@Service("contractPointService")
public class ContractPointServiceImpl implements ContractPointService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private ContractPointMapper contractPointMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<ContractPointBean> selectContractPoints(ContractPointFilter filter) {
		return contractPointMapper.selectContractPoints(filter);

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

	/*@Override
	public List<String> selectIds(String query) {
		return contractPointMapper.selectIds(query);
	}*/

	// offshore
	@Override
	public Map<BigDecimal, Object> selectIdsSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectIdsComboSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectPointTypes() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectPointTypes();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	// offshore
	@Override
	public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectAreas(ContractPointBean contractPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectAreas(contractPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	// offshore
	@Override
	public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectZonesSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	// offshore
	@Override
	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectAreasSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertContractPoint(ContractPointBean contractPoint) throws Exception {

		List<String> list = contractPointMapper.getContractPointCode(contractPoint);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		int ins = contractPointMapper.insertContractPoint(contractPoint);
		if (ins != 1) {
			throw new Exception("-2");
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateContractPoint(ContractPointBean contractPoint) throws Exception {
		int upd = contractPointMapper.updateContractPoint(contractPoint);
		if (upd != 1) {
			throw new Exception("-1");
		}

		return "0";
	}

	@Override
	public Map<BigDecimal, Object> selectAreaContractPoint(ContractPointBean newContractPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectAreaContractPoint(newContractPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectSubAreaContractPoint(ContractPointBean newContractPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractPointMapper.selectSubAreaContractPoint(newContractPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public Date restarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); //
		calendar.add(Calendar.DATE, -dias); // numero de días a añadir, o restar
											// en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos
									// días añadidos
	}
}
