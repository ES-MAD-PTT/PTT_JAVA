package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ElementIdBean;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAgreementBean;
import com.atos.beans.booking.ContractPointBean;
import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.filters.booking.ReleaseCapacitySubmissionFilter;

public interface ReleaseCapacitySubmissionMapper extends Serializable{
	
	public List<ComboFilterNS> selectShipperIdByUserId(String userId);
	
	//public List<ComboFilterNS> selectContractsByShipperId(BigDecimal shipperId);
	public List<ComboFilterNS> selectContractsByShipperId(@Param("shipperId") BigDecimal shipperId,@Param("idn_system") BigDecimal idn_system);
	
	public List<ComboFilterNS> selectSystemPointsByContractId(BigDecimal contractId);
	public List<Integer> selectAgreementStartYearsByContractId(BigDecimal contractId);
	
	public List<ReleaseCapacitySubmissionBean> selectReleaseCapacitySubmissionPoints(ReleaseCapacitySubmissionFilter filter);
	public Date getStartDate(BigDecimal idContract);
	public Date getEndDate(BigDecimal idContract);
	//public List<ElementIdBean> getElementId(ElementIdBean bean);
	public int insertContractRequest(CapacityRequestBean c);
	public int insertContractAgreement(ContractAgreementBean c);
	public int insertContractPoint(ContractPointBean c);	
}
