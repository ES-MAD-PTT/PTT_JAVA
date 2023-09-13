package com.atos.mapper.allocation;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AdjustmentAllocationBean;
import com.atos.filters.allocation.AdjustmentAllocationFilter;

public interface AdjustmentAllocationMapper {

	
	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system);
	public List<ComboFilterNS> selectShipperId();
	public List<AdjustmentAllocationBean> selectAdjustmentAllocation(AdjustmentAllocationFilter filter);
	
	public AdjustmentAllocationBean getImbalanceCorrection(AdjustmentAllocationBean bean);
	public BigDecimal selectNomConcept(String concept);
	
}
