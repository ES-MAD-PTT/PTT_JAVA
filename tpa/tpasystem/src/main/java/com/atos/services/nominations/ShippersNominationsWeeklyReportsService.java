package com.atos.services.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.atos.beans.nominations.NominationDetailBean;
import com.atos.beans.nominations.ShippersNominationReportsDailyBean;
import com.atos.beans.nominations.ShippersNominationsReportsBean;
import com.atos.filters.nominations.ShippersNominationsReportsFilter;

public interface ShippersNominationsWeeklyReportsService extends Serializable {
	
	List<ShippersNominationsReportsBean> selectShippersNomReports(ShippersNominationsReportsFilter filter);
	public  Map<BigDecimal, Object> selectShippersIds();
	
	public Map<String, BigDecimal> selectZonesNomination();
	public List<ShippersNominationReportsDailyBean> selectShipperNomReportsDailyDetail(ShippersNominationsReportsBean bean);
	public List<ShippersNominationReportsDailyBean> selectShipperNomReportsParkUnpark(ShippersNominationsReportsBean bean);
	
}
