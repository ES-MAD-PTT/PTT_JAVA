package com.atos.services.allocation;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ErrorBean;
import com.atos.beans.allocation.AdjustmentAllocationBean;
import com.atos.filters.allocation.AdjustmentAllocationFilter;
import com.atos.mapper.allocation.AdjustmentAllocationMapper;

@Service("adjustmentAllocationService")
public class AdjustmentAllocationServiceImpl implements AdjustmentAllocationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3282627734839966100L;

	@Autowired
	private AdjustmentAllocationMapper mapper;


	public Map<BigDecimal, Object> selectShipperId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public Map<BigDecimal, Object> getZoneIds(BigDecimal idn_system) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectZonesSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public List<AdjustmentAllocationBean> search(AdjustmentAllocationFilter filter) {
		return mapper.selectAdjustmentAllocation(filter);
	}

	@Override
	public ErrorBean getImbalanceCorrection(AdjustmentAllocationBean bean) {
		ErrorBean error = new ErrorBean(); 
				
		mapper.getImbalanceCorrection(bean);
		
		error.setError_code(bean.getErrorCode());
		error.setError_msg(bean.getErrorDesc());
		return error;
	}
	
	@Override
	public BigDecimal getNomConcept(String concept) {
		return mapper.selectNomConcept(concept);
	}
	
}
