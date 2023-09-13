package com.atos.services.tariff;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.tariff.TariffDailyOverviewBean;
import com.atos.filters.tariff.TariffDailyOverviewFilter;
import com.atos.mapper.tariff.TariffDailyOverviewMapper;

@Service("tariffDailyOverviewService")
public class TariffDailyOverviewServiceImpl implements TariffDailyOverviewService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private TariffDailyOverviewMapper tariffDailyOverviewMapper;

	private static final Logger log = LogManager.getLogger("com.atos.services.tariff.TariffDailyOverviewImpl");

	public List<TariffDailyOverviewBean> selectTariffDailyOverviews(TariffDailyOverviewFilter filter) {
		return tariffDailyOverviewMapper.selectTariffDailyOverviews(filter);

	}

	@Override
	public Map<BigDecimal, Object> selectShippers(TariffDailyOverviewFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tariffDailyOverviewMapper.selectShippersCombo(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectContracts(TariffDailyOverviewFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tariffDailyOverviewMapper.selectContracts(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectVariable(TariffDailyOverviewFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tariffDailyOverviewMapper.selectVariable(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public Map<BigDecimal, Object> selectSystemPointType(TariffDailyOverviewFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = tariffDailyOverviewMapper.selectSystemPointType(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}

	@Override
	public List<TariffDailyOverviewBean> selectDetailCapacityCharge(TariffDailyOverviewFilter item) {
		return tariffDailyOverviewMapper.selectDetailCapacityCharge(item);
	}

	/*@Override  ch715
	public List<TariffDailyOverviewBean> selectDetailComodityCharge(TariffDailyOverviewFilter item) {
		return tariffDailyOverviewMapper.selectDetailComodityCharge(item);
	}
	*/
	@Override
	public List<TariffDailyOverviewBean> selectDetailComodityChargeEntry(TariffDailyOverviewFilter item) {
		return tariffDailyOverviewMapper.selectDetailComodityChargeEntry(item);
	}
	
	@Override
	public List<TariffDailyOverviewBean> selectDetailComodityChargeExit(TariffDailyOverviewFilter item) {
		return tariffDailyOverviewMapper.selectDetailComodityChargeExit(item);
	}

	@Override
	public List<TariffDailyOverviewBean> selectDetailOverUsageChargeEntry(TariffDailyOverviewFilter item) {
		return tariffDailyOverviewMapper.selectDetailOverUsageChargeEntry(item);
	}

	@Override
	public List<TariffDailyOverviewBean> selectDetailOverUsageChargeExit(TariffDailyOverviewFilter item) {
		return tariffDailyOverviewMapper.selectDetailOverUsageChargeExit(item);
	}

	@Override
	public List<TariffDailyOverviewBean> selectDetailImbalancePenalty(TariffDailyOverviewFilter item) {
		return tariffDailyOverviewMapper.selectDetailImbalancePenalty(item);
	}

	@Override
	public String getCodeTarifCode(TariffDailyOverviewFilter filter) {

		return tariffDailyOverviewMapper.getCodeTarifCode(filter);
	}

	@Override
	public String getCodeTarifType(TariffDailyOverviewFilter filter) {

		return tariffDailyOverviewMapper.getCodeTarifType(filter);
	}
	
	@Override
	public BigDecimal getIdnTariffCharge(TariffDailyOverviewFilter filter) {
		return tariffDailyOverviewMapper.getIdnTariffCharge(filter);
	}

}
