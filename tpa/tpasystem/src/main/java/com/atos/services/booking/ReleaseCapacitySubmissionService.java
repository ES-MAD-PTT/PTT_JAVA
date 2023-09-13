package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.filters.booking.ReleaseCapacitySubmissionFilter;

public interface ReleaseCapacitySubmissionService extends Serializable {

	public Map<BigDecimal, Object> selectShipperIdByUserId(String userId);
	public Map<BigDecimal, Object> selectContracts(BigDecimal shipperId, BigDecimal idn_system);
	
	public Date getStartDate(BigDecimal idContract);
	public Date getEndDate(BigDecimal idContract);
	
	public Map<BigDecimal, Object> selectSystemPoints(BigDecimal contractId);
	public List<Integer> selectAgreementStartYears(BigDecimal contractId);
	public List<ReleaseCapacitySubmissionBean> search(ReleaseCapacitySubmissionFilter filter);
	
	public void save(BigDecimal shipperId, BigDecimal contractId, List<ReleaseCapacitySubmissionBean> lrcsBeans, BigDecimal idn_system, boolean toOperator) throws Exception;

}
