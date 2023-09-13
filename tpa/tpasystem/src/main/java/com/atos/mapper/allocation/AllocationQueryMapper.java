package com.atos.mapper.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationQueryBean;
import com.atos.filters.allocation.AllocationQueryFilter;

public interface AllocationQueryMapper extends Serializable {

	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectContractId(AllocationQueryFilter filter);
	public List<ComboFilterNS> selectZonesFromSystemCode(String systemCode);
	public List<ComboFilterNS> selectAreasFromZoneId(AllocationQueryFilter filter);
	public List<ComboFilterNS> selectPointId(AllocationQueryFilter filter);
	public List<AllocationQueryBean> selectAllocations(AllocationQueryFilter filter);
}
