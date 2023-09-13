package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.filters.allocation.AllocationManagementFilter;

public interface AllocationManagementService extends Serializable {
	
	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public BigDecimal selectFactorToDefaultUnit(String otherUnit);

	public Date selectOpenPeriodFirstDay(Map<String, Object> params);

	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception;

	public Float selectAllocationReviewMaxPercentChange(Date gasDay, String userId, String lang, String systemCode)
			throws Exception;
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContractId(AllocationManagementFilter filter);

	public Map<BigDecimal, Object> selectPointId(BigDecimal systemId);
	public List<AllocationBean> search(AllocationManagementFilter filter);
	public String acceptReview(List<AllocationBean> _allocationList,
								boolean _afterConfirm,
								UserBean _user, 
								LanguageBean _language,
			BigDecimal factorToDefaultUnit, BigDecimal systemId, Map<String, Object> params) throws Exception;
	public void rejectReview(List<AllocationBean> _allocationList, 
								UserBean _user, 
								LanguageBean _language,
			BigDecimal factorToDefaultUnit, Map<String, Object> params) throws Exception;

	public void allocationAndBalance(Date _startDate, Date _endDate, UserBean _user, LanguageBean _lang,
			BigDecimal idnSystem) throws Exception;
}
