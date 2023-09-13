package com.atos.services.metering;

import java.io.Serializable;
import java.util.List;
import com.atos.beans.metering.MeteringQualityReportBean;
import com.atos.beans.metering.MeteringQualityReportOffshoreBean;
import com.atos.filters.metering.MeteringQualityReportFilter;

public interface MeteringQualityReportService extends Serializable {


	public List<MeteringQualityReportBean> search(MeteringQualityReportFilter filter);
	// Se define un nuevo metodo porque el bean de salida es distinto.
	public List<MeteringQualityReportOffshoreBean> searchOffshore(MeteringQualityReportFilter filter);
}
