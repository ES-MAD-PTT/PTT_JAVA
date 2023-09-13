package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.nominations.QualityPublicationBean;
import com.atos.filters.allocation.AllocationReportPerPointFilter;
import com.atos.filters.nominations.QualityPublicationFilter;


public interface QualityPublicationService extends Serializable {

	public Map<BigDecimal, Object> selectZones();
	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system); //offshore
	public List<QualityPublicationBean> search(QualityPublicationFilter filter);
	public Map<BigDecimal, Object> selectOperationId(QualityPublicationFilter filters);
}
