package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceInProgressReportBean;
import com.atos.beans.balancing.BalanceInProgressReportOffshoreBean;
import com.atos.filters.balancing.BalanceInProgressReportFilter;
import com.atos.mapper.balancing.BalanceInProgressReportMapper;
import com.atos.utils.POIXSSFExcelUtils;

@Service("BalanceInProgressReportService")
public class BalanceInProgressReportServiceImpl implements BalanceInProgressReportService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4061868449409857809L;

	private static final String strBalanceInProgressReportTemplateCode = "BALANCE_INPROGRESS_REPORT";
	
	@Autowired
	private BalanceInProgressReportMapper birMapper;
	
	private POIXSSFExcelUtils excelUtil = new POIXSSFExcelUtils();
	
	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = birMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectTimestamp(BalanceInProgressReportFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = birMapper.selectTimestamp(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<BalanceInProgressReportBean> search(BalanceInProgressReportFilter filter){
		return birMapper.selectBalances(filter);
	}
		
	public List<BalanceInProgressReportOffshoreBean> searchOffshore(BalanceInProgressReportFilter filter){
		return birMapper.selectBalancesOffshore(filter);
	}
	
	@Override
	public ByteArrayInputStream getReportTemplate(BigDecimal systemId) throws Exception{
		ReportTemplateBean rtbEntrada = new ReportTemplateBean();
		rtbEntrada.setTempCode(strBalanceInProgressReportTemplateCode);
		rtbEntrada.setSystemId(systemId);
		
		List<ReportTemplateBean> lData = birMapper.selectReportTemplateFromCodeSystem(rtbEntrada);		
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
		birMapper.insertReportTemplate(rtb);
	}*/
}
