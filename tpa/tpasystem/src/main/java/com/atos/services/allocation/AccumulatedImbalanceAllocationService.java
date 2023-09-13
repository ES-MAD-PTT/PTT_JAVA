package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ErrorBean;
import com.atos.beans.allocation.AccumulatedImbalanceAllocationBean;
import com.atos.filters.allocation.AccumulatedImbalanceAllocationFilter;


public interface AccumulatedImbalanceAllocationService extends Serializable {
	
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> getZoneIds(BigDecimal idn_system);
	public List<AccumulatedImbalanceAllocationBean> search(AccumulatedImbalanceAllocationFilter filter);
	public ErrorBean getImbalanceCorrection(AccumulatedImbalanceAllocationBean bean);
	public BigDecimal getNomConcept(String concept);
}
