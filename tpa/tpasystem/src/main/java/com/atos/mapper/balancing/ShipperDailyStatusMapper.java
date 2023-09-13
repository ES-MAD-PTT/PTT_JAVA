package com.atos.mapper.balancing;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.ShipperDailyStatusBean;
import com.atos.beans.balancing.ShipperDailyStatusOffshoreBean;
import com.atos.filters.balancing.ShipperDailyStatusFilter;

public interface ShipperDailyStatusMapper extends Serializable {

	public List<ComboFilterNS> selectShipperId();
	public List<ShipperDailyStatusBean> selectShipperDailyStatus(ShipperDailyStatusFilter filter);
	public List<ShipperDailyStatusOffshoreBean> selectShipperDailyStatusOffshore(ShipperDailyStatusFilter filter);
}
