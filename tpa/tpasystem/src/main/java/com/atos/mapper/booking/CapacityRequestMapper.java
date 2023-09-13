package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAgreementBean;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.beans.booking.ContractBean;
import com.atos.beans.booking.ContractConsolidateBean;
import com.atos.beans.booking.ContractRejectedPointBean;
import com.atos.beans.booking.ContractShadowBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.filters.booking.CRManagementFilter;

public interface CapacityRequestMapper extends Serializable{

	public List<CapacityRequestBean> selectCapacityRequestsWithoutGrandfathering(CRManagementFilter filter);
	
	public List<CapacityRequestBean> selectCapacityRequests(CRManagementFilter filter);

	public List<ComboFilterNS> selectShipperId();

	public List<ComboFilterNS> selectOperators();
	
	public List<ComboFilterNS> selectContractTypes();

	public List<String> selectRejectedPoints(CapacityRequestBean cr);
	
	public List<ComboFilterNS> selectRequestedPoints(CapacityRequestBean cr);
	
	public int updateCRReject(CapacityRequestBean cr);
	
	public int insertRejectedPoint(ContractRejectedPointBean crp);
	
	public List<ContractBean> selectContractByCode(String cCode);

	public int updateCRAccept(CapacityRequestBean cr);	

	public int insertContract(ContractBean c);	

	public int insertContractShadow(ContractShadowBean cs);

	public int updateCRComplete(CapacityRequestBean cr);
	
	public List<ContractAgreementBean> selectContractAgreementByContReqId(BigDecimal crId);
	
	public int insertContractConsolidate(ContractConsolidateBean cc);	
	
	public List<OperationFileBean> getFileByOpFileId(BigDecimal opFileId);

	public List<ContractAttachTypeBean> selectContractAttachTypes();

	public List<ContractAttachmentBean> selectAdditionalDocs(BigDecimal crId);

	public List<ContractAttachmentBean> selectAdditionalDocsBankGuarantee(BigDecimal crId);
	
	public void insertContractAttachment(ContractAttachmentBean cab);
	
	public List<ContractAttachmentBean> getFileByContractAttachmentId(BigDecimal caId);
	
	public int updateContractAttachmentDelete(ContractAttachmentBean cab);	
	
	
	public void ejec_pro_logger_clean();
}
