package com.atos.mapper.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationBean;
import com.atos.filters.allocation.AllocationReviewFilter;

public interface AllocationReviewMapper extends Serializable {

	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public BigDecimal selectFactorToDefaultUnit(String otherUnit);
	public Date selectOpenPeriodFirstDay();
	public List<Integer> selectAllocationReviewOpenDays();
	public List<Integer> selectAllocationReviewMaxPercentChange(Date gasDay);
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectContractId(AllocationReviewFilter filter);
	public List<ComboFilterNS> selectZonesFromSystemCode(String systemCode);
	public List<ComboFilterNS> selectAreasFromZoneId(AllocationReviewFilter filter);
	public List<ComboFilterNS> selectPointId(AllocationReviewFilter filter);
	public List<AllocationBean> selectAllocations(AllocationReviewFilter filter);
	public List<AllocationBean> selectLastAllocationReview(AllocationBean bean);
	public int insertAllocationReview(AllocationBean ab);
	public List<BigDecimal> selectGroupIdFromGroupCode(String groupCode);
}
