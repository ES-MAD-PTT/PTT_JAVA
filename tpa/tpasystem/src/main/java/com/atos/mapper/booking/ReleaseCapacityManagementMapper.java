package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAgreementBean;
import com.atos.beans.booking.ContractConsolidateBean;
import com.atos.beans.booking.ContractPointBean;
import com.atos.beans.booking.ContractRejectedPointBean;
import com.atos.beans.booking.ReleaseCapacityManagementBean;
import com.atos.beans.booking.ReleaseCapacityManagementInfoMailBean;
import com.atos.beans.booking.ReleaseCapacitySubmissionBean;
import com.atos.filters.booking.ReleaseCapacityManagementFilter;

public interface ReleaseCapacityManagementMapper extends Serializable{

	public List<ComboFilterNS> selectShipperId();
	//offshore
	//public List<ComboFilterNS> selectContractsByShipperId(BigDecimal shipperId);
	public List<ComboFilterNS> selectContractsByShipperId(@Param("shipperId") BigDecimal shipperId,@Param("idn_system") BigDecimal idn_system);
	
	public String selectContractCodeFromId(BigDecimal contractId);
	public List<ReleaseCapacityManagementBean> selectReleaseCapacityManagementRequests(ReleaseCapacityManagementFilter filter);
	public List<ReleaseCapacitySubmissionBean> selectPointsByCapacityRequestId(BigDecimal capacityRequestId);
	public List<ReleaseCapacitySubmissionBean> selectPointsByContractId(BigDecimal contractId);
	public int updateContractRequest(ReleaseCapacityManagementBean rcmb);
	public int insertRejectedPoint(ContractRejectedPointBean crpb);
	public int insertContractConsolidate(ContractConsolidateBean ccb);
	public int updateContractPoint(ContractPointBean cpb);
	public int insertContractRequest(CapacityRequestBean c);
	public int insertContractAgreement(ContractAgreementBean c);
	public int insertContractPoint(ContractPointBean c);
	public ReleaseCapacityManagementInfoMailBean selectContractInfoMail (BigDecimal idn_contract);
}
