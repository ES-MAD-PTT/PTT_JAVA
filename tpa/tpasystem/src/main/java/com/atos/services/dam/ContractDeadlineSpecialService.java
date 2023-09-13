package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ContractDeadlineSpecialBean;
import com.atos.filters.dam.ContractDeadlineSpecialFilter;

public interface ContractDeadlineSpecialService extends Serializable {

	public List<ContractDeadlineSpecialBean> selectContractDeadlineSpecials(ContractDeadlineSpecialFilter filter);

	public Map<BigDecimal, Object> selectTypes();

	public SystemParameterBean getSystemParameter(String str);

	public String updateContractDeadlineSpecial(ContractDeadlineSpecialBean contractDeadline) throws Exception;

	public String insertContractDeadlineSpecial(ContractDeadlineSpecialBean contractDeadline) throws Exception;

	public String deleteContractDeadlineSpecial(ContractDeadlineSpecialBean contractDeadline) throws Exception;

}
