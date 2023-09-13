package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ErrorBean;
import com.atos.beans.allocation.AdjustmentAllocationBean;
import com.atos.filters.allocation.AdjustmentAllocationFilter;


public interface AdjustmentAllocationService extends Serializable {
	
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> getZoneIds(BigDecimal idn_system);
	public List<AdjustmentAllocationBean> search(AdjustmentAllocationFilter filter);
	public ErrorBean getImbalanceCorrection(AdjustmentAllocationBean bean);
	public BigDecimal getNomConcept(String concept);
}
