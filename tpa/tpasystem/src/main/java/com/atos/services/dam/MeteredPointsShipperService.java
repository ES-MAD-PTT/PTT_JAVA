package com.atos.services.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public interface MeteredPointsShipperService extends Serializable {

	public Map<BigDecimal, Object> selectShippers();

}
