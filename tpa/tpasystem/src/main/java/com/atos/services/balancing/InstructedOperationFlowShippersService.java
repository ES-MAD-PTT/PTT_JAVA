package com.atos.services.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.atos.beans.balancing.InstructedOperationFlowShippersBean;
import com.atos.filters.balancing.InstructedOperationFlowShippersFilter;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryFilter;

public interface InstructedOperationFlowShippersService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();	
	public Map<BigDecimal, Object> selectZoneId(BigDecimal idn_system);
	public List<InstructedOperationFlowShippersBean> search(InstructedOperationFlowShippersFilter filter);
	public int updateComments(InstructedOperationFlowShippersBean bean);
	public Map<BigDecimal, Object> selectTimestampIds(InstructedOperationFlowShippersFilter filters);
	public void sendNotification(String notifTypeCode, String origin, String info, BigDecimal userGroupId, BigDecimal systemId) throws Exception;
	
	public String insertFile(UploadedFile file, InstructedOperationFlowShippersBean item) throws Exception;
	public String updateFile(UploadedFile file, InstructedOperationFlowShippersBean item) throws Exception;
	public StreamedContent getFile(BigDecimal idn_intraday_gas_flow_file);
}
