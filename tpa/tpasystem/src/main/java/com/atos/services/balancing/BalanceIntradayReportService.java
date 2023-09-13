package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceIntradayReportBean;
import com.atos.beans.balancing.BalanceIntradayReportOffshoreBean;
import com.atos.filters.balancing.BalanceIntradayReportFilter;

public interface BalanceIntradayReportService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectTimestamp(BalanceIntradayReportFilter filter);
	public List<BalanceIntradayReportBean> search(BalanceIntradayReportFilter filter);
	// Se define un nuevo metodo porque el bean de salida es distinto.
	public List<BalanceIntradayReportOffshoreBean> searchOffshore(BalanceIntradayReportFilter filter);
	public ByteArrayInputStream getReportTemplate(BigDecimal systemId) throws Exception;
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet);
	// Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo.
	//public void insertReportTemplate(ReportTemplateBean rtb) throws Exception;
}
