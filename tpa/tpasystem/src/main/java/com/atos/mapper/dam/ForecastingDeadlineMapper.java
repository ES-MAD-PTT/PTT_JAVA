package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ForecastingDeadlineBean;
import com.atos.filters.dam.ForecastingDeadlineFilter;

public interface ForecastingDeadlineMapper {

	public List<ForecastingDeadlineBean> selectForecastingDeadlines(ForecastingDeadlineFilter filters);

	public List<ComboFilterNS> selectTypes();

	public int insertForecastingDeadline(ForecastingDeadlineBean forecastingDeadline);

	public List<BigDecimal> getIdnOperationForecasting(ForecastingDeadlineBean forecastingDeadline);

	public int deleteForecastingDeadline(ForecastingDeadlineBean forecastingDeadline); // es un update

	public Date getForecastingDeadlineStarDate(ForecastingDeadlineBean forecastingDeadline);

}
