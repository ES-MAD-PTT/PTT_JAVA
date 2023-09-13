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
import com.atos.beans.tariff.CreditDebitNoteBean;
import com.atos.beans.tariff.CreditDebitNoteDetailBean;
import com.atos.beans.tariff.TariffDailyOverviewBean;
import com.atos.filters.tariff.CreditDebitNoteDetailFilter;
import com.atos.filters.tariff.CreditDebitNoteFilter;
import com.atos.filters.tariff.CreditDebitNoteIdnOperTermFilter;
import com.atos.filters.tariff.TariffDailyOverviewFilter;
import com.atos.mapper.tariff.CreditDebitNoteMapper;
import com.atos.mapper.tariff.TariffDailyOverviewMapper;

@Service("creditDebitNoteService")
public class CreditDebitNoteServiceImpl implements CreditDebitNoteService {

	private static final long serialVersionUID = 5738619896981240370L;

	@Autowired
	private CreditDebitNoteMapper creditDebitNoteMapper;

	private static final Logger log = LogManager.getLogger("com.atos.services.tariff.CreditDebitNoteServiceImpl");

	@Override
	public Map<BigDecimal, Object> selectShippers() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = creditDebitNoteMapper.selectShippersCombo();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectCnDnIds(CreditDebitNoteFilter filters) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = creditDebitNoteMapper.selectCnDnIdsCombo(filters);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectTypes() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = creditDebitNoteMapper.selectTypesCombo();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectCharges() {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = creditDebitNoteMapper.selectChargesCombo();
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectChargeDetails(BigDecimal charge) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = creditDebitNoteMapper.selectChargeDetailsCombo(charge);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public Map<BigDecimal, Object> selectContracts(CreditDebitNoteDetailFilter filter) {
		Map<BigDecimal, Object> map = new LinkedHashMap<BigDecimal, Object>();
		List<ComboFilterNS> list = creditDebitNoteMapper.selectContractCombo(filter);
		for (ComboFilterNS combo : list) {
			if (combo == null)
				continue;
			map.put(combo.getKey(), combo.getValue());
		}
		return map;
	}
	
	@Override
	public int insertNote(CreditDebitNoteBean newNote) {
		return creditDebitNoteMapper.insertNote(newNote);
	}
	
	@Override
	public List<CreditDebitNoteBean> selectCreditDebitNotes(CreditDebitNoteFilter filter) {
		return creditDebitNoteMapper.selectCreditDebitNotes(filter);
	}
	
	@Override
	public BigDecimal selectOperationTerm(CreditDebitNoteIdnOperTermFilter f) {			
		return creditDebitNoteMapper.selectOperationTerm(f);		
	}
	
	@Override
	public int insertCreditDebitNote(CreditDebitNoteBean note) {
		return creditDebitNoteMapper.insertCreditDebitNote(note);
	}
	
	@Override
	public boolean noteExists(CreditDebitNoteBean note) {
		BigDecimal res = creditDebitNoteMapper.noteExists(note);
		if(res.equals(new BigDecimal(0))) return false;
		else return true;
	}
	
	@Override
	public int insertCreditDebitNoteDetails(CreditDebitNoteDetailBean note) {
		return creditDebitNoteMapper.insertCreditDebitNoteDetails(note);
	}
	
	@Override
	public CreditDebitNoteBean selectBean(BigDecimal id) {
		return creditDebitNoteMapper.selectBean(id);
	}
	
	@Override
	public String getShipper(BigDecimal shipperId) {
		return creditDebitNoteMapper.getShipper(shipperId);
	}
	
	@Override
	public String getType(BigDecimal type) {
		return creditDebitNoteMapper.getType(type);
	}

	@Override
	public String getChargeDesc(BigDecimal charge) {
		return creditDebitNoteMapper.getChargeDesc(charge);
	}
	
	@Override
	public String getChargeDetailDesc(BigDecimal chargeDetail) {
		return creditDebitNoteMapper.getChargeDetailDesc(chargeDetail);
	}
	
	@Override
	public String getContractDesc(BigDecimal contract) {
		return creditDebitNoteMapper.getContractDesc(contract);
	}
	
	@Override
	public String selectTypeContract(BigDecimal contract) {
		return creditDebitNoteMapper.selectTypeContract(contract);
	}
}
