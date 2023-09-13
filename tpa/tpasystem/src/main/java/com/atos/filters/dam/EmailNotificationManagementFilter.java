package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmailNotificationManagementFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;
	
	private String module;
	private BigDecimal idn_shipper;
	private BigDecimal idn_system;

	public EmailNotificationManagementFilter() {		
		this.module=null;
		this.idn_shipper=null;
		this.idn_system=null;
	}


	public EmailNotificationManagementFilter(String module, BigDecimal idn_shipper, BigDecimal idn_system) {
		super();
		this.module = module;
		this.idn_shipper = idn_shipper;
		this.idn_system = idn_system;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}


	public BigDecimal getIdn_shipper() {
		return idn_shipper;
	}


	public void setIdn_shipper(BigDecimal idn_shipper) {
		this.idn_shipper = idn_shipper;
	}


	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}
}
