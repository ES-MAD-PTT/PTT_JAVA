package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.allocation.AllocationQueryBean;
import com.atos.filters.allocation.AllocationQueryFilter;

public interface AllocationQueryService extends Serializable {

	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception;
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContractId(AllocationQueryFilter filter);
	public Map<BigDecimal, Object> selectZones(String systemCode);
	public Map<BigDecimal, Object> selectAreas(AllocationQueryFilter filter);
	public Map<BigDecimal, Object> selectPointId(AllocationQueryFilter filter);
	public List<AllocationQueryBean> search(AllocationQueryFilter filter);
}
