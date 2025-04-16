package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.dam.MeteredPointBean;
import com.atos.beans.dam.NomConceptMeteringBean;
import com.atos.beans.dam.NominationConceptBean;
import com.atos.beans.dam.SystemPointConnectBean;
import com.atos.filters.dam.NominationConceptFilter;

public interface NominationConceptService extends Serializable {

	public Map<BigDecimal, Object> selectNominationConceptCombo(BigDecimal system);
	
	public Map<BigDecimal, Object> selectNominationConceptTypeCombo(BigDecimal system);
	
	public List<NominationConceptBean> selectNominationConcept(NominationConceptFilter filter);
	
	public Map<BigDecimal, Object> selectNominationConceptComboUnitType(BigDecimal system);
	
	public Integer getCountNominationConcept(NominationConceptFilter filters);
	
	public int insertNomConceptSystem(NominationConceptFilter filters);
	
	public int insertNomConcept(NominationConceptFilter filters);

	public int insertMeteredPoint(MeteredPointBean bean);
	
	public int insertNomConceptMetering(NomConceptMeteringBean bean);

	public int insertSystemPointConcept(SystemPointConnectBean bean);
}
