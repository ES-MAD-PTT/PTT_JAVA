package com.atos.services.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeMap;

import com.atos.beans.FileBean;
import com.atos.beans.ValidateIntervalBean;
import com.atos.filters.forecasting.ShipperForecastingSubmissionFileFilter;


public interface ShipperForecastingLongSubmissionFileService extends Serializable{

	
	public BigDecimal selectOperationCategory();
	
	public BigDecimal selectOperationTerm();

	public BigDecimal selectOperation(TreeMap<String,BigDecimal> map);

	public String saveFile(ShipperForecastingSubmissionFileFilter filters, FileBean file) throws Exception;
	
	public ValidateIntervalBean getValidateInterval(ValidateIntervalBean bean);

	public BigDecimal selectIdnUserGroup(ShipperForecastingSubmissionFileFilter filter);
	
	public ValidateIntervalBean getValidateForecastingLongInterval(Date interval_start);
	
}
