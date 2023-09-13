package com.atos.services.dam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.OperatorBean;
import com.atos.filters.dam.OperatorFilter;

public interface OperatorService extends Serializable{

	public List<OperatorBean> selectOperators(OperatorFilter filter);
	
	public Map<String, Object> selectOperatorId();
	
	public List<String> selectName(String query);
	
	public SystemParameterBean getSystemParameter(String str);
	
	public String updateOperator(OperatorBean operator) throws Exception;
	
	public String insertOperator(OperatorBean operator) throws Exception;
}
