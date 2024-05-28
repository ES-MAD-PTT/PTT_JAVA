package com.atos.mapper.balancing;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceIntradayReportBean;
import com.atos.beans.balancing.BalanceIntradayReportFormBean;
import com.atos.beans.balancing.BalanceIntradayReportOffshoreBean;
import com.atos.filters.balancing.BalanceIntradayReportFilter;

public interface BalanceIntradayReportMapper extends Serializable {

	public List<ComboFilterNS> selectShipperId();
	public List<BalanceIntradayReportBean> selectBalances(BalanceIntradayReportFilter filter);
	public List<BalanceIntradayReportOffshoreBean> selectBalancesOffshore(BalanceIntradayReportFilter filter);
	public List<ReportTemplateBean> selectReportTemplateFromCodeSystem(ReportTemplateBean rtb);
	public List<ComboFilterNS> selectTimestamp(BalanceIntradayReportFilter filter);
	
	//Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo.
	//public void insertReportTemplate(ReportTemplateBean rtb);
	
	public List<ComboFilterNS> selectTimestampIds(BalanceIntradayReportFilter filters);
	public List<String> selectTimestampIdsNoShipper(BalanceIntradayReportFilter filters);
	
	public int deleteBalanceIntradayReportShipperFilter(BalanceIntradayReportFormBean bean);
	public int insertBalanceIntradayReportShipperFilter(BalanceIntradayReportFormBean bean);
}
