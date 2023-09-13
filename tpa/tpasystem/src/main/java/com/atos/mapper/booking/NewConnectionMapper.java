package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.OpTemplateBean;
import com.atos.beans.booking.NewConnectionBean;
import com.atos.beans.booking.OperationFileBean;
import com.atos.filters.booking.NewConnectionFilter;

public interface NewConnectionMapper extends Serializable{
	
	public List<ComboFilterNS> selectShipperId();
	public List<NewConnectionBean> selectNewConnectionCapacityRequests(NewConnectionFilter filter);
	public List<OperationFileBean> getFileByOpFileId(BigDecimal opFileId);	
	public List<String> selectNewConnectionPointCodesByCapRequest(BigDecimal capacityRequestId);
	public List<String> selectContractCodeByCapRequest(BigDecimal capacityRequestId);
	public List<String> selectERCLicenseIdByShipperId(BigDecimal shipperId);
	public List<OpTemplateBean> getOpTemplateByCatTermFiletypeSystem(OpTemplateBean otb);
}
