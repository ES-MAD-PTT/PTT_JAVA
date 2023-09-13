package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.dam.ContractNomPointBean;
import com.atos.filters.dam.ContractNomPointFilter;

public interface ContractNomPointService extends Serializable {

	public List<ContractNomPointBean> selectContractNomPoints(ContractNomPointFilter filter);

	public Map<BigDecimal, Object> selectShippers();

	public Map<BigDecimal, Object> selectContractIds(ContractNomPointFilter filters);

	public Map<BigDecimal, Object> selectContractForm(ContractNomPointBean newContractNomPoint);

	public Map<BigDecimal, Object> selectContractPoints(ContractNomPointFilter filters);

	public Map<BigDecimal, Object> selectNominationPoints(ContractNomPointFilter filters);

	public String updateContractNomPoint(ContractNomPointBean contractNomPoint) throws Exception;

	public String insertContractNomPoint(ContractNomPointBean contractNomPoint) throws Exception;

	public Map<BigDecimal, Object> selectContractNomPointsForm(ContractNomPointBean newContractNomPoint);

	public Map<BigDecimal, Object> selectNominationPointsForm(ContractNomPointBean newContractNomPoint);

	public String deleteContractNomPoint(ContractNomPointBean bean) throws Exception;

}
