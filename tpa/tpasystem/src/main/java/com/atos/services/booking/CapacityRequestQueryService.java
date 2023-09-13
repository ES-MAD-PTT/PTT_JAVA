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

public interface CapacityRequestQueryService extends Serializable{

	// Constantes para las acciones a realizar con la CapacityRequest
	// Se definen aqui porque solo se usan en esta pantalla.
	// Las acciones son similares. Solo se diferencian en el estado final de la Capacity Request y 
	// si hay que generar notificacion al shipper o no.
	static final String Reject_Tech_Shipper = "Reject_Tech_Shipper";	// Reject desde SUBMITTED
	static final String Reject_Tech_Operator = "Reject_Tech_Operator";	// Reject desde SUBMITTED
	
	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectContractTypes();
	public List<CapacityRequestBean> search(CRManagementFilter filter);
	public void selectRejectedPoints(CapacityRequestBean cr);
	public void selectRequestedPoints(CapacityRequestBean cr);
	public SystemParameterBean getSystemParameter(String str);
	public void getFileByOpFileId(CapacityRequestBean cr) throws Exception;
	public void changeStatusCapacityRequest(String _action, CapacityRequestBean _crbSelected) throws Exception;
	public List<ContractAttachTypeBean> selectContractAttachTypes();
	public void selectAdditionalDocs(CapacityRequestBean cr);
	public void selectAdditionalDocsBankGuarantee(CapacityRequestBean cr);
	public void insertContractAttachment(CapacityRequestBean _selectedCapReq, ContractAttachmentBean _newDoc) throws Exception;
	public void getDocFile(ContractAttachmentBean _contracDoc) throws Exception;
	public void deleteDocFile(ContractAttachmentBean _contracDoc) throws Exception;
	
	//ch 361256
	public void deleteCapacityRequest(CapacityRequestBean _crbSelected) throws Exception;
	
}
