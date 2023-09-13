package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.IntradayNomSummaryBean;
import com.atos.filters.nominations.IntradayNomSummaryFilter;

public interface IntradayNomSummaryMapper extends Serializable{

	public List<IntradayNomSummaryBean> selectIntradayNomSummary(IntradayNomSummaryFilter filter);
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectContractId(IntradayNomSummaryFilter filter);
	public List<ComboFilterNS> selectSystemPointId(BigDecimal contractId);
}
