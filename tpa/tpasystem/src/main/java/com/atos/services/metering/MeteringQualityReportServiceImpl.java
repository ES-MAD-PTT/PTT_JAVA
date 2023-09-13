package com.atos.services.metering;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.metering.MeteringQualityReportBean;
import com.atos.beans.metering.MeteringQualityReportOffshoreBean;
import com.atos.filters.metering.MeteringQualityReportFilter;
import com.atos.mapper.metering.MeteringQualityReportMapper;


@Service("meteringQualityReportService")
public class MeteringQualityReportServiceImpl implements MeteringQualityReportService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3941640931671820208L;
	@Autowired
	private MeteringQualityReportMapper meteringRetrieveMapper;

	public List<MeteringQualityReportBean> search(MeteringQualityReportFilter filter){
		return meteringRetrieveMapper.selectMeteringQualityReport(filter);
	}
	
	public List<MeteringQualityReportOffshoreBean> searchOffshore(MeteringQualityReportFilter filter){
		return meteringRetrieveMapper.selectMeteringQualityReportOffshore(filter);
	}
	
}
