package com.atos.services.nominations;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.IntradayNomSummaryBean;
import com.atos.filters.nominations.IntradayNomSummaryFilter;
import com.atos.mapper.nominations.IntradayNomSummaryMapper;

@Service("intradayNomSummaryService")
public class IntradayNomSummaryServiceImpl implements IntradayNomSummaryService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008063454154512438L;
		
	@Autowired
	private IntradayNomSummaryMapper intradayNomSummaryMapper;


	
	@Override
	public List<IntradayNomSummaryBean> selectIntradayNomSummary(IntradayNomSummaryFilter filter) {
		return intradayNomSummaryMapper.selectIntradayNomSummary(filter);
	}
	
	@Override
	public Map<BigDecimal, Object> selectShipperId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = intradayNomSummaryMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectContractId(IntradayNomSummaryFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = intradayNomSummaryMapper.selectContractId(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectSystemPointId(BigDecimal contractId) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = intradayNomSummaryMapper.selectSystemPointId(contractId);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
}
