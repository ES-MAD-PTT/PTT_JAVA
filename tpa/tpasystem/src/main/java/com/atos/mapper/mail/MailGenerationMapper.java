package com.atos.mapper.mail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.atos.beans.forecasting.ForecastingDatesBean;

public interface MailGenerationMapper {

	public List<BigDecimal> getShippers(HashMap<String,Object> map);
	
	public List<ForecastingDatesBean> callForecastingDates(ForecastingDatesBean bean);
}
