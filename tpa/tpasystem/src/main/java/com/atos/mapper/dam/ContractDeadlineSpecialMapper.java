package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ContractDeadlineSpecialBean;
import com.atos.filters.dam.ContractDeadlineSpecialFilter;

public interface ContractDeadlineSpecialMapper {

	public List<ContractDeadlineSpecialBean> selectContractDeadlineSpecials(ContractDeadlineSpecialFilter filters);

	public List<ComboFilterNS> selectTypes();

	public int insertContractDeadlineSpecial(ContractDeadlineSpecialBean bean);

	public List<BigDecimal> getIdnOperationContractDeadLine(ContractDeadlineSpecialBean bean);

	public int deleteContractDeadlineSpecial(ContractDeadlineSpecialBean bean); // es un update

	public Date getContractDeadlineSpecialStarDate(ContractDeadlineSpecialBean bean);

}
