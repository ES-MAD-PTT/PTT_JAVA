package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GuestUserFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private BigDecimal idn_user;
	private String id;
	private String name;
	private BigDecimal shipperOperator;
	private Date startDate;
	private Date endDate;
	
	
	
	public GuestUserFilter() {
		this.idn_user = null;
		this.id=null;
		this.name =null;
		this.shipperOperator = null;
		this.startDate = null;
		this.endDate =null;
	}



	public BigDecimal getIdn_user() {
		return idn_user;
	}


	public void setIdn_user(BigDecimal idn_user) {
		this.idn_user = idn_user;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public BigDecimal getShipperOperator() {
		return shipperOperator;
	}


	public void setShipperOperator(BigDecimal shipperOperator) {
		this.shipperOperator = shipperOperator;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "GuestUserFilter [idn_user=" + idn_user + ", name=" + name + ", shipperOperator=" + shipperOperator
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
	
}
