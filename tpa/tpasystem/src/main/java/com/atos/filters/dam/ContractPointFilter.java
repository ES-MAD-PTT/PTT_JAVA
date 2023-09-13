package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ContractPointFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384739501695766356L;

	private String id;
	private String pointType;
	private String systemCode;
	private String zone;
	private String area;
	private Date startDate;
	private Date endDate;
	private BigDecimal idn_system;//offshore
	
	public ContractPointFilter() {
		this.id = null;
		this.pointType = null;
		this.systemCode= null;
		this.zone = null;
		this.area = null;
		this.startDate = null;
		this.endDate = null;
		this.idn_system= null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	@Override
	public String toString() {
		return "ContractPointFilter [id=" + id + ", pointType=" + pointType + ", systemCode=" + systemCode + ", zone="
				+ zone + ", area=" + area + ", startDate=" + startDate + ", endDate=" + endDate + ", idn_system="
				+ idn_system + "]";
	}

	
	
	}
