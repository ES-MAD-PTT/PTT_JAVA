package com.atos.mapper.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.ReserveBalancingGasContractBean;
import com.atos.beans.balancing.ReserveBalancingGasContractDetailBean;
import com.atos.filters.balancing.ReserveBalancingGasContractsFilter;

public interface ReserveBalancingGasContractsMapper extends Serializable {
	public List<ComboFilterNS> selectShipperId();
	public List<ComboFilterNS> selectValidShipperId();
	public List<ComboFilterNS> selectReserveContractCodesFromShipperId(ReserveBalancingGasContractsFilter filter);
	public List<ComboFilterNS> selectZonesFromSystemCode(String systemCode);
	public List<ComboFilterNS> selectValidZonesFromSystemCode(String systemCode);
	public List<ComboFilterNS> selectPointTypes();
	public List<ComboFilterNS> selectValidPoints(ReserveBalancingGasContractDetailBean det);
	public List<ReserveBalancingGasContractBean> selectReserveContracts(ReserveBalancingGasContractsFilter filter);
	public List<ReserveBalancingGasContractBean> getFileFromResBalGasContractId(BigDecimal contractId);
	public List<ReserveBalancingGasContractBean> selectReserveContractsFromCode(ReserveBalancingGasContractBean rbgc);
	public int insertResBalGasContract(ReserveBalancingGasContractBean rbgc);
	public int insertResBalGasContractDetail(ReserveBalancingGasContractDetailBean rbgcd);
	public Integer checkDeletableContract(BigDecimal contractId);
	public void deleteContractDetail(BigDecimal contractId);
	public void deleteContract(BigDecimal contractId);

	public List<ComboFilterNS> getAreas(Map<String, Object> params);
}
