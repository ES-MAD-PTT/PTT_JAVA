package com.atos.mapper.forecasting;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.forecasting.OperationTemplateBean;

public interface IntermediateShipperSubmissionForecastingFileMapper extends Serializable{

	public List<OperationTemplateBean> selectTemplateFile(OperationTemplateBean bean); 
}
