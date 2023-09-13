package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.booking.ContractCapacityConnectionPathsBean;
import com.atos.beans.booking.ContractCapacityPathDetailBean;
import com.atos.beans.booking.ContractCapacityPathEditionBean;
import com.atos.beans.booking.ContractCapacityPathEditionDatesBean;
import com.atos.beans.booking.ContractCapacityPathEntryExitBean;
import com.atos.filters.booking.ContractCapacityPathFilter;

public interface ContractCapacityPathEditionService extends Serializable{

	public Map<BigDecimal, Object> selectShippers(ContractCapacityPathFilter filter);
	
	public Map<String, Object> selectBookingIds(ContractCapacityPathFilter filters);

	public ArrayList<Map<String, Object>> selectPeriods(ContractCapacityPathFilter filters);

	public List<ContractCapacityPathEditionDatesBean> search(ContractCapacityPathFilter filters);

	public List<ContractCapacityPathEntryExitBean> selectEntryPoints(ContractCapacityPathFilter filters2);

	public List<ContractCapacityPathEntryExitBean> selectExitPoints(ContractCapacityPathFilter filters2);

	public List<ContractCapacityConnectionPathsBean> selectConnectionPaths(ContractCapacityPathFilter filters2, Date start_date, Date end_date);

	public int savePath(ContractCapacityPathEditionBean contractCapacityPathEditionBean, BigDecimal value, BigDecimal idn_capacity_path, BigDecimal idn_entry, BigDecimal idn_exit);
	
	public ArrayList<BigDecimal> getCapacityPathStep(BigDecimal idn_capacity_path);

	public ArrayList<String> getAreaCodes(List<ContractCapacityPathEditionDatesBean> tech_capacities);

	public ArrayList<ContractCapacityPathEditionBean> getEditTable(List<ContractCapacityPathEditionDatesBean> tech_capacities, ContractCapacityPathFilter filters);
}
