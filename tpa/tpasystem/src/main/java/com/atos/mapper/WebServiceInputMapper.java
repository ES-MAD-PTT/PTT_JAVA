package com.atos.mapper;

import java.math.BigDecimal;

import com.atos.beans.WebServiceInputBean;
import com.atos.beans.WebServiceProcBean;

public interface WebServiceInputMapper {

	public int insertWebServiceInput(WebServiceInputBean bean);
	
	public void webServiceAcumInventorySave(WebServiceProcBean wsb);	

	public void webServiceBaseInventorySave(WebServiceProcBean wsb);	

	public BigDecimal checkIntradayExecution(BigDecimal idnSystem);
}
