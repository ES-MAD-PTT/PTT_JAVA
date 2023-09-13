package com.atos.mapper.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.allocation.AllocationIntradayBean;
import com.atos.beans.allocation.AllocationIntradayDetailBean;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.beans.allocation.CalculateAllocationBalanceBean;
import com.atos.filters.allocation.AllocRepQryFilter;
import com.atos.filters.allocation.AllocationIntradayFilter;

public interface AllocationIntradayMapper extends Serializable{
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectContractId(AllocationIntradayFilter filter);
	public List<ComboFilterNS> selectPointId(BigDecimal idnSystem);
	public List<AllocationIntradayBean> search(AllocationIntradayFilter filters);
	public List<AllocationIntradayDetailBean> searchDetail(AllocationIntradayFilter filters);
	public Date selectOpenPeriodFirstDay(Map<String, Object> params);
	public List<CalculateAllocationBalanceBean> calculateAllocationBalance(CalculateAllocationBalanceBean bean);
	public List<ComboFilterNS> selectShipperIdForInsert();
}
