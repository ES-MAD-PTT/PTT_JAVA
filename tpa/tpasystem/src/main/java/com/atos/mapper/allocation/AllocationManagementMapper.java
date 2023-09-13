package com.atos.mapper.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationBean;
import com.atos.beans.allocation.CalculateAllocationBalanceBean;
import com.atos.beans.allocation.ValidateShipperReviewBean;
import com.atos.filters.allocation.AllocationManagementFilter;

public interface AllocationManagementMapper extends Serializable {

	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public BigDecimal selectFactorToDefaultUnit(String otherUnit);
	public Date selectOpenPeriodFirstDay(Map<String, Object> params);
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectShipperIdForInsert();
	public List<ComboFilterNS> selectContractId(AllocationManagementFilter filter);
	public List<ComboFilterNS> selectPointId(BigDecimal systemId);
	public List<AllocationBean> selectAllocations(AllocationManagementFilter filter);
	public List<AllocationBean> selectLastAllocationReview(AllocationBean bean);
	public int insertAllocationReview(AllocationBean ab);
	public List<ValidateShipperReviewBean> validateShipperReview(ValidateShipperReviewBean bean);
	public List<CalculateAllocationBalanceBean> calculateAllocationBalance(CalculateAllocationBalanceBean bean);
}
