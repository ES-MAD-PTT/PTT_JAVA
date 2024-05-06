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
import com.atos.beans.dam.MeteredPointBean;
import com.atos.beans.dam.MeteredPointBeanDateRange;
import com.atos.exceptions.ValidationException;
import com.atos.filters.dam.MeteredPointFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.dam.MeteredPointMapper;

@Service("meteredPointService")
public class MeteredPointServiceImpl implements MeteredPointService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private MeteredPointMapper meteredPointMapper;

	@Autowired
	private SystemParameterMapper systemParameterMapper;

	public List<MeteredPointBean> selectMeteredPoints(MeteredPointFilter filter) {
		return meteredPointMapper.selectMeteredPoints(filter);
	}
	
	public MeteredPointBean selectMeteredPoint(MeteredPointBean item) {
		return meteredPointMapper.selectMeteredPoint(item);
	}

	public List<MeteredPointBean> selectMeteredPointsQuery(MeteredPointFilter filter) {
		return meteredPointMapper.selectMeteredPointsQuery(filter);
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
	public List<String> selectIds(String query) {
		return meteredPointMapper.selectIds(query);
	}

	@Override
	public Map<BigDecimal, Object> selectIdsSystem(BigDecimal idn_system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectIdsComboSystem(idn_system);
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
		List<ComboFilterNS> list = meteredPointMapper.selectPointTypes();
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
		List<ComboFilterNS> list = meteredPointMapper.selectSystem();
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
		List<ComboFilterNS> list = meteredPointMapper.selectZonesSystem(idn_system);
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
		List<ComboFilterNS> list = meteredPointMapper.selectAreasSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectCustomerTypes(BigDecimal bd) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectCustomerTypes(bd);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<String, Object> selectMeteringIds() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ComboFilter> list = meteredPointMapper.selectMeteringIds();
		for (ComboFilter combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}

	@Override
	public Map<BigDecimal, Object> selectMeteredPointIds() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectMeteredPointIds();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;

	}
	
	@Override
	public MeteredPointBean selectMeteredPointValues(BigDecimal id) {
		List<MeteredPointBean> list = meteredPointMapper.selectMeteredPointValues(id);
		if(list.size()>0) {
			return list.get(0);
		} else {
			return new MeteredPointBean();
		}
		
	}
	
	// offshore
	@Override
	public Map<BigDecimal, Object> selectNominationPointsSystem(MeteredPointBean meteredPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectNominationPointsSystem(meteredPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContractPointsSystem(MeteredPointBean meteredPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectContractPointsSystem(meteredPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectAreaNominationPoint(MeteredPointBean meteredPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectAreaNominationPoint(meteredPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectSubAreaNominationPoint(MeteredPointBean meteredPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectSubAreaNominationPoint(meteredPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertMeteredPoint(MeteredPointBean meteredPoint) throws Exception {

		List<String> list = meteredPointMapper.getMeteredPointCode(meteredPoint);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		int ins = meteredPointMapper.insertMeteredPoint(meteredPoint);
		if (ins != 1) {
			throw new Exception("-2");
		}
		int ins2 = meteredPointMapper.insertMeteredPointParam(meteredPoint);
		if (ins2 != 1) {
			throw new Exception("-3");

		}

		// Hay que actualizar la tabla tpa_tmoc_metering_id Poniendo
		// is_assigned='S' al punto
		// primero buscar el idn ya que tenemos su .metering_id y luego hacer
		// update

		int up1 = meteredPointMapper.updateMocMetering(meteredPoint);
		if (up1 != 1) {
			throw new Exception("-4");
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertMeteredPointNewPeriod(MeteredPointBean meteredPoint) throws Exception {

		List<String> list = meteredPointMapper.getMeteredPointCodeNewPeriod(meteredPoint);
		if (list.size() == 0) {
			// the id not exist
			throw new Exception("-1");
		}
		int ins2 = meteredPointMapper.insertMeteredPointParam(meteredPoint);
		if (ins2 != 1) {
			throw new Exception("-3");

		}

		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertMeteredPointParam(MeteredPointBean meteredPoint) throws Exception {

		int ins2 = meteredPointMapper.insertMeteredPointParam(meteredPoint);
		if (ins2 != 1) {
			throw new Exception("-3");

		}

		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class })
	public String updateMeteredPoint(MeteredPointBean meteredPoint) throws Exception {

		Date endDatePantalla = meteredPoint.getEndDate();

		int upd = meteredPointMapper.updateMeteredPoint(meteredPoint);
		if (upd != 1) {
			throw new Exception("-1");
		}

		// We calculate the end date, which will be the startDate -1 day
		Date startDateBd = meteredPointMapper.getMeteredPointParamStarDate(meteredPoint);
		Date endDate = restarDiasFecha(startDateBd, 1);
		meteredPoint.setEndDate(endDate);

		int ins = meteredPointMapper.deleteMeteredPointParam(meteredPoint);
		if (ins != 1) {
			throw new Exception("-10");
		}
		//

		// to do insert instead of update because they are historical
		/*
		 * int upd2= meteredPointMapper.updateMeteredPointParam(meteredPoint);
		 * if(upd2!=1){ throw new Exception("-2"); }
		 */
		meteredPoint.setEndDate(endDatePantalla);
		int ins2 = meteredPointMapper.insertMeteredPointParam(meteredPoint);
		if (ins2 != 1) {
			throw new Exception("-2");
		}

		return "0";
	}
	
	@Transactional(rollbackFor = { Throwable.class })
	public String updateMeteredPointNewPeriod(MeteredPointBean meteredPoint) throws Exception {

		int upd = meteredPointMapper.updateMeteredPointNewPeriod(meteredPoint);
		if (upd != 1) {
			throw new Exception("-1");
		}		

		return "0";
	}

	public Date restarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); //
		calendar.add(Calendar.DATE, -dias); // numero de días a añadir, o restar
											// en caso de días<0
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
	}

	@Override
	public String getAreaCode(BigDecimal Idn_nomination_point) {
		String areaCode = meteredPointMapper.getAreaCode(Idn_nomination_point);
		return areaCode;

	}

	public BigDecimal getAreaID(BigDecimal Idn_nomination_point) {
		BigDecimal areaId = meteredPointMapper.getAreaID(Idn_nomination_point);
		return areaId;

	}

	@Override
	public String getZoneCode(BigDecimal Idn_nomination_point) {
		String zoneCode = meteredPointMapper.getZoneCode(Idn_nomination_point);
		return zoneCode;

	}

	@Override
	public String getSystemCode(BigDecimal Idn_nomination_point) {
		String systemCode = meteredPointMapper.getSystemCode(Idn_nomination_point);
		return systemCode;

	}

	// offshore
	@Override
	public Map<BigDecimal, Object> selectQualityPointSystem(MeteredPointBean meteredPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = meteredPointMapper.selectQualityPointSystem(meteredPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public BigDecimal getMOCAvailable(String value) {
		return meteredPointMapper.getMOCavailable(value);
	}

	@Override
	public void generarMOC(UserBean user, LanguageBean lang) throws Exception {
		String errorMsg = null;
		String errorDetails = null;

		MeteredPointBean bean = new MeteredPointBean();
		// bean.setUser(user.getUser_group_id());
		bean.setUser(user.getUsername());// ponemos el username para la auditoria
		bean.setLang(lang.getLocale());

		meteredPointMapper.generarMOC(bean);

		// El error al tratar de obtener el codigo se trata como error tecnico,
		// que no se va a mostrar al usuario.
		if (bean == null || bean.getErrorCode() == null)
			throw new Exception("Error generating MOC.");

		int res = bean.getErrorCode().intValue();
		if (res != 0) {
			errorMsg = bean.getErrorDesc();
			errorDetails = bean.getErrorDetail();
			if (errorDetails != null)
				errorMsg += ":" + System.getProperty("line.separator") + errorDetails;

			if (res >= 1000) // Errores funcionales.
				throw new ValidationException(errorMsg);
			else // Errores tecnicos.
				throw new Exception(errorMsg);
		}

	}
	@Override
	public void validateDateRange(MeteredPointBean newPeriodMeteredPoint, UserBean user, LanguageBean lang, String type) throws Exception {
		String errorMsg = null;

		MeteredPointBeanDateRange bean = new MeteredPointBeanDateRange();
		// bean.setUser(user.getUser_group_id());
		bean.setUser(user.getUsername());// ponemos el username para la auditoria
		bean.setLanguage(lang.getLocale());
		bean.setDate_ini(newPeriodMeteredPoint.getStartDate());
		bean.setDate_fin(newPeriodMeteredPoint.getEndDate());
		bean.setType(type);
		bean.setIdn_system_point(newPeriodMeteredPoint.getIdn_system_point());
		bean.setIdn_system_point_param(newPeriodMeteredPoint.getIdn_system_point_param());

		meteredPointMapper.getValidateDateRange(bean);

		// El error al tratar de obtener el codigo se trata como error tecnico,
		// que no se va a mostrar al usuario.
		if (bean == null || bean.getErrorCode() == null)
			throw new Exception("Error generating MOC.");

		int res = bean.getErrorCode().intValue();
		if (res != 0) {
			errorMsg = bean.getErrorDesc();
			
			throw new Exception(errorMsg);
		}

	}

	@Override
	public boolean getValidateGroupID(MeteredPointBean meteredPoint) {
		List<BigDecimal> result = meteredPointMapper.getValidateGroupID(meteredPoint);
		if(result.size()==0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getValidateGroupIDEdit(MeteredPointBean meteredPoint) {
		List<BigDecimal> result = meteredPointMapper.getValidateGroupIDEdit(meteredPoint);
		if(result.size()==0) {
			return true;
		} else {
			return false;
		}
	}

}
