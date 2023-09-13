package com.atos.mapper.tariff;

import java.math.BigDecimal;
import java.util.List;

import com.atos.beans.ComboFilterNS;
import com.atos.beans.tariff.CreditDebitNoteBean;
import com.atos.beans.tariff.CreditDebitNoteDetailBean;
import com.atos.beans.tariff.TariffDailyOverviewBean;
import com.atos.filters.tariff.CreditDebitNoteDetailFilter;
import com.atos.filters.tariff.CreditDebitNoteFilter;
import com.atos.filters.tariff.CreditDebitNoteIdnOperTermFilter;
import com.atos.filters.tariff.TariffChargeReportFilter;
import com.atos.filters.tariff.TariffDailyOverviewFilter;

public interface CreditDebitNoteMapper {
	public List<ComboFilterNS> selectShippersCombo();
	public List<ComboFilterNS> selectCnDnIdsCombo(CreditDebitNoteFilter filters);
	public List<ComboFilterNS> selectTypesCombo();
	public List<ComboFilterNS> selectChargesCombo();
	public List<ComboFilterNS> selectContractCombo(CreditDebitNoteDetailFilter filter);
	public List<ComboFilterNS> selectChargeDetailsCombo(BigDecimal charge);
	public BigDecimal selectOperationTerm(CreditDebitNoteIdnOperTermFilter f);
	public BigDecimal noteExists(CreditDebitNoteBean note);
	public int insertNote(CreditDebitNoteBean newNote); 
	public int insertCreditDebitNote(CreditDebitNoteBean note);
	public int insertCreditDebitNoteDetails(CreditDebitNoteDetailBean note);
	public List<CreditDebitNoteBean> selectCreditDebitNotes(CreditDebitNoteFilter filter);
	public CreditDebitNoteBean selectBean(BigDecimal id);
	public String getShipper(BigDecimal shipperId);	
	public String getType(BigDecimal typeId);
	public String getChargeDesc(BigDecimal charge);
	public String getChargeDetailDesc(BigDecimal chargeDetail);
	public String getContractDesc(BigDecimal contract);
	public String selectTypeContract(BigDecimal contract);
}

