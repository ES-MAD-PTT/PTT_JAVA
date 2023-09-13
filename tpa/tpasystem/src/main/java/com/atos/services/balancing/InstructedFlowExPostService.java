package com.atos.services.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.balancing.InstructedFlowExPostBean;
import com.atos.filters.balancing.InstructedFlowExPostFilter;


public interface InstructedFlowExPostService extends Serializable{
	
	public Map<BigDecimal, Object> selectShippersFilter(BigDecimal idn_user_group);
	public Map<BigDecimal, Object> selectShippersFilter();
	public Map<BigDecimal, Object> selectShippersNew();
	
	
	public Map<BigDecimal, Object> selectContracts(InstructedFlowExPostFilter filter);
	public Map<BigDecimal, Object> selectContracts(InstructedFlowExPostBean filter);
	public Map<BigDecimal, Object> selectConcepts();
	public String insertInstructedFlowExPost(InstructedFlowExPostBean area) throws Exception;
	public Date getFirstOpenDay(Map<String, Object> params);
	public SystemParameterBean getSystemParameter(String str);
	public List<InstructedFlowExPostBean> selectInstructedFlowExPosts(InstructedFlowExPostFilter filter);
	public String getContractCode(BigDecimal idnContract);
	
	public Map<BigDecimal, Object> selectZones(String systemCode);
	
}
