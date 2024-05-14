package com.atos.services.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.atos.beans.booking.ContractCapacityConnectionPathsBean;
import com.atos.beans.booking.ContractCapacityPathDetailBean;
import com.atos.beans.booking.ContractCapacityPathEntryExitBean;
import com.atos.filters.booking.ContractCapacityPathFilter;

public interface ContractCapacityPathService extends Serializable{

	public List<LinkedHashMap<String,String>> search(ContractCapacityPathFilter filters);
	
	public Map<String, Object> selectBookingIds(ContractCapacityPathFilter filters);

	public ArrayList<Map<String, Object>> selectPeriods(ContractCapacityPathFilter filters);

	public List<ContractCapacityPathDetailBean> search2(ContractCapacityPathFilter filters);

	public List<ContractCapacityPathEntryExitBean> selectEntryPoints(ContractCapacityPathFilter filters2);

	public List<ContractCapacityPathEntryExitBean> selectExitPoints(ContractCapacityPathFilter filters2);

	public List<ContractCapacityConnectionPathsBean> selectConnectionPaths(ContractCapacityPathFilter filters2);

	public int savePath(ContractCapacityPathFilter filters2, BigDecimal value, BigDecimal idn_capacity_path, BigDecimal idn_entry, BigDecimal idn_exit);
	
	public ArrayList<BigDecimal> getCapacityPathStep(BigDecimal idn_capacity_path);

	public int publishPath(ContractCapacityPathFilter filters2, String username);
}
