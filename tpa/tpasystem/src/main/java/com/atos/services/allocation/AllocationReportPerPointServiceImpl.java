package com.atos.services.allocation;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationReportPerPointBean;
import com.atos.filters.allocation.AllocationReportPerPointFilter;
import com.atos.mapper.allocation.AllocationReportPerPointMapper;

@Service("allocationReportPerPointService")
public class AllocationReportPerPointServiceImpl implements AllocationReportPerPointService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private AllocationReportPerPointMapper mapper;

	@Override
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

	/*@Override
	public Map<BigDecimal, Object> selectContractId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectContractId(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}*/

	@Override
	public Map<BigDecimal, Object> selectPointId(AllocationReportPerPointFilter filters) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectPointId(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public List<AllocationReportPerPointBean> search(AllocationReportPerPointFilter filters) {
		return mapper.selectReportPerPoint(filters);
	}

}
