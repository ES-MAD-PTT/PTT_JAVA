package com.atos.filters.dam;

import java.io.Serializable;
import java.util.Date;

public class ShipperFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384739501695766356L;

	private String id;
	private String companyName;
	private Date startDate;
	private Date endDate;

	public ShipperFilter() {
		this.id = null;
		this.companyName = null;
		this.startDate = null;
		this.endDate = null;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	@Override
	public String toString() {
		return "ShipperFilter [id=" + id + ", companyName=" + companyName + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}

	
}
