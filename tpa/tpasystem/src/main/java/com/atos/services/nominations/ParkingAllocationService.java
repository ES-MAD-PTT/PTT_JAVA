package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import com.atos.beans.nominations.ParkingAllocationBean;
import com.atos.beans.nominations.ParkingAllocationFormBean;
import com.atos.filters.nominations.ParkingAllocationFilter;

public interface ParkingAllocationService extends Serializable{

	public Map<String, Object> selectParkingAllocationZones();
	public Map<BigDecimal, Object> selectParkingAllocationZonesSystem(BigDecimal idn_system); //offshore
	
	public List<ParkingAllocationBean> selectParkingAllocation(ParkingAllocationFilter filter);
	
	public BigDecimal getParkDefaultValue(ParkingAllocationFormBean form);
	public BigDecimal getParkLastUserParkValue(ParkingAllocationFormBean form);
	
	public String allocated(ParkingAllocationFormBean form) throws Exception;
	public String defaultValueCalc(ParkingAllocationFormBean form) throws Exception;
	
	public String getZoneCode(BigDecimal idn_zone);
	
	
	
}
