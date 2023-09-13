package com.atos.mapper.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.ParkingAllocationBean;
import com.atos.beans.nominations.ParkingAllocationFormBean;
import com.atos.filters.nominations.ParkingAllocationFilter;

public interface ParkingAllocationMapper extends Serializable{

	public List<ComboFilterNS> selectParkingAllocationZones();
	public List<ComboFilterNS> selectParkingAllocationZonesSystem(BigDecimal idn_system);
	
	public List<ParkingAllocationBean> selectParkingAllocation(ParkingAllocationFilter filter);
	
	public BigDecimal getParkDefaultValue(ParkingAllocationFormBean form);
	public BigDecimal getLastUserParkValue(ParkingAllocationFormBean form);
	
	public List<ParkingAllocationFormBean> allocated(ParkingAllocationFormBean form);
	public List<ParkingAllocationFormBean> defaultValueCalc(ParkingAllocationFormBean form);
	
	public String getZoneCode(BigDecimal idn_zone);
	
}
