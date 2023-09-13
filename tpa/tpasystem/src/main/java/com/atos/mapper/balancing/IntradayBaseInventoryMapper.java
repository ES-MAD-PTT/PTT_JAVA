package com.atos.mapper.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.IntradayBaseInventoryBean;
import com.atos.filters.balancing.InstructedOperationFlowShippersFilter;
import com.atos.filters.balancing.IntradayBaseInventoryFilter;


public interface IntradayBaseInventoryMapper extends Serializable {

	List<ComboFilterNS> selectZoneId(BigDecimal systemId);
	
	List<ComboFilterNS> selectModeId(BigDecimal zoneId);

	List<IntradayBaseInventoryBean> selectIntradayBaseInventory(IntradayBaseInventoryFilter filters);
	
	public List<ComboFilterNS> selectTimestampIds(IntradayBaseInventoryFilter filters);

}
