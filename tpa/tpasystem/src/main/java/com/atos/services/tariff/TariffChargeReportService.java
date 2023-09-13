package com.atos.services.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atos.beans.UserBean;
import com.atos.beans.tariff.TariffChargeDetailBean;
import com.atos.beans.tariff.TariffChargeReportBean;
import com.atos.filters.tariff.TariffChargeReportFilter;



public interface TariffChargeReportService extends Serializable{
	
	public Map<BigDecimal, Object> selectShippers(TariffChargeReportFilter filter);
	public String updateTariffChargeMonth(TariffChargeReportBean feeData, TariffChargeReportFilter filters) throws Exception;
	
	public String updateTariffCharge(TariffChargeReportFilter filter) throws Exception;
	
	public List<TariffChargeReportBean> selectTariffChargeReports(TariffChargeReportFilter filter);
	
	public void ejecRunTariff(TariffChargeReportBean feeData, UserBean user) throws Exception;
	public String ejecBacCalc(TariffChargeReportBean bacCalc) throws Exception;
	public String updateNewVersion(TariffChargeReportBean bean) throws Exception;
	public String getNewVersion(TariffChargeReportBean bacCalc) throws Exception;
	public BigDecimal renderBacCal(TariffChargeReportFilter filter);
	
	public Map<BigDecimal, Object> selectTariffIdComboFiltro(TariffChargeReportFilter filter);
	public Map<BigDecimal, Object> selectTariffIdComboBac(TariffChargeReportFilter filter);
	
	public BigDecimal getIdnLastTarifCharge(TariffChargeReportFilter filter);
	public String getCodeLastTarifCharge(TariffChargeReportFilter filter);
	public String getCodeCompareTarifCharge(TariffChargeReportFilter filter);
	public BigDecimal getIdFromTariffCharge(String tariff_charge_code);
	public String getComments(TariffChargeReportFilter filter);
	
	public String getOffSpec(TariffChargeReportFilter filter);
	
	
	public String getInvoiceSent(TariffChargeReportFilter filter);
	public String invoiceSentUpdate(TariffChargeReportFilter filter) throws Exception;
	public String invoiceSentFalseUpdate (TariffChargeReportFilter filter) throws Exception;
	
	public BigDecimal getRepartosShipperDay(TariffChargeReportBean newRunTariff);
	
	public String getShipperGroupID(TariffChargeReportFilter filter);
	public String getShipperGroupID(TariffChargeReportBean selected);
	
	public List<TariffChargeDetailBean> selectDetailCapacityCharge(TariffChargeReportBean item);
	
	//ch715 ahora exit y entry
	//public List<TariffChargeDetailBean> selectDetailComodityCharge(TariffChargeReportBean item);
	
	public List<TariffChargeDetailBean> selectDetailComodityChargeExit(TariffChargeReportBean item);
	public List<TariffChargeDetailBean> selectDetailComodityChargeEntry(TariffChargeReportBean item);
	
	public List<TariffChargeDetailBean> selectDetailOverUsageChargeEntry(TariffChargeReportBean item);
	public List<TariffChargeDetailBean> selectDetailOverUsageChargeExit(TariffChargeReportBean item);
	public List<TariffChargeDetailBean> selectDetailImbalancePenalty(TariffChargeReportBean item);
	
    public String getCodeTarifCharge(TariffChargeReportFilter filter);	

    public BigDecimal getBacAcmount(TariffChargeReportFilter filter);
    
    public Map<String, Object> selectTypeCriteria();
    public Map<String, Object> selectModeCriteria();
    public int insertTypeSelected(HashMap<String,Object> map);
    public int insertModeSelected(HashMap<String,Object> map);
    
    public boolean compare(BigDecimal idn_tariff_charge);
    public boolean compare2(BigDecimal idn_tariff_charge_compare);
}
