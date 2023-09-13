package com.atos.services.nominations;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.ShippersNominationsReportsBean;
import com.atos.filters.nominations.ShippersNominationsReportsFilter;
import com.atos.mapper.nominations.ShippersNominationsReportsMapper;

@Service("shippersNominationsReportsService")
public class ShippersNominationsReportsServiceImpl implements ShippersNominationsReportsService{
	
	@Autowired
	private ShippersNominationsReportsMapper mapper;
	private static final long serialVersionUID = -1474869475323574106L;
	
	public List<ShippersNominationsReportsBean> selectShippersNomReports(ShippersNominationsReportsFilter filter) {
		return mapper.selectShippersNomReports(filter);
	}
	
	public Map<BigDecimal, Object> selectShippersIds(){
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = mapper.selectShippersIds();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
}
