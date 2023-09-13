package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ContractDeadlineBean;
import com.atos.filters.dam.ContractDeadlineFilter;

public interface ContractDeadlineService extends Serializable {
	public List<ContractDeadlineBean> selectContractDeadlines(ContractDeadlineFilter filter);

	public Map<BigDecimal, Object> selectTypes();

	public Map<BigDecimal, Object> selectDeadlineType();

	public SystemParameterBean getSystemParameter(String str);

	public String updateContractDeadline(ContractDeadlineBean contractDeadline) throws Exception;

	public String insertContractDeadline(ContractDeadlineBean contractDeadline) throws Exception;

	public String deleteContractDeadline(ContractDeadlineBean contractDeadline) throws Exception;

	public String validateContractOrders(ContractDeadlineBean contractDeadline) throws Exception;

}
