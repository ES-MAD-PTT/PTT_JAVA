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
import com.atos.beans.dam.NominationPointBean;
import com.atos.filters.dam.NominationPointFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.NominationPointMapper;

@Service("nominationPointService")
public class NominationPointServiceImpl implements NominationPointService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private NominationPointMapper nominationPointMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<NominationPointBean> selectNominationPoints(NominationPointFilter filter) {
		return nominationPointMapper.selectNominationPoints(filter);

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
		return nominationPointMapper.selectIds(query);
	}*/

	// offshore
	@Override
	public Map<BigDecimal, Object> selectIdsSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationPointMapper.selectIdsComboSystem(idn_system);
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
		List<ComboFilterNS> list = nominationPointMapper.selectPointTypes();
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
		List<ComboFilterNS> list = nominationPointMapper.selectSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectAreas(NominationPointBean nominationPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationPointMapper.selectAreas(nominationPoint);
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
		List<ComboFilterNS> list = nominationPointMapper.selectZonesSystem(idn_system);
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
		List<ComboFilterNS> list = nominationPointMapper.selectAreasSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertNominationPoint(NominationPointBean nominationPoint) throws Exception {

		List<String> list = nominationPointMapper.getNominationPointCode(nominationPoint);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		int ins = nominationPointMapper.insertNominationPoint(nominationPoint);
		if (ins != 1) {
			throw new Exception("-2");
		}
		int ins2 = nominationPointMapper.insertNominationPointParam(nominationPoint);
		if (ins2 != 1) {
			throw new Exception("-3");
		}
		// ** copiamos el punto con la categoria de Contract
		// 20/05/2021 AGA se elimina la insercion del contract point ya que se crea una pantalla especifica para ello
/*		int ins3 = nominationPointMapper.insertContractPoint(nominationPoint);
		if (ins3 != 1) {
			throw new Exception("-4");
		}*/

		// 22/7/2016 se inserta solo en la tabla de puntos....

		/*
		 * int ins4=
		 * nominationPointMapper.insertNominationPointParam(nominationPoint);
		 * if(ins4!=1){ throw new Exception("-5"); }
		 */
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateNominationPoint(NominationPointBean nominationPoint) throws Exception {
		Date endDatePantalla = nominationPoint.getEndDate();

		int upd = nominationPointMapper.updateNominationPoint(nominationPoint);
		if (upd != 1) {
			throw new Exception("-1");
		}

		// We calculate the end date, which will be the startDate -1 day
		Date startDateBd = nominationPointMapper.getNominationPointParamStarDate(nominationPoint);
		Date endDate = restarDiasFecha(startDateBd, 1);
		nominationPoint.setEndDate(endDate);

		int ins = nominationPointMapper.deleteNominationPointParam(nominationPoint);
		if (ins != 1) {
			throw new Exception("-10");
		}
		//

		// to do insert instead of update because they are historical
		nominationPoint.setEndDate(endDatePantalla);
		int ins2 = nominationPointMapper.insertNominationPointParam(nominationPoint);

		if (ins2 != 1) {
			throw new Exception("-2");
		}
		return "0";
	}

	@Override
	public Map<BigDecimal, Object> selectAreaNominationPoint(NominationPointBean newNominationPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationPointMapper.selectAreaNominationPoint(newNominationPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectSubAreaNominationPoint(NominationPointBean newNominationPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationPointMapper.selectSubAreaNominationPoint(newNominationPoint);
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
