package com.atos.services.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.atos.beans.UserBean;
import com.atos.beans.tariff.CreditDebitNoteBean;
import com.atos.beans.tariff.CreditDebitNoteDetailBean;
import com.atos.beans.tariff.TariffChargeDetailBean;
import com.atos.beans.tariff.TariffDailyOverviewBean;
import com.atos.filters.tariff.CreditDebitNoteDetailFilter;
import com.atos.filters.tariff.CreditDebitNoteFilter;
import com.atos.filters.tariff.CreditDebitNoteIdnOperTermFilter;
import com.atos.filters.tariff.TariffDailyOverviewFilter;


public interface CreditDebitNoteService extends Serializable{
	
	public Map<BigDecimal, Object> selectShippers();
	public Map<BigDecimal, Object> selectCnDnIds(CreditDebitNoteFilter filters);
	public Map<BigDecimal, Object> selectTypes();
	public Map<BigDecimal, Object> selectCharges();
	public Map<BigDecimal, Object> selectChargeDetails(BigDecimal charge);
	public BigDecimal selectOperationTerm(CreditDebitNoteIdnOperTermFilter f);
	public Map<BigDecimal, Object> selectContracts(CreditDebitNoteDetailFilter filter);
	public int insertNote(CreditDebitNoteBean newNote);
	public CreditDebitNoteBean selectBean(BigDecimal id);
	public boolean noteExists(CreditDebitNoteBean note);
	public String getShipper(BigDecimal shipperId);
	public String getType(BigDecimal type);
	public String getChargeDesc(BigDecimal charge);
	public String getChargeDetailDesc(BigDecimal chargeDetail);
	public String getContractDesc(BigDecimal contract);
	public int insertCreditDebitNote(CreditDebitNoteBean note);
	public int insertCreditDebitNoteDetails(CreditDebitNoteDetailBean note);
	public List<CreditDebitNoteBean> selectCreditDebitNotes(CreditDebitNoteFilter creditDebitNoteFilter);
	public String selectTypeContract(BigDecimal contract);
}
