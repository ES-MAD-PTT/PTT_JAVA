package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.filters.allocation.AllocationReviewSupplyEWFilter;

public interface AllocationReviewSupplyEWService extends Serializable {
	
	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public BigDecimal selectFactorToDefaultUnit(String otherUnit);

	public Date selectOpenPeriodFirstDay(HashMap<String, Object> params);
	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception;
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContractId(AllocationReviewSupplyEWFilter filter);
	public Map<BigDecimal, Object> selectConceptId(String system);
	public List<AllocationBean> search(AllocationReviewSupplyEWFilter filter);
	public void saveReview(AllocationBean _allocation, 
							UserBean _user, 
							LanguageBean _language,
			BigDecimal factorToDefaultUnit, HashMap<String, Object> params) throws Exception;

	public void allocationAndBalance(Date _startDate, Date _endDate, UserBean _user, LanguageBean _lang,
			BigDecimal idnSystem) throws Exception;
}
