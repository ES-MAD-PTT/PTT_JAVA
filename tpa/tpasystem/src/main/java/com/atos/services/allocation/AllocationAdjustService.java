package com.atos.services.allocation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AdjustBean;
import com.atos.beans.allocation.AdjustBeanQryResult;
import com.atos.beans.allocation.ContractBean;
import com.atos.filters.allocation.AllocationAdjustFilter;
import com.atos.mapper.allocation.AllocationAdjustMapper;
import com.atos.mapper.allocation.AllocationManagementMapper;

@Service
public class AllocationAdjustService {
	private static final Logger log = LogManager.getLogger("com.atos.services.allocation.AllocationAdjustService");

	@Autowired
	private AllocationManagementMapper amMapper;

	@Autowired
	private AllocationAdjustMapper adMapper;

	public Map<BigDecimal, Object> selectShipperId() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = amMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	public AdjustBean getAdjustCode(AdjustBean newAdjust) {
		return adMapper.getAdjustCode(newAdjust);
	}

	public List<ContractBean> selectContractId(AllocationAdjustFilter filter) {

		return adMapper.selectContractId(filter);

	}

	public List<AdjustBeanQryResult> search(AllocationAdjustFilter filter) {
		return adMapper.search(filter);
	}

	public List<ContractBean> selectOriginContractId(AdjustBean newAdjust) {
		return adMapper.selectOriginContractId(newAdjust);
	}

	public List<ContractBean> selectDestinationContractId(AdjustBean newAdjust) {
		return adMapper.selectDestinationContractId(newAdjust);
	}

	public Map<BigDecimal, Object> getZoneIds(BigDecimal origcontractId) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = adMapper.selectZoneIds(origcontractId);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional
	public AdjustBean save(AdjustBean newAdjust) {
		adMapper.getAdjustCode(newAdjust);
		if (newAdjust.getErrorCode() == 0) {
			// String adjustCode = res.get("ADJUST_CODE");
			HashMap<String, Object> insMap = new HashMap<>();
			insMap.put("adjustCode", newAdjust.getAdjustCode());
			insMap.put("gasDay", newAdjust.getGasDay());
			insMap.put("idnContract", newAdjust.getOrigContractId());
			insMap.put("idnZone", newAdjust.getOrigZone());
			insMap.put("operatorComments", newAdjust.getOperatorComments());
			insMap.put("user", newAdjust.getUser());
			insMap.put("adjustValue", newAdjust.getAdjustValue().negate());
			adMapper.insertAdjust(insMap);
			insMap.put("adjustValue", newAdjust.getAdjustValue().abs());
			insMap.put("idnContract", newAdjust.getDestContractId());
			insMap.put("idnZone", newAdjust.getDestZone());
			adMapper.insertAdjust(insMap);

		}
		return newAdjust;

	}
}
