package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.nominations.IntradayNomSummaryBean;
import com.atos.filters.nominations.IntradayNomSummaryFilter;

public interface IntradayNomSummaryService extends Serializable{
	
	public List<IntradayNomSummaryBean> selectIntradayNomSummary(IntradayNomSummaryFilter filter);
	public Map<BigDecimal, Object> selectShipperId(); 
	public Map<BigDecimal, Object> selectContractId(IntradayNomSummaryFilter filter); 
	public Map<BigDecimal, Object> selectSystemPointId(BigDecimal contractId);

}
