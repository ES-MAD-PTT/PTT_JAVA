package com.atos.services.allocation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.allocation.AllocationQueryBean;
import com.atos.filters.allocation.AllocationQueryFilter;
import com.atos.mapper.SystemParameterMapper;
import com.atos.mapper.allocation.AllocationQueryMapper;

@Service("AllocationQueryService")
public class AllocationQueryServiceImpl implements AllocationQueryService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5195291291493121888L;

	private static final String strAllocationMaxDateOffset = "ALLOCATION.MAX.DATE.OFFSET";
	
	@Autowired
	private AllocationQueryMapper aqMapper;

	@Autowired
	private SystemParameterMapper sysParMapper;
	
	public BigDecimal selectFactorFromDefaultUnit(String otherUnit){
		return aqMapper.selectFactorFromDefaultUnit(otherUnit);
	}

	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception {
		SystemParameterBean bean = new SystemParameterBean();
		bean.setDate(new Date());	// Now
		bean.setParameter_name(strAllocationMaxDateOffset);
		bean.setUser_id(userId);
		bean.setLanguage(lang);
		sysParMapper.getIntegerSystemParameter(bean);
		
		// El error al tratar de obtener el codigo se trata como error tecnico, que no se va a mostrar al usuario.
		if(bean == null || bean.getInteger_exit()==null)
			throw new Exception("Error getting ALLOCATION.MAX.DATE.OFFSET parameter");
		
		return bean.getInteger_exit();
	}
	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = aqMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectContractId(AllocationQueryFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = aqMapper.selectContractId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}

	public Map<BigDecimal, Object> selectZones(String systemCode){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = aqMapper.selectZonesFromSystemCode(systemCode);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectAreas(AllocationQueryFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = aqMapper.selectAreasFromZoneId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectPointId(AllocationQueryFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = aqMapper.selectPointId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<AllocationQueryBean> search(AllocationQueryFilter filter){
		return aqMapper.selectAllocations(filter);
	}
}
