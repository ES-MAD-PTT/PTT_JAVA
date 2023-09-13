package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.SystemParameterBean;
import com.atos.beans.dam.ContractPointBean;
import com.atos.filters.dam.ContractPointFilter;

public interface ContractPointService extends Serializable {

	public List<ContractPointBean> selectContractPoints(ContractPointFilter filter);

	//public List<String> selectIds(String query);

	public Map<BigDecimal, Object> selectIdsSystem(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectPointTypes();

	public Map<BigDecimal, Object> selectSystems(BigDecimal idn_system);// offshore

	public Map<BigDecimal, Object> selectZonesSystem(BigDecimal idn_system); // offshore

	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system); // offshore

	public Map<BigDecimal, Object> selectAreas(ContractPointBean meteredPoint);

	public SystemParameterBean getSystemParameter(String str);

	public String updateContractPoint(ContractPointBean contractPoint) throws Exception;

	public String insertContractPoint(ContractPointBean contractPoint) throws Exception;

	public Map<BigDecimal, Object> selectAreaContractPoint(ContractPointBean newContractPoint);

	public Map<BigDecimal, Object> selectSubAreaContractPoint(ContractPointBean newContractPoint);

}
