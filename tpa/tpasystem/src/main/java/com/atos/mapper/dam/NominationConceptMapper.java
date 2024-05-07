package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.NominationConceptBean;
import com.atos.filters.dam.NominationConceptFilter;

public interface NominationConceptMapper {

	public List<NominationConceptBean> selectNominationConcept(NominationConceptFilter filters);
	
	public List<ComboFilterNS> selectNominationConceptCombo (BigDecimal system);
	
	public List<ComboFilterNS> selectNominationConceptTypeCombo (BigDecimal system);
	
	public List<ComboFilterNS> selectNominationConceptComboUnitType (BigDecimal system);
	
	public Integer getCountNominationConcept(NominationConceptFilter filters);
}
