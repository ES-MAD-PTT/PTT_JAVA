package com.atos.services.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.balancing.IntradayBaseInventoryBean;
import com.atos.filters.balancing.InstructedOperationFlowShippersFilter;
import com.atos.filters.balancing.IntradayBaseInventoryFilter;


public interface IntradayBaseInventoryService extends Serializable {

	public Map<BigDecimal, Object> selectZoneIds(BigDecimal systemId);
	public Map<BigDecimal, Object> selectModeIds(BigDecimal zoneId);
	public List<IntradayBaseInventoryBean> search(IntradayBaseInventoryFilter filters);
	public Map<BigDecimal, Object> selectTimestampIds(IntradayBaseInventoryFilter filters);
}

