package com.atos.filters.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class TariffDailyOverviewFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;


	private BigDecimal idn_user_group; //key del combo shipper
	private Date shortDate;
	//private BigDecimal variable;
	private BigDecimal idn_tariff_code;//en pantalla se llama variable
	private BigDecimal idn_contract;
	private BigDecimal idn_tariff_charge;
	private BigDecimal idn_system_point_type;
	private BigDecimal idn_system;//offshore
	
	
	public TariffDailyOverviewFilter() {
		
		this.idn_user_group = null;
		this.shortDate=null;
		this.idn_tariff_code=null;
		this.idn_contract=null;
		this.idn_tariff_charge=null;
		this.idn_system_point_type=null;
		this.idn_system=null;
		
	}
	

	public BigDecimal getIdn_contract() {
		return idn_contract;
	}


	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}


	public BigDecimal getIdn_tariff_charge() {
		return idn_tariff_charge;
	}


	public void setIdn_tariff_charge(BigDecimal idn_tariff_charge) {
		this.idn_tariff_charge = idn_tariff_charge;
	}


	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	
	public Date getShortDate() {
		return shortDate;
	}

	public void setShortDate(Date shortDate) {
		this.shortDate = shortDate;
	}

	public BigDecimal getIdn_tariff_code() {
		return idn_tariff_code;
	}


	public void setIdn_tariff_code(BigDecimal idn_tariff_code) {
		this.idn_tariff_code = idn_tariff_code;
	}


	public BigDecimal getIdn_system_point_type() {
		return idn_system_point_type;
	}


	public void setIdn_system_point_type(BigDecimal idn_system_point_type) {
		this.idn_system_point_type = idn_system_point_type;
	}


	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}


	@Override
	public String toString() {
		return "TariffDailyOverviewFilter [idn_user_group=" + idn_user_group + ", shortDate=" + shortDate
				+ ", idn_tariff_code=" + idn_tariff_code + ", idn_contract=" + idn_contract + ", idn_tariff_charge="
				+ idn_tariff_charge + ", idn_system_point_type=" + idn_system_point_type + ", idn_system=" + idn_system
				+ "]";
	}

}
