package com.atos.mapper.allocation;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AccumulatedImbalanceAllocationBean;
import com.atos.filters.allocation.AccumulatedImbalanceAllocationFilter;

public interface AccumulatedImbalanceAllocationMapper {

	
	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system);
	public List<ComboFilterNS> selectShipperId();
	public List<AccumulatedImbalanceAllocationBean> selectAccumulatedImbalanceAllocation(AccumulatedImbalanceAllocationFilter filter);
	
	public AccumulatedImbalanceAllocationBean getImbalanceCorrection(AccumulatedImbalanceAllocationBean bean);
	public BigDecimal selectNomConcept(String concept);
	
}
