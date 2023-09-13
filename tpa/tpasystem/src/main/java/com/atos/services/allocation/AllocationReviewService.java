package com.atos.services.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.LanguageBean;
import com.atos.beans.UserBean;
import com.atos.beans.allocation.AllocationBean;
import com.atos.filters.allocation.AllocationReviewFilter;

public interface AllocationReviewService extends Serializable {
	
	public BigDecimal selectFactorFromDefaultUnit(String otherUnit);
	public BigDecimal selectFactorToDefaultUnit(String otherUnit);

	public Integer selectAllocationReviewOpenDays(String userId, String lang, String codSystem) throws Exception;
	public Integer selectAllocationMaxDateOffset(String userId, String lang) throws Exception;
	public BigDecimal getDefaultOperatorId(UserBean _user, LanguageBean _lang) throws Exception;
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContractId(AllocationReviewFilter filter);
	public Map<BigDecimal, Object> selectZones(String systemCode);
	public Map<BigDecimal, Object> selectAreas(AllocationReviewFilter filter);
	public Map<BigDecimal, Object> selectPointId(AllocationReviewFilter filter);
	public List<AllocationBean> search(AllocationReviewFilter filter);
	public void saveReview(AllocationBean _allocation, 
							UserBean _user, 
							LanguageBean _language,
			BigDecimal factorToDefaultUnit, Map<String, Object> params) throws Exception;
}
