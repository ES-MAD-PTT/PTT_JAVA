package com.atos.filters.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class TariffChargeReportFilter extends UserAudBean implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;


	private BigDecimal idn_user_group; //key del combo shipper
	private Date shortDate;
	private BigDecimal idn_tariff_charge;
	
	private String is_invoice_sent;
	private String comments;
	private String idTariff;
	private boolean isShipper;
	
	private BigDecimal idn_system;//offshore
	
	
	public TariffChargeReportFilter() {
		this.idn_user_group = null;
		this.shortDate=null;
		this.idn_tariff_charge=null;
		this.is_invoice_sent=null;
		this.comments=null;
		this.idTariff= null;
		this.isShipper = false;
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


	public String getIs_invoice_sent() {
		return is_invoice_sent;
	}


	public void setIs_invoice_sent(String is_invoice_sent) {
		this.is_invoice_sent = is_invoice_sent;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getIdTariff() {
		return idTariff;
	}


	public void setIdTariff(String idTariff) {
		this.idTariff = idTariff;
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

	@Override
	public String toString() {
		return "TariffChargeReportFilter [idn_user_group=" + idn_user_group + ", shortDate=" + shortDate
				+ ", idn_tariff_charge=" + idn_tariff_charge + ", is_invoice_sent=" + is_invoice_sent + ", comments="
				+ comments + ", idTariff=" + idTariff + ", isShipper=" + isShipper + ", idn_system=" + idn_system + "]";
	}

}
