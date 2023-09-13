package com.atos.services.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.balancing.ShipperDailyStatusBean;
import com.atos.beans.balancing.ShipperDailyStatusOffshoreBean;
import com.atos.filters.balancing.ShipperDailyStatusFilter;

public interface ShipperDailyStatusService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	public List<ShipperDailyStatusBean> search(ShipperDailyStatusFilter filter);
	// Se define un nuevo metodo porque el bean de salida es distinto.
	public List<ShipperDailyStatusOffshoreBean> searchOffshore(ShipperDailyStatusFilter filter);	
}
