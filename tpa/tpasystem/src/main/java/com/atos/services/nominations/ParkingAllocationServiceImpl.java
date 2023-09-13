package com.atos.services.nominations;

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
import com.atos.beans.nominations.ParkingAllocationBean;
import com.atos.beans.nominations.ParkingAllocationFormBean;
import com.atos.filters.nominations.ParkingAllocationFilter;
import com.atos.mapper.nominations.ParkingAllocationMapper;

@Service("parkingAllocationService")
public class ParkingAllocationServiceImpl implements ParkingAllocationService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008063454154512438L;
		
	@Autowired
	private ParkingAllocationMapper parkingAllocationrMapper;

	@Override
	public Map<String, Object> selectParkingAllocationZones() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = parkingAllocationrMapper.selectParkingAllocationZones();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectParkingAllocationZonesSystem(BigDecimal idn_system) {
		
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = parkingAllocationrMapper.selectParkingAllocationZonesSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	
	@Override
	public List<ParkingAllocationBean> selectParkingAllocation(ParkingAllocationFilter filter) {
		return parkingAllocationrMapper.selectParkingAllocation(filter);
	}

	@Override
	public BigDecimal getParkDefaultValue(ParkingAllocationFormBean form) {
		form.setDefault_value(parkingAllocationrMapper.getParkDefaultValue( form));
		return parkingAllocationrMapper.getParkDefaultValue( form);
	}

	@Override
	public BigDecimal getParkLastUserParkValue(ParkingAllocationFormBean form) {
		
		form.setLast_value(parkingAllocationrMapper.getLastUserParkValue(form));

		return parkingAllocationrMapper.getLastUserParkValue(form);
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String allocated(ParkingAllocationFormBean form) throws Exception  {
		
		
		form.setUser((String)SecurityUtils.getSubject().getPrincipal());
		form.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		parkingAllocationrMapper.allocated(form);
		
		if(form.getValid()!=0){
			throw new Exception(form.getError_desc());
		} 
		
		return "0";
		
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String defaultValueCalc(ParkingAllocationFormBean form) throws Exception {
		
		
		form.setUser((String)SecurityUtils.getSubject().getPrincipal());
		form.setLanguage(FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage());
		parkingAllocationrMapper.defaultValueCalc(form);
		
		if(form.getValid()!=0){
			throw new Exception(form.getError_desc());
		} 
		
		return "0";
	}

	@Override
	public String getZoneCode(BigDecimal idn_zone) {
		
			String zoneCode = parkingAllocationrMapper.getZoneCode(idn_zone);
			return zoneCode;
			
		}	
	
	
}
