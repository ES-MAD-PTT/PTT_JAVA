package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.booking.ContractQueryBean;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.filters.booking.CRManagementFilter;

public interface ContractQueryService extends Serializable{

	
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContractTypes();
	public List<ContractQueryBean> search(CRManagementFilter filter);
	public SystemParameterBean getSystemParameter(String str);
	public List<ContractAttachTypeBean> selectContractAttachTypes();
	public void getFileByOpFileId(ContractQueryBean cr) throws Exception;
	
	public void selectAdditionalDocs(ContractQueryBean cr);
	public void selectAdditionalDocsBankGuarantee(ContractQueryBean cr);
	
}
