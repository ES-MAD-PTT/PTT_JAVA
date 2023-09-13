package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.beans.booking.ContractQueryBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.filters.booking.CRManagementFilter;

public interface ContractQueryMapper extends Serializable{

	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectContractTypes();
	public List<ContractQueryBean> selectContractQueryRequests(CRManagementFilter filter);
	public List<ContractAttachTypeBean> selectContractAttachTypes();
	public List<OperationFileBean> getFileByOpFileId(BigDecimal opFileId);
	public List<ContractAttachmentBean> selectAdditionalDocs(BigDecimal crId);
	public List<ContractAttachmentBean> selectAdditionalDocsBankGuarantee(BigDecimal crId);

}
