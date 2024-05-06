package com.atos.mapper.balancing;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.LockBean;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.WebserviceLogBean;
import com.atos.beans.balancing.IntradayAccImbalanceInventoryBean;
import com.atos.beans.balancing.IntradayAccImbalanceInventoryFormBean;
import com.atos.beans.metering.MeteringProcBean;
import com.atos.filters.balancing.IntradayAccImbalanceInventoryFilter;



public interface IntradayAccImbalanceInvMapper extends Serializable{
	public List<IntradayAccImbalanceInventoryBean> selectIntradayAccImbalanceInv(IntradayAccImbalanceInventoryFilter filters);
	public List<ComboFilterNS> selectTimestampIds(IntradayAccImbalanceInventoryFilter filters);
	public List<ReportTemplateBean> selectReportTemplateFromCodeSystem(ReportTemplateBean rtb);
	public Integer exclusiveLockRequest(LockBean bean);
	public Integer exclusiveLockRelease(LockBean bean);
	public List<ComboFilterNS> selectUserGroupByUserId(String userId);
	public int insertRequestWebserviceLog(WebserviceLogBean bean);
	
	public int deleteAllocationShipperFilter(IntradayAccImbalanceInventoryFormBean bean);
	public int insertAllocationShipperFilter(IntradayAccImbalanceInventoryFormBean bean);
	
}
