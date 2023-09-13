package com.atos.mapper;

import java.util.Date;
import java.util.List;

import com.atos.beans.ElementIdBean;
import com.atos.beans.SystemParameterBean;
import com.atos.beans.ValidateDeadlineBean;
import com.atos.beans.ValidateIntervalBean;
import com.atos.beans.forecasting.ValidateForecastingXmlBean;

public interface SystemParameterMapper {

	public List<SystemParameterBean> getIntegerSystemParameter(SystemParameterBean bean);

	public List<SystemParameterBean> getStringSystemParameter(SystemParameterBean bean);

	public List<SystemParameterBean> getFloatSystemParameter(SystemParameterBean bean);

	public List<SystemParameterBean> getDateSystemParameter(SystemParameterBean bean);
	
	public List<Date> getSysdate();
	
	public List<ValidateDeadlineBean> getValidateDeadline(ValidateDeadlineBean bean);
	
	public List<ValidateIntervalBean> getValidateInterval(ValidateIntervalBean bean);
	
	public List<ValidateForecastingXmlBean>getValidateForecastingXml(ValidateForecastingXmlBean bean);
	
	public List<ValidateIntervalBean> getValidateForecastingInterval(ValidateIntervalBean bean);

	public List<ElementIdBean> getElementId(ElementIdBean bean);
}
