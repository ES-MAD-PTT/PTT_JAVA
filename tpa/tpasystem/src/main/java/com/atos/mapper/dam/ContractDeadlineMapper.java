package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ContractDeadlineBean;
import com.atos.filters.dam.ContractDeadlineFilter;

public interface ContractDeadlineMapper {

	public List<ContractDeadlineBean> selectContractDeadlines(ContractDeadlineFilter filters);

	public List<ComboFilterNS> selectTypes();

	public List<ComboFilterNS> selectDeadlineType();

	public int insertContractDeadline(ContractDeadlineBean forecastingDeadline);

	public List<BigDecimal> getIdnOperationContract(ContractDeadlineBean contractDeadline);

	public List<ContractDeadlineBean> getValidateContractOrders(ContractDeadlineBean contractDeadline);

	public int deleteContractDeadline(ContractDeadlineBean contractDeadline); // Es update

	public Date getContractDeadlineStarDate(ContractDeadlineBean contractDeadline);

}
