package com.atos.filters.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CreditDebitNoteFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;


	private BigDecimal idn_user_group; 
	private Date monthYear;
	private String CNDNId;
	private BigDecimal type;
	private BigDecimal idn_system;
	private boolean isShipper;
	
	
	public CreditDebitNoteFilter() {
		
		this.idn_user_group = null;
		this.monthYear=null;
		this.CNDNId=null;
		this.type = null;
		this.idn_system=null;
		this.isShipper = false;
	}


	public CreditDebitNoteFilter(BigDecimal idn_user_group, Date monthYear, String cNDNId, BigDecimal type, BigDecimal idn_system, boolean isShipper) {
		super();
		this.idn_user_group = idn_user_group;
		this.monthYear = monthYear;
		this.CNDNId = cNDNId;
		this.type = type;
		this.idn_system = idn_system;
		this.isShipper = isShipper;
	}


	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}


	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}


	public Date getMonthYear() {
		return monthYear;
	}


	public void setMonthYear(Date monthYear) {
		this.monthYear = monthYear;
	}


	public String getCNDNId() {
		return CNDNId;
	}


	public void setCNDNId(String cNDNId) {
		CNDNId = cNDNId;
	}

	public BigDecimal getType() {
		return type;
	}

	public void setType(BigDecimal type) {
		this.type = type;
	}


	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public boolean isShipper() {
		return isShipper;
	}

	public void setIsShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}
	
	
}
