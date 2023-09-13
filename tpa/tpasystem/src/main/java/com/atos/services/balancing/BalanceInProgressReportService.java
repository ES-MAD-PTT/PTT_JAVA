package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.atos.beans.balancing.BalanceInProgressReportBean;
import com.atos.beans.balancing.BalanceInProgressReportOffshoreBean;
import com.atos.filters.balancing.BalanceInProgressReportFilter;

public interface BalanceInProgressReportService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	public Map<BigDecimal, Object> selectTimestamp(BalanceInProgressReportFilter filter);
	public List<BalanceInProgressReportBean> search(BalanceInProgressReportFilter filter);
	// Se define un nuevo metodo porque el bean de salida es distinto.
	public List<BalanceInProgressReportOffshoreBean> searchOffshore(BalanceInProgressReportFilter filter);
	public ByteArrayInputStream getReportTemplate(BigDecimal systemId) throws Exception;
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet);
	// Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo.
	//public void insertReportTemplate(ReportTemplateBean rtb) throws Exception;
}
