package com.atos.mapper.dam;

import java.util.List;

import com.atos.beans.ComboFilter;
import com.atos.beans.dam.OperatorBean;
import com.atos.filters.dam.OperatorFilter;

public interface OperatorMapper {

	public List<OperatorBean> selectOperators(OperatorFilter filters);

	public List<ComboFilter> selectOperatorId();

	public List<String> selectName(String query);
	
	public List<String> getOperatorId(OperatorBean bean);
	
	public int insertOperator(OperatorBean operator);	
	
	public int insertUserGroup(OperatorBean operator);
	
	public int updateOperator(OperatorBean operator);

	public int updateUserGroup(OperatorBean operator);
}
