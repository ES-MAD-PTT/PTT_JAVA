package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceReportBean;
import com.atos.beans.balancing.BalanceReportOffshoreBean;
import com.atos.filters.balancing.BalanceReportFilter;

public interface BalanceReportService extends Serializable {

	public Map<BigDecimal, Object> selectShipperId();
	public List<BalanceReportBean> search(BalanceReportFilter filter);
	// Se define un nuevo metodo porque el bean de salida es distinto.
	public List<BalanceReportOffshoreBean> searchOffshore(BalanceReportFilter filter);	
	public ByteArrayInputStream getReportTemplate(BigDecimal systemId) throws Exception;
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet);
	// Para insertar la plantilla excel en la BD. Solo se usa en Desarrollo.
	//public void insertReportTemplate(ReportTemplateBean rtb) throws Exception;
}
