package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.booking.ReleaseCapacityManagementBean;
import com.atos.beans.booking.ReleaseCapacityManagementInfoMailBean;
import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.filters.booking.ReleaseCapacityManagementFilter;

public interface ReleaseCapacityManagementService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContracts(BigDecimal shipperId, BigDecimal idn_system);

	public List<ReleaseCapacityManagementBean> search(ReleaseCapacityManagementFilter filter);
	public List<ReleaseCapacitySubmissionBean> selectDetailsFrom(BigDecimal capacityRequestId);	
	public List<ReleaseCapacityManagementBean> validate(List<ReleaseCapacityManagementBean> lrcmBeans) throws Exception;
	
	public void save(List<ReleaseCapacityManagementBean> lrcsBeans,BigDecimal idn_system) throws Exception;
	public ReleaseCapacityManagementInfoMailBean selectContractInfoMail (BigDecimal idn_contract);
}
