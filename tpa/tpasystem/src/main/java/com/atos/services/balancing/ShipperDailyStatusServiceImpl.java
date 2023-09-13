package com.atos.services.balancing;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.balancing.ShipperDailyStatusBean;
import com.atos.beans.balancing.ShipperDailyStatusOffshoreBean;
import com.atos.filters.balancing.ShipperDailyStatusFilter;
import com.atos.mapper.balancing.ShipperDailyStatusMapper;
import com.atos.utils.POIXSSFExcelUtils;

@Service("ShipperDailyStatusService")
public class ShipperDailyStatusServiceImpl implements ShipperDailyStatusService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7123677875086212210L;

	//private static final String strShipperDailyStatusTemplateCode = "BALANCE_REPORT";
	
	@Autowired
	private ShipperDailyStatusMapper bsrMapper;

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
	
	public List<ShipperDailyStatusBean> search(ShipperDailyStatusFilter filter){
		return bsrMapper.selectShipperDailyStatus(filter);
	}
	
	public List<ShipperDailyStatusOffshoreBean> searchOffshore(ShipperDailyStatusFilter filter){
		return bsrMapper.selectShipperDailyStatusOffshore(filter);
	}
	
	public void copySheets(XSSFSheet srcSheet, XSSFSheet destSheet){   
		excelUtil.copySheets(srcSheet, destSheet, true);   
	}

	
}
