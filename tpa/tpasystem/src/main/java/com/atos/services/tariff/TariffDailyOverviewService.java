package com.atos.services.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.UserBean;
import com.atos.beans.tariff.TariffChargeDetailBean;
import com.atos.beans.tariff.TariffDailyOverviewBean;
import com.atos.filters.tariff.TariffDailyOverviewFilter;



public interface TariffDailyOverviewService extends Serializable{
	
	public Map<BigDecimal, Object> selectShippers(TariffDailyOverviewFilter filter);
	public Map<BigDecimal, Object> selectContracts(TariffDailyOverviewFilter filter);
	public Map<BigDecimal, Object> selectVariable(TariffDailyOverviewFilter filter);
	public Map<BigDecimal, Object> selectSystemPointType(TariffDailyOverviewFilter filter);
	
	public String getCodeTarifCode(TariffDailyOverviewFilter filter);
	public String getCodeTarifType(TariffDailyOverviewFilter filter);
	
	public BigDecimal getIdnTariffCharge(TariffDailyOverviewFilter filter);
	public List<TariffDailyOverviewBean> selectTariffDailyOverviews(TariffDailyOverviewFilter filter);
	
	public List<TariffDailyOverviewBean> selectDetailCapacityCharge(TariffDailyOverviewFilter item);
	//public List<TariffDailyOverviewBean> selectDetailComodityCharge(TariffDailyOverviewFilter item); //CH715
	public List<TariffDailyOverviewBean> selectDetailComodityChargeEntry(TariffDailyOverviewFilter item); 
	public List<TariffDailyOverviewBean> selectDetailComodityChargeExit(TariffDailyOverviewFilter item); 
	
	public List<TariffDailyOverviewBean> selectDetailOverUsageChargeEntry(TariffDailyOverviewFilter item);
	public List<TariffDailyOverviewBean> selectDetailOverUsageChargeExit(TariffDailyOverviewFilter item);
	public List<TariffDailyOverviewBean> selectDetailImbalancePenalty(TariffDailyOverviewFilter item);
	
	
	

}
