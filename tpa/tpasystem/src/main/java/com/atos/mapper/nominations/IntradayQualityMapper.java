package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.IntradayQualityBean;
import com.atos.filters.nominations.IntradayQualityFilter;

public interface IntradayQualityMapper extends Serializable{

	public List<ComboFilterNS> selectZones();
	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system); //offshore	
	public List<IntradayQualityBean> selectIntradayQuality(IntradayQualityFilter filter);
}
