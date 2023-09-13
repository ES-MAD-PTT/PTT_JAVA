package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntradayAccImbalanceInventoryAdjustmentFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private String id;	
	private Date gasDay;
	private BigDecimal idn_system;//offshore	
	

	public IntradayAccImbalanceInventoryAdjustmentFilter() {
		
		this.id = null;
		this.gasDay = null;		
		this.idn_system=null;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getGasDay() {
		return gasDay;
	}
	
	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}
	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}	
	
}
