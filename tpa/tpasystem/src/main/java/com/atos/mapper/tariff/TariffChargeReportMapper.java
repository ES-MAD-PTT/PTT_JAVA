package com.atos.mapper.tariff;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.atos.beans.ComboFilter;
import com.atos.beans.ComboFilterNS;
import com.atos.beans.tariff.TariffChargeDetailBean;
import com.atos.beans.tariff.TariffChargeReportBean;
import com.atos.filters.tariff.TariffChargeReportFilter;

public interface TariffChargeReportMapper {

	public List<ComboFilterNS> selectShippersCombo(TariffChargeReportFilter filter);

	public List<ComboFilterNS> selectTariffIdComboFiltro(TariffChargeReportFilter filter);

	public List<ComboFilterNS> selectTariffIdComboBac(TariffChargeReportFilter filter);

	public List<TariffChargeReportBean> selectTariffChargeReports(TariffChargeReportFilter filters);

	public int insertTariffChargeMonth(TariffChargeReportBean tariffChargeReport);

	public int updateTariffChargeMonth(TariffChargeReportBean tariffChargeReport);

	public int updateTariffCharge(TariffChargeReportFilter filter);

	public String ejecRunTariff(TariffChargeReportBean feeData);

	public String ejecBacCalc(TariffChargeReportBean feeData);
	
	public int updateNewVersion(TariffChargeReportBean bean);
	
	public String getNewVersion(TariffChargeReportBean feeData);

	public BigDecimal getRepartosShipperDay(TariffChargeReportBean newRunTariff);

	public BigDecimal renderBacCal(TariffChargeReportFilter filter);

	public int insertTariffCharge(TariffChargeReportBean tariffChargeReport);

	public int insertSelectTariffChargeMonth(TariffChargeReportBean tariffChargeReport);

	public int insertSelectTariffChargeSource(TariffChargeReportBean tariffChargeReport);

	public BigDecimal getIdnNuevaCabecera(TariffChargeReportBean tariffChargeReport);

	public String getShipperGroupID(BigDecimal idn_user_group);

	public String getCodeTarifCharge(TariffChargeReportFilter filter);

	public BigDecimal getIdnLastTarifCharge(TariffChargeReportFilter filter);

	public String getCodeLastTarifCharge(TariffChargeReportFilter filter);

	public String getCodeCompareTarifCharge(TariffChargeReportFilter filter);
	
	public BigDecimal getIdFromTariffCharge(String tariff_charge_code);

	public String getComments(TariffChargeReportFilter filter);

	public String getOffSpec(TariffChargeReportFilter filter);

	public String getInvoiceSent(TariffChargeReportFilter filter);

	public List<TariffChargeDetailBean> selectDetailCapacityCharge(TariffChargeReportBean item);

	//ch715
	//public List<TariffChargeDetailBean> selectDetailComodityCharge(TariffChargeReportBean item);
	public List<TariffChargeDetailBean> selectDetailComodityChargeEntry(TariffChargeReportBean item);
	public List<TariffChargeDetailBean> selectDetailComodityChargeExit(TariffChargeReportBean item);
	

	public List<TariffChargeDetailBean> selectDetailOverUsageChargeEntry(TariffChargeReportBean item);

	public List<TariffChargeDetailBean> selectDetailOverUsageChargeExit(TariffChargeReportBean item);

	public List<TariffChargeDetailBean> selectDetailImbalancePenalty(TariffChargeReportBean item);

	public int invoiceSentUpdate(TariffChargeReportFilter filter);
	
	public int invoiceSentFalseUpdate(TariffChargeReportFilter filter);
	
	//ch721
	public BigDecimal getBacAcmount(TariffChargeReportFilter filter);
	
	public List<ComboFilter> selectTypeCriteria();
	
	public List<ComboFilter> selectModeCriteria();
	
	public int insertTypeSelected(HashMap<String,Object> map);
	
	public int insertModeSelected(HashMap<String,Object> map);
	
	public List<TariffChargeReportBean> compare(BigDecimal idn_tariff_charge);
    public List<TariffChargeReportBean> compare2(BigDecimal idn_tariff_charge_compare);
	
}
