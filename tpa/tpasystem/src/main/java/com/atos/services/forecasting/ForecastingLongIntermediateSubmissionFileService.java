package com.atos.services.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.TreeMap;

import org.primefaces.model.StreamedContent;

import com.atos.beans.FileBean;
import com.atos.beans.forecasting.ForecastingMailBean;


public interface ForecastingLongIntermediateSubmissionFileService extends Serializable{

	public HashMap<String,Object> saveFile(FileBean file, String user, BigDecimal systemId) throws Exception;

	public BigDecimal selectOperationCategory();
	
	public BigDecimal selectOperationTerm();

	public BigDecimal selectOperation(TreeMap<String,BigDecimal> map);

	public StreamedContent getFile(BigDecimal idnSystem);
	
	public ForecastingMailBean getInfoShipperSubForecastingBean(BigDecimal idn_forecasting);
}
