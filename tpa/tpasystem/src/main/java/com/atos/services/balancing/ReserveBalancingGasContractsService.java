package com.atos.services.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.balancing.ReserveBalancingGasContractBean;
import com.atos.beans.balancing.ReserveBalancingGasContractDetailBean;
import com.atos.filters.balancing.ReserveBalancingGasContractsFilter;

public interface ReserveBalancingGasContractsService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectValidShipperId();
	public Map<BigDecimal, Object> selectReserveContractCodes(ReserveBalancingGasContractsFilter filter);
	public Map<BigDecimal, Object> selectZones(String systemCode);
	public Map<BigDecimal, Object> selectValidZones(String systemCode);
	public Map<BigDecimal, Object> selectPointTypes();
	public Map<BigDecimal, Object> selectValidPoints(ReserveBalancingGasContractDetailBean det);	
	public List<ReserveBalancingGasContractBean> search(ReserveBalancingGasContractsFilter filter);
	public void getFile(ReserveBalancingGasContractBean _contract) throws Exception;

	public void save(ReserveBalancingGasContractBean _newContract, Map<String, Object> params) throws Exception;

	void deleteContract(BigDecimal contract) throws Exception;
	public boolean checkDeletableContract(BigDecimal contractId);

	public Map<BigDecimal, Object> selectAreas(String systemCode, ReserveBalancingGasContractDetailBean det);
}
