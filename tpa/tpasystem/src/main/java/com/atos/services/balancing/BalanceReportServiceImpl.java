package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceReportBean;
import com.atos.beans.balancing.BalanceReportOffshoreBean;
import com.atos.filters.balancing.BalanceReportFilter;
import com.atos.mapper.balancing.BalanceReportMapper;
import com.atos.utils.POIXSSFExcelUtils;

@Service("BalanceReportService")
public class BalanceReportServiceImpl implements BalanceReportService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7123677875086212210L;

	private static final String strBalanceReportTemplateCode = "BALANCE_REPORT";
	
	@Autowired
	private BalanceReportMapper bsrMapper;

	private POIXSSFExcelUtils excelUtil = new POIXSSFExcelUtils();

	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = bsrMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<BalanceReportBean> search(BalanceReportFilter filter){
		return bsrMapper.selectBalances(filter);
	}
	
	public List<BalanceReportOffshoreBean> searchOffshore(BalanceReportFilter filter){
		return bsrMapper.selectBalancesOffshore(filter);
	}
	
	public ByteArrayInputStream getReportTemplate(BigDecimal systemId) throws Exception{
		ReportTemplateBean rtbEntrada = new ReportTemplateBean();
		rtbEntrada.setTempCode(strBalanceReportTemplateCode);
		rtbEntrada.setSystemId(systemId);
		
		List<ReportTemplateBean> lData = bsrMapper.selectReportTemplateFromCodeSystem(rtbEntrada);		
		if(lData == null || lData.size()==0)
			throw new Exception("Template file not found.");
		
		// Solo se va a tener un fichero por cada capacity request.
		ReportTemplateBean tmpRTBean = lData.get(0);
		if(tmpRTBean == null)
			throw new Exception("Template file not found.");		

		byte[] ba = tmpRTBean.getBinaryData();
		if(ba == null)
			throw new Exception("Template in BD with no binary data.");		
		
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		
		return bais;
	}
	
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet){   
		excelUtil.copySheets(srcSheet, destSheet, true);   
	}

	// Para insertar la plantilla excel en la BD.
	/*public void insertReportTemplate(ReportTemplateBean rtb) throws Exception {
		bsrMapper.insertReportTemplate(rtb);
	}*/
}
