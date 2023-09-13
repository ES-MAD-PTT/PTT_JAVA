package com.atos.services.balancing;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.balancing.IntradayAccImbalanceInventoryAdjustmentBean;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryAdjustmentFilter;

public interface IntradayAccImbalanceInvAdjustmentService  extends Serializable {

	public List<IntradayAccImbalanceInventoryAdjustmentBean> selectIntradayAccImbalanceInvAdjustment(IntradayAccImbalanceInventoryAdjustmentFilter filter);

	public String insertIntradayAccImbalanceInvAdjustment(IntradayAccImbalanceInventoryAdjustmentBean bean) throws Exception;


}
