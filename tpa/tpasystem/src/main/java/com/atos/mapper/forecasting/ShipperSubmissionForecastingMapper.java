package com.atos.mapper.forecasting;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.atos.beans.forecasting.ForecastingBean;
import com.atos.beans.forecasting.ForecastingMailBean;
import com.atos.beans.forecasting.OperationFileBean;
import com.atos.filters.forecasting.ShipperForecastingSubmissionFileFilter;



public interface ShipperSubmissionForecastingMapper {

	public List<BigDecimal> selectOperationCategory(String type);
	
	public List<BigDecimal> selectOperationTerm(String type);

	public List<BigDecimal> selectOperation(TreeMap<String,BigDecimal> map);

	public String selectForecastingCode(ForecastingBean bean);
	
	public int insertOperationFile(OperationFileBean bean);
	
	public List<BigDecimal> selectIdnUserGroup(ShipperForecastingSubmissionFileFilter filter);
	
	public int insertNewForecasting(ForecastingBean bean);
	
	public ForecastingMailBean getInfoShipperSubForecastingBean(BigDecimal idn_forecasting);
	
	
}
