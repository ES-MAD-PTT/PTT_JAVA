package com.atos.filters.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CreditDebitNoteDetailFilter implements Serializable{
	
	private static final long serialVersionUID = -5384739501695766356L;

	private BigDecimal shipper;
	private Date originalPeriod;	
	private BigDecimal idnOperationTerm;
	private BigDecimal idn_system;
	
	public CreditDebitNoteDetailFilter() {
		super();
		this.shipper = null;
		this.originalPeriod = null;
		this.idnOperationTerm = null;
		this.idn_system = null;
	}
	
	public CreditDebitNoteDetailFilter(BigDecimal shipper, Date originalPeriod, BigDecimal idnOperationTerm, BigDecimal idn_system) {
		super();
		this.shipper = shipper;
		this.originalPeriod = originalPeriod;
		this.idnOperationTerm = idnOperationTerm;
		this.idn_system = idn_system;
	}

	public BigDecimal getShipper() {
		return shipper;
	}

	public void setShipper(BigDecimal shipper) {
		this.shipper = shipper;
	}

	public Date getOriginalPeriod() {
		return originalPeriod;
	}

	public void setOriginalPeriod(Date originalPeriod) {
		this.originalPeriod = originalPeriod;
	}

	public BigDecimal getIdnOperationTerm() {
		return idnOperationTerm;
	}

	public void setIdnOperationTerm(BigDecimal idnOperationTerm) {
		this.idnOperationTerm = idnOperationTerm;
	}

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}
}
