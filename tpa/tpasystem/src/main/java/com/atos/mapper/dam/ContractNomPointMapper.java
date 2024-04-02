package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ContractNomPointBean;
import com.atos.filters.dam.ContractNomPointFilter;

public interface ContractNomPointMapper {

	public List<ContractNomPointBean> selectContractNomPoints(ContractNomPointFilter filters);
	
	public List<ContractNomPointBean> selectContractNomPointsFormTable(ContractNomPointBean contractNomPoint);
	
	public List<ContractNomPointBean> selectContractNomPointsNullFormTable(ContractNomPointBean contractNomPoint);
	
	public List<ContractNomPointBean> selectContractNomPointsFormEdit(ContractNomPointBean contractNomPoint);
	
	public ContractNomPointBean selectIdShipper(ContractNomPointBean contractNomPoint);
	
	public ContractNomPointBean selectContraCodeById(ContractNomPointBean contractNomPoint);
	
	public ContractNomPointBean selectCodeNomPointById(ContractNomPointBean contractNomPoint);
	
	public BigDecimal selectExistingNumSlop(ContractNomPointBean contractNomPoint);
	
	public Date selectDateContra(ContractNomPointBean contractNomPoint);

	public List<ComboFilterNS> selectShippers();

	public List<ComboFilterNS> selectContractIds(ContractNomPointFilter filters);
	
	public List<ComboFilterNS> selectContractPoints(ContractNomPointFilter filters);// offshore

	public List<ComboFilterNS> selectNominationPoints(ContractNomPointFilter filters); // offshore

	public int insertContractNomPoint(ContractNomPointBean contractNomPoint);

	public int updateContractNomPoint(ContractNomPointBean contractNomPoint);

	public List<BigDecimal> getContractNomPointCode(ContractNomPointBean contractNomPoint);

	public List<ComboFilterNS> selectContractForm(ContractNomPointBean newContractNomPoint);

	public List<ComboFilterNS> selectContractNomPointsForm(ContractNomPointBean newContractNomPoint);
	
	public List<ComboFilterNS> selectNominationPointsForm(ContractNomPointBean newContractNomPoint);

	public int deleteContractNomPoint(ContractNomPointBean bean) throws Exception;
	
}
