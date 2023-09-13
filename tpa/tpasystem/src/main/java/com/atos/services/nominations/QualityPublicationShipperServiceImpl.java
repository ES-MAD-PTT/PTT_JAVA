package com.atos.services.nominations;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.QualityPublicationBean;
import com.atos.beans.nominations.QualityPublicationShipperBean;
import com.atos.filters.nominations.QualityPublicationFilter;
import com.atos.filters.nominations.QualityPublicationShipperFilter;
import com.atos.mapper.nominations.QualityPublicationMapper;
import com.atos.mapper.nominations.QualityPublicationShipperMapper;

@Service("qualityPublicationShipperService")
public class QualityPublicationShipperServiceImpl implements QualityPublicationShipperService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3099680219235859006L;

	@Autowired
	private QualityPublicationShipperMapper qpMapper;	

	public Map<BigDecimal, Object> selectZones(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = qpMapper.selectZones();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	//offshore
	public Map<BigDecimal, Object> selectAreasSystem(BigDecimal idn_system){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = qpMapper.selectAreasSystem(idn_system);
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	public Map<BigDecimal, Object> selectShippers(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = qpMapper.selectShippers();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map; 		
	}
	
	
	public List<QualityPublicationShipperBean> search(QualityPublicationShipperFilter filter){
		return qpMapper.selectQualityPublicationShipper(filter);
	}
}
