package com.atos.mapper.balancing;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceInProgressReportBean;
import com.atos.beans.balancing.BalanceInProgressReportOffshoreBean;
import com.atos.filters.balancing.BalanceInProgressReportFilter;

public interface BalanceInProgressReportMapper extends Serializable {

	public List<ComboFilterNS> selectShipperId();
	public List<BalanceInProgressReportBean> selectBalances(BalanceInProgressReportFilter filter);
	public List<BalanceInProgressReportOffshoreBean> selectBalancesOffshore(BalanceInProgressReportFilter filter);
	public List<ReportTemplateBean> selectReportTemplateFromCodeSystem(ReportTemplateBean rtb);
	public List<ComboFilterNS> selectTimestamp(BalanceInProgressReportFilter filter);
	//Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo.
	//public void insertReportTemplate(ReportTemplateBean rtb);
}
