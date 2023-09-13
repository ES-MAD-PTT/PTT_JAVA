package com.atos.mapper.dam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.dam.ContractPointBean;
import com.atos.filters.dam.ContractPointFilter;

public interface ContractPointMapper {

	public List<ContractPointBean> selectContractPoints(ContractPointFilter filters);

	public List<ComboFilterNS> selectPointTypes();

	//public List<String> selectIds(String query);

	public List<ComboFilterNS> selectIdsComboSystem(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectSystem(BigDecimal idn_system);// offshore
	
	public List<ComboFilterNS> selectZonesSystem(BigDecimal idn_system);// offshore

	public List<ComboFilterNS> selectAreas(ContractPointBean contractPoint);

	public List<ComboFilterNS> selectAreasSystem(BigDecimal idn_system);// offshore

	public int insertContractPoint(ContractPointBean contractPoint);

	public int insertContractPointParam(ContractPointBean contractPoint);

	public int updateContractPoint(ContractPointBean contractPoint);

	public int updateContractPointParam(ContractPointBean contractPoint);

	public List<String> getContractPointId(ContractPointBean bean);

	public List<ComboFilterNS> selectAreaContractPoint(ContractPointBean newContractPoint);

	public List<ComboFilterNS> selectSubAreaContractPoint(ContractPointBean newContractPoint);

	public List<String> getContractPointCode(ContractPointBean contractPoint);

	public int updateMocMetering(ContractPointBean contractPoint);

	public int deleteContractPointParam(ContractPointBean contractPoint);

}
