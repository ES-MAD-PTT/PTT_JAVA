package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.nominations.IntradayQualityBean;
import com.atos.filters.nominations.IntradayQualityFilter;


public interface IntradayQualityService extends Serializable {

	public Map<BigDecimal, Object> selectZones();
	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system); //offshore
	public List<IntradayQualityBean> search(IntradayQualityFilter filter);
}
