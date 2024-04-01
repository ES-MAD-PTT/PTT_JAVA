package com.atos.mapper.dam;

import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.filters.dam.MeteredPointsShipperFilter;

public interface MeteredPointsShipperMapper {

	public List<ComboFilterNS> selectShippers();
	
	public List<ComboFilterNS> selectMeteredPoin(MeteredPointsShipperFilter filters);
	
	public List<ComboFilterNS> selectCustomerType(MeteredPointsShipperFilter filters);
	
}
