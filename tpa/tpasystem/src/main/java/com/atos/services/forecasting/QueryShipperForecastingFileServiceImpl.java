package com.atos.services.forecasting;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.forecasting.OperationFileBean;
import com.atos.beans.forecasting.QueryShipperForecastingFileBean;
import com.atos.filters.forecasting.QueryShipperForecastingFileFilter;
import com.atos.mapper.forecasting.QueryShipperForecastingMapper;
 
@Service("queryShipperForecastingFileService")
public class QueryShipperForecastingFileServiceImpl implements QueryShipperForecastingFileService{
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1274495587942564993L;
	private static final Logger log = LogManager.getLogger(QueryShipperForecastingFileService.class);
	
	@Autowired
	private QueryShipperForecastingMapper queryShipperMapper;

	
	@Override
	public List<QueryShipperForecastingFileBean> selectQueryForecasting(QueryShipperForecastingFileFilter filter) {

		// Se crea un nuevo filtro, para añadir los % e invocar la consulta.
		QueryShipperForecastingFileFilter tmpFilter = new QueryShipperForecastingFileFilter(filter);
		String tmpForeCode = filter.getForecasting_code();
		
		if((tmpForeCode != null) && (!"".equalsIgnoreCase(tmpForeCode)))
			tmpFilter.setForecasting_code("%" + tmpForeCode + "%");
				
		return queryShipperMapper.selectQueryForecasting(tmpFilter);
	}
	
	@Override
	public Map<BigDecimal, Object> selectTermCode() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
 		List<ComboFilterNS> list = queryShipperMapper.selectTermCode();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 
	}

	@Override
	public Map<String, Object> selectShipper(QueryShipperForecastingFileFilter filter) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
 		List<ComboFilterNS> list = queryShipperMapper.selectShipper(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey().toString(), combo.getValue());
		}
		return map;
	}

	@Override
	public StreamedContent getFile(TreeMap<String, BigDecimal> map) throws Exception {
		List<OperationFileBean> l = queryShipperMapper.selectGetFile(map);
		if(l.size()==0){
			return null;
		} else {
			OperationFileBean bean = l.get(0);
			try {
			ByteArrayInputStream ba = new ByteArrayInputStream(bean.getBinary_data());
			return new DefaultStreamedContent(ba, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bean.getFile_name());
			} catch (Exception e) {
				log.error("error retrieving file data: " + e.getMessage(), e);
				throw new Exception("data seems corrupted");
			}
		}
	}
	
	
}



