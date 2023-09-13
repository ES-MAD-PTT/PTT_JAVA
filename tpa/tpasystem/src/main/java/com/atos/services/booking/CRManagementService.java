package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.booking.CapacityRequestBean;
import com.atos.beans.booking.ContractAttachTypeBean;
import com.atos.beans.booking.ContractAttachmentBean;
import com.atos.filters.booking.CRManagementFilter;

public interface CRManagementService extends Serializable{

	// Constantes para las acciones a realizar con la CapacityRequest
	// Se definen aqui porque solo se usan en esta pantalla.
	static final String Accept = "Accept";
	static final String Complete = "Complete";
	static final String Reject_Tech = "Reject_Tech";	// Reject desde SUBMITTED
	static final String Reject_Def = "Reject_Def";		// Reject desde ACCEPTED
	
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContractTypes();
	public List<CapacityRequestBean> search(CRManagementFilter filter);
	public void selectAdditionalDocs(CapacityRequestBean cr);
	public void selectRejectedPoints(CapacityRequestBean cr);
	public void selectRequestedPoints(CapacityRequestBean cr);
	public void changeStatusCapacityRequest(String _action, CapacityRequestBean _crbSelected) throws Exception;	
	public SystemParameterBean getSystemParameter(String str);
	public void getFileByOpFileId(CapacityRequestBean cr) throws Exception;
	public List<ContractAttachTypeBean> selectContractAttachTypes();
	public void insertContractAttachment(CapacityRequestBean _selectedCapReq, ContractAttachmentBean _newDoc) throws Exception;
	public void getDocFile(ContractAttachmentBean _contracDoc) throws Exception;
	public void deleteDocFile(ContractAttachmentBean _contracDoc) throws Exception;
	public void generateSignedContractTemplate(CapacityRequestBean cr) throws Exception;	
}
