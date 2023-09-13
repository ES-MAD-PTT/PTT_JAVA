package com.atos.mapper.balancing;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.InstructedFlowExPostBean;
import com.atos.filters.balancing.InstructedFlowExPostFilter;

public interface InstructedFlowExPostMapper {

	public List<ComboFilterNS> selectShippersComboFilter();
	public List<ComboFilterNS> selectShippersComboNew();
	public List<ComboFilterNS> selectShippersComboAlone(BigDecimal idn_user_group);
	
	
	public List<ComboFilterNS> selectContractsFilter(InstructedFlowExPostFilter filter);
	public List<ComboFilterNS> selectContractsNew(InstructedFlowExPostBean bean);
	
	public List<ComboFilterNS> selectConcepts();
	
	public String getContracCode(BigDecimal idnContract);
	
	public int insertInstructedFlowExPost(InstructedFlowExPostBean instructedFlowExPost);	
	public List<InstructedFlowExPostBean> selectInstructedFlowExPosts(InstructedFlowExPostFilter filters);
	
	public List<ComboFilterNS> selectZones(String systemCode);
}
