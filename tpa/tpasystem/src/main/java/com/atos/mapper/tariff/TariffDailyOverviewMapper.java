package com.atos.mapper.tariff;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.tariff.TariffDailyOverviewBean;
import com.atos.filters.tariff.TariffDailyOverviewFilter;

public interface TariffDailyOverviewMapper {

	public List<ComboFilterNS> selectShippersCombo(TariffDailyOverviewFilter filter);

	public List<ComboFilterNS> selectContracts(TariffDailyOverviewFilter filter);

	public List<ComboFilterNS> selectVariable(TariffDailyOverviewFilter filter);

	public List<ComboFilterNS> selectSystemPointType(TariffDailyOverviewFilter filter);

	public String getCodeTarifCode(TariffDailyOverviewFilter filter);

	public String getCodeTarifType(TariffDailyOverviewFilter filter);

	public List<TariffDailyOverviewBean> selectTariffDailyOverviews(TariffDailyOverviewFilter filters);

	public List<TariffDailyOverviewBean> selectDetailCapacityCharge(TariffDailyOverviewFilter item);

	//public List<TariffDailyOverviewBean> selectDetailComodityCharge(TariffDailyOverviewFilter item);
	public List<TariffDailyOverviewBean> selectDetailComodityChargeEntry(TariffDailyOverviewFilter item);
	public List<TariffDailyOverviewBean> selectDetailComodityChargeExit(TariffDailyOverviewFilter item);

	public List<TariffDailyOverviewBean> selectDetailOverUsageChargeEntry(TariffDailyOverviewFilter item);

	public List<TariffDailyOverviewBean> selectDetailOverUsageChargeExit(TariffDailyOverviewFilter item);

	public List<TariffDailyOverviewBean> selectDetailImbalancePenalty(TariffDailyOverviewFilter item);

	public BigDecimal getIdnTariffCharge(TariffDailyOverviewFilter filter);
}
