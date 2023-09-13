package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ForecastingDeadlineBean;
import com.atos.filters.dam.ForecastingDeadlineFilter;

public interface ForecastingDeadlineService extends Serializable {

	public List<ForecastingDeadlineBean> selectForecastingDeadlines(ForecastingDeadlineFilter filter);

	public Map<BigDecimal, Object> selectTypes();

	public SystemParameterBean getSystemParameter(String str);

	public String updateForecastingDeadline(ForecastingDeadlineBean forecastingDeadline) throws Exception;

	public String insertForecastingDeadline(ForecastingDeadlineBean forecastingDeadline) throws Exception;

	public String deleteForecastingDeadline(ForecastingDeadlineBean forecastingDeadline) throws Exception;

}
