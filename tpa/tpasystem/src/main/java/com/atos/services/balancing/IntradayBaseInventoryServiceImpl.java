package com.atos.services.balancing;


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.IntradayBaseInventoryBean;
import com.atos.filters.balancing.InstructedOperationFlowShippersFilter;
import com.atos.filters.balancing.IntradayBaseInventoryFilter;
import com.atos.mapper.balancing.IntradayBaseInventoryMapper;

@Service("intradayBaseInventoryService")
public class IntradayBaseInventoryServiceImpl implements IntradayBaseInventoryService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IntradayBaseInventoryMapper mapper;

	@Override
	public Map<BigDecimal, Object> selectZoneIds(BigDecimal systemId) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectZoneId(systemId);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectModeIds(BigDecimal zoneId) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectModeId(zoneId);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public List<IntradayBaseInventoryBean> search(IntradayBaseInventoryFilter filters) {
		return mapper.selectIntradayBaseInventory(filters);
	}
	
	@Override
	public Map<BigDecimal, Object> selectTimestampIds(IntradayBaseInventoryFilter filters) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectTimestampIds(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 	
	}

}
