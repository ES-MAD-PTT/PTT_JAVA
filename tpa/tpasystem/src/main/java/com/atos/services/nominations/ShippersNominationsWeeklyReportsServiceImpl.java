package com.atos.services.nominations;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.nominations.ShippersNominationReportsDailyBean;
import com.atos.beans.nominations.ShippersNominationReportsWeeklyBean;
import com.atos.beans.nominations.ShippersNominationsReportsBean;
import com.atos.filters.nominations.ShippersNominationsReportsFilter;
import com.atos.mapper.nominations.NominationMapper;
import com.atos.mapper.nominations.ShippersNominationsWeeklyReportsMapper;

@Service("shippersNominationsWeeklyReportsService")
public class ShippersNominationsWeeklyReportsServiceImpl implements ShippersNominationsWeeklyReportsService{
	
	private static final long serialVersionUID = -1474869475323574106L;

	@Autowired
	private ShippersNominationsWeeklyReportsMapper mapper;

	@Autowired
	private NominationMapper nomMapper;
	
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
	
	@Override
	public Map<String, BigDecimal> selectZonesNomination() {
		Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>();
 		List<ComboFilterNS> list = nomMapper.selectZonesNomination();
		for (ComboFilterNS combo : list) {
			if (combo == null) continue;
			map.put(combo.getValue(), combo.getKey());
		}
		return map;
	}

	@Override
	public List<ShippersNominationReportsWeeklyBean> selectShipperNomReportsDailyDetail(ShippersNominationsReportsBean bean) {
		return mapper.selectShipperNomReportsDailyDetail(bean);
	}

	@Override
	public List<ShippersNominationReportsDailyBean> selectShipperNomReportsParkUnpark(ShippersNominationsReportsBean bean) {
		return mapper.selectShipperNomReportsParkUnpark(bean);
	}


}
