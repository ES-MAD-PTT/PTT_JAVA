package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CRNotificationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3115765982182076603L;

	private String iDshipper;
	private String requestCode;
	private String arrivingDate;
	private BigDecimal systemId;

	
	public String getiDshipper() {
		return iDshipper;
	}
	public void setiDshipper(String iDshipper) {
		this.iDshipper = iDshipper;
	}
	public String getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}
	public String getArrivingDate() {
		return arrivingDate;
	}
	public void setArrivingDate(String arrivingDate) {
		this.arrivingDate = arrivingDate;
	}
	
	
	public BigDecimal getSystemId() {
		return systemId;
	}
	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}
	@Override
	public String toString() {
		return "CRNotificationBean [iDshipper=" + iDshipper + ", requestCode=" + requestCode + ", arrivingDate="
				+ arrivingDate + "]";
	}
	
	
	
	
	
    
}
