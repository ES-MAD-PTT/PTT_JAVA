package com.atos.services.balancing;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.ReportTemplateBean;
import com.atos.beans.balancing.BalanceIntradayReportBean;
import com.atos.beans.balancing.BalanceIntradayReportOffshoreBean;
import com.atos.filters.balancing.BalanceIntradayReportFilter;
import com.atos.mapper.balancing.BalanceIntradayReportMapper;
import com.atos.utils.POIXSSFExcelUtils;

@Service("BalanceIntradayReportService")
public class BalanceIntradayReportServiceImpl implements BalanceIntradayReportService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4061868449409857809L;

	private static final String strBalanceIntradayReportTemplateCode = "BALANCE_INTRADAY_REPORT";
	
	@Autowired
	private BalanceIntradayReportMapper birMapper;
	
	private POIXSSFExcelUtils excelUtil = new POIXSSFExcelUtils();
	
	private static final Logger log = LogManager.getLogger("com.atos.services.balancing.BalanceIntradayReportServiceImpl");
	
	
	public Map<BigDecimal, Object> selectShipperId(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = birMapper.selectShipperId();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectTimestamp(BalanceIntradayReportFilter filter){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = birMapper.selectTimestamp(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public List<BalanceIntradayReportBean> search(BalanceIntradayReportFilter filter){
		return birMapper.selectBalances(filter);
	}
		
	public List<BalanceIntradayReportOffshoreBean> searchOffshore(BalanceIntradayReportFilter filter){
		return birMapper.selectBalancesOffshore(filter);
	}
	
	@Override
	public ByteArrayInputStream getReportTemplate(BigDecimal systemId) throws Exception{
		ReportTemplateBean rtbEntrada = new ReportTemplateBean();
		rtbEntrada.setTempCode(strBalanceIntradayReportTemplateCode);
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

	@Override
	public String saveTimestamp(BalanceIntradayReportFilter filters_form, String user) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public String saveTimestamp(BalanceIntradayReportFilter filters_form, String user) {
//		BalanceIntradayReportFormBean bean = new BalanceIntradayReportFormBean();
//		bean.setGasday(filters_form.getGasDay());
//		bean.setUser(user);
//
//		try {
//			birMapper.deleteAllocationShipperFilter(bean);	
//		} catch(Exception e) {
//			log.error("Error deleting timestamps");
//			e.printStackTrace();
//			return "-1";
//		}
//
//
//
//		for(int i=0;i<filters_form.getTimestampVarList().size();i++) {
//			try {
//				BigDecimal idn_allocation = new BigDecimal(filters_form.getTimestampVarList().get(i));
//				bean.setIdn_allocation(idn_allocation);
//
//				int ret = birMapper.insertAllocationShipperFilter(bean);
//
//			} catch (Exception e) {
//				log.error("Error saving timestamps");
//				e.printStackTrace();
//				return "-1";
//			}
//
//		}
//
//		return "0";
//	}  
	
	// Para insertar la plantilla excel en la BD.
	/*public void insertReportTemplate(ReportTemplateBean rtb) throws Exception {
		birMapper.insertReportTemplate(rtb);
	}*/
}
