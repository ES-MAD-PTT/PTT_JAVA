package com.atos.mapper.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.forecasting.OperationFileBean;
import com.atos.beans.forecasting.QueryShipperForecastingFileBean;
import com.atos.filters.forecasting.QueryShipperForecastingFileFilter;

public interface QueryShipperForecastingMapper extends Serializable{

	public List<ComboFilterNS> selectShipper(QueryShipperForecastingFileFilter filter);
	
	public List<ComboFilterNS> selectTermCode();
	
	public List<QueryShipperForecastingFileBean> selectQueryForecasting(QueryShipperForecastingFileFilter filter);

	public List<OperationFileBean> selectGetFile(TreeMap<String,BigDecimal> map);
	
}
