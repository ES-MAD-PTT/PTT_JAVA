package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.filters.allocation.AllocRepQryFilter;
import com.atos.mapper.allocation.AllocationReportQryMapper;

@Service("AllocationReportQryService")
public class AllocationReportQryService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private AllocationReportQryMapper mapper;

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

	public Map<BigDecimal, Object> selectContractId(AllocRepQryFilter filters) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectContractId(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public Map<BigDecimal, Object> selectPointId(BigDecimal systemId) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectPointId(systemId);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public List<AllocationReportDto> search(AllocRepQryFilter filters) {
		return mapper.search(filters);
	}

	public List<AllocationReportDetailDto> searchDetail(AllocRepQryFilter filters) {
		return mapper.searchDetail(filters);
	}

	public Map<String, List<AllocationReportDetailDto>> getDetailMap(List<AllocationReportDetailDto> detailList) {
		Map<String, List<AllocationReportDetailDto>> result = null;
		result = detailList.stream().collect(Collectors
				.groupingBy(d -> d.getIdnAllocation() + "-" + d.getIdnContract() + "-" + d.getIdnContractPoint()));
		return result;

	}

}
