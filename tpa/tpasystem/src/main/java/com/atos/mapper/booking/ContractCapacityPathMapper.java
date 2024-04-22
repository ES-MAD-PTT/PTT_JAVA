package com.atos.mapper.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.booking.ContractCapacityConnectionPathsBean;
import com.atos.beans.booking.ContractCapacityPathAreaValuesBean;
import com.atos.beans.booking.ContractCapacityPathBean;
import com.atos.beans.booking.ContractCapacityPathDetailBean;
import com.atos.beans.booking.ContractCapacityPathEntryExitBean;
import com.atos.beans.booking.ContractCapacityPathInsertBean;
import com.atos.beans.booking.ContractCapacityPathPeriodBean;
import com.atos.filters.booking.ContractCapacityPathFilter;

public interface ContractCapacityPathMapper extends Serializable {

	public List<ContractCapacityPathBean> selectCapacityPath(ContractCapacityPathFilter filters);
	
	public List<ComboFilterNS> selectBookingIds(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathPeriodBean> selectPeriod(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathPeriodBean>selectPeriodContractComplete(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathDetailBean> selectTechCapacity(ContractCapacityPathFilter filters);

	public List<ContractCapacityPathDetailBean> selectRemainBooked(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathDetailBean> selectAvailableCapacity(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathEntryExitBean> selectEntryPoints(ContractCapacityPathFilter filters);

	public List<ContractCapacityPathEntryExitBean> selectExitPoints(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityConnectionPathsBean> getConnectionPaths(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathInsertBean> getContractAgreementIdPoint(ContractCapacityPathInsertBean bean);
	
	public int insertCapacityPath(ContractCapacityPathInsertBean bean);
	
	public List<ContractCapacityPathInsertBean> getContractCapacityPathValues(ContractCapacityPathInsertBean bean);
	
	public List<ContractCapacityPathAreaValuesBean> getContractCapacityPathAreaValuesBean(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathAreaValuesBean> getCapacityPathStep(BigDecimal idn_capacity_path);
	
	public List<ComboFilterNS> selectShippersCombo(ContractCapacityPathFilter filter);
	
	public List<ComboFilterNS> selectBookingIdsByShipper(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathDetailBean> selectTechCapacityEditionPeriod(ContractCapacityPathFilter filters);
	
	public List<ContractCapacityPathDetailBean> selectTechCapacityEditionDates(ContractCapacityPathFilter filters);
	
	public List<String> getShipperCode(ContractCapacityPathFilter filters);
	
	public List<String> getShipperShortName(ContractCapacityPathFilter filters);
	
	public List<String> getContractCode(ContractCapacityPathFilter filters);
	
	public List<String> getAreaCode(BigDecimal idn_area);
	
}
