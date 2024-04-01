package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import com.atos.filters.dam.MeteredPointsShipperFilter;

public interface MeteredPointsShipperService extends Serializable {

	public Map<BigDecimal, Object> selectShippers();
	
	public Map<BigDecimal, Object> selectMeteredPoin(MeteredPointsShipperFilter filters);
	
	public Map<BigDecimal, Object> selectCustomerType(MeteredPointsShipperFilter filters);

}
