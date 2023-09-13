package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.allocation.AllocationReportPerPointBean;
import com.atos.filters.allocation.AllocationReportPerPointFilter;

public interface AllocationReportPerPointService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	//public Map<BigDecimal, Object> selectContractId(AllocationReportPerPointFilter filters);
	public Map<BigDecimal, Object> selectPointId(AllocationReportPerPointFilter filters);
	public List<AllocationReportPerPointBean> search(AllocationReportPerPointFilter filters);

}
