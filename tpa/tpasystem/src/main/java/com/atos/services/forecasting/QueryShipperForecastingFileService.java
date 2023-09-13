package com.atos.services.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.forecasting.QueryShipperForecastingFileBean;
import com.atos.filters.forecasting.QueryShipperForecastingFileFilter;

public interface QueryShipperForecastingFileService extends Serializable{
	
	
	public Map<String, Object> selectShipper(QueryShipperForecastingFileFilter filter);
	
	public Map<BigDecimal, Object> selectTermCode();

	public List<QueryShipperForecastingFileBean> selectQueryForecasting(QueryShipperForecastingFileFilter filter);
	
	public StreamedContent getFile(TreeMap<String, BigDecimal> map) throws Exception;
	
}
