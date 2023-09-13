package com.atos.mapper.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationBean;
import com.atos.filters.allocation.AllocationReviewSupplyEWFilter;

public interface AllocationReviewSupplyEWMapper extends Serializable {

	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public BigDecimal selectFactorToDefaultUnit(String otherUnit);

	public Date selectOpenPeriodFirstDay(HashMap<String, Object> params);
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectContractId(AllocationReviewSupplyEWFilter filter);

	public List<ComboFilterNS> selectConceptId(String system);
	public List<AllocationBean> selectAllocations(AllocationReviewSupplyEWFilter filter);
	public List<AllocationBean> selectLastAllocationReview(AllocationBean bean);
	public int insertAllocationReview(AllocationBean ab);
}
