package com.atos.services.dam;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.NominationConceptBean;
import com.atos.filters.dam.NominationConceptFilter;
import com.atos.mapper.dam.NominationConceptMapper;

@Service("nominationConceptService")
public class NominationConceptServiceImpl implements NominationConceptService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NominationConceptMapper nominationConceptMapper;

	@Override
	public Map<BigDecimal, Object> selectNominationConceptCombo(BigDecimal system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationConceptMapper.selectNominationConceptCombo(system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}
	
	@Override
	public Map<BigDecimal, Object> selectNominationConceptTypeCombo(BigDecimal system) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = nominationConceptMapper.selectNominationConceptTypeCombo(system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}

	@Override
	public List<NominationConceptBean> selectNominationConcept(NominationConceptFilter filter) {
		return nominationConceptMapper.selectNominationConcept(filter);
	}

}
