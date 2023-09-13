package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationIntradayBean;
import com.atos.beans.allocation.AllocationIntradayDetailBean;
import com.atos.beans.allocation.AllocationReportDetailDto;
import com.atos.beans.allocation.AllocationReportDto;
import com.atos.filters.allocation.AllocRepQryFilter;
import com.atos.filters.allocation.AllocationIntradayFilter;


public interface AllocationIntradayService extends Serializable {
	public Map<BigDecimal, Object> selectShipperId(); 
	public Map<BigDecimal, Object> selectContractId(AllocationIntradayFilter filter); 
	public Map<BigDecimal, Object> selectPointId(BigDecimal contractId);
	public List<AllocationIntradayBean> search(AllocationIntradayFilter filters);
	public List<AllocationIntradayDetailBean> searchDetail(AllocationIntradayFilter filters);
	public Map<String, List<AllocationIntradayDetailBean>> getDetailMap(List<AllocationIntradayDetailBean> detailList);
	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception;
	public Date selectOpenPeriodFirstDay(Map<String, Object> params);
	public void allocationAndBalance(Date _startDate, Date _endDate, UserBean _user, LanguageBean _lang,
			BigDecimal idnSystem) throws Exception;
}
