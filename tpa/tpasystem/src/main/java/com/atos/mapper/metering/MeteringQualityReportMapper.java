package com.atos.mapper.metering;

import java.io.Serializable;
import java.util.List;
import com.atos.beans.metering.MeteringQualityReportBean;
import com.atos.beans.metering.MeteringQualityReportOffshoreBean;
import com.atos.filters.metering.MeteringQualityReportFilter;

public interface MeteringQualityReportMapper extends Serializable{

	public List <MeteringQualityReportBean> selectMeteringQualityReport(MeteringQualityReportFilter filter);
	public List <MeteringQualityReportOffshoreBean> selectMeteringQualityReportOffshore(MeteringQualityReportFilter filter);	
}
