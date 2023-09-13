package com.atos.filters.tariff;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreditDebitNoteIdnOperTermFilter implements Serializable{
	
	private static final long serialVersionUID = -5384739501695766356L;

	private BigDecimal charge;
	private BigDecimal idn_tariff_code_detail;
	
	public CreditDebitNoteIdnOperTermFilter() {
		super();
		this.charge = null;
		this.idn_tariff_code_detail = null;		
	}
	
	public CreditDebitNoteIdnOperTermFilter(BigDecimal charge, BigDecimal idn_tariff_code_detail) {
		super();		
		this.charge = charge;
		this.idn_tariff_code_detail = idn_tariff_code_detail;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public BigDecimal getIdn_tariff_code_detail() {
		return idn_tariff_code_detail;
	}

	public void setIdn_tariff_code_detail(BigDecimal idn_tariff_code_detail) {
		this.idn_tariff_code_detail = idn_tariff_code_detail;
	}
}
