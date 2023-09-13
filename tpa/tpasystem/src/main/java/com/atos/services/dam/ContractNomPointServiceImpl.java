package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ContractNomPointBean;
import com.atos.filters.dam.ContractNomPointFilter;
import com.atos.mapper.dam.ContractNomPointMapper;

@Service("contractNomPointService")
public class ContractNomPointServiceImpl implements ContractNomPointService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private ContractNomPointMapper contractNomPointMapper;

	public List<ContractNomPointBean> selectContractNomPoints(ContractNomPointFilter filter) {
		return contractNomPointMapper.selectContractNomPoints(filter);

	}

	@Override
	public Map<BigDecimal, Object> selectShippers() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectShippers();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContractIds(ContractNomPointFilter filters){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractIds(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContractForm(ContractNomPointBean newContractNomPoint){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractForm(newContractNomPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContractPoints(ContractNomPointFilter filters) {

		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractPoints(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectNominationPoints(ContractNomPointFilter filters) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectNominationPoints(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String insertContractNomPoint(ContractNomPointBean contractNomPoint) throws Exception {

		List<BigDecimal> list = contractNomPointMapper.getContractNomPointCode(contractNomPoint);
		if (list.size() > 0) {
			// the id is already inserted
			throw new Exception("-1");
		}
		int ins = contractNomPointMapper.insertContractNomPoint(contractNomPoint);
		if (ins != 1) {
			throw new Exception("-2");
		}
		return "0";
	}

	@Transactional(rollbackFor = { Throwable.class })
	public String updateContractNomPoint(ContractNomPointBean contractNomPoint) throws Exception {
		int upd = contractNomPointMapper.updateContractNomPoint(contractNomPoint);
		if (upd != 1) {
			throw new Exception("-1");
		}

		return "0";
	}

	@Override
	public Map<BigDecimal, Object> selectContractNomPointsForm(ContractNomPointBean newContractNomPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectContractNomPointsForm(newContractNomPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}


	@Override
	public Map<BigDecimal, Object> selectNominationPointsForm(ContractNomPointBean newContractNomPoint) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = contractNomPointMapper.selectNominationPointsForm(newContractNomPoint);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Transactional( rollbackFor = { Throwable.class })
	public String deleteContractNomPoint(ContractNomPointBean bean) throws Exception {
		 
		int ret1 = contractNomPointMapper.deleteContractNomPoint(bean);
		if(ret1!=1){
			throw new Exception("-1");
		}
		
		
		return "0";
	}
	

}
