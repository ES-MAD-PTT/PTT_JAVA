package com.atos.mapper.balancing;

import java.io.Serializable;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceReportBean;
import com.atos.beans.balancing.BalanceReportOffshoreBean;
import com.atos.filters.balancing.BalanceReportFilter;

public interface BalanceReportMapper extends Serializable {

	public List<ComboFilterNS> selectShipperId();
	public List<BalanceReportBean> selectBalances(BalanceReportFilter filter);
	public List<BalanceReportOffshoreBean> selectBalancesOffshore(BalanceReportFilter filter);
	public List<ReportTemplateBean> selectReportTemplateFromCodeSystem(ReportTemplateBean rtb);
	//Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo.
	//public void insertReportTemplate(ReportTemplateBean rtb);
}
