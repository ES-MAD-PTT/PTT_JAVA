package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MeteredPointFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384739501695766356L;

	//private String id;
	private BigDecimal id;
	private String pointType;
	private String systemCode;
	private String zone;
	private String area;
	private Date startDate;
	private Date endDate;
	private BigDecimal idn_system;//offshore
	private boolean check_version_date;
	private String version_date;
	
	public MeteredPointFilter() {
		this.id = null;
		this.pointType = null;
		this.systemCode= null;
		this.zone = null;
		this.area = null;
		this.startDate = null;
		this.endDate = null;
		this.idn_system= null;
		this.check_version_date = false;
		this.version_date = null;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
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

	public boolean isCheck_version_date() {
		return check_version_date;
	}

	public void setCheck_version_date(boolean check_version_date) {
		this.check_version_date = check_version_date;
		if(check_version_date) {
			this.version_date = "S";
		} else {
			this.version_date = null;
		}
	}

	public String getVersion_date() {
		return version_date;
	}

	public void setVersion_date(String version_date) {
		this.version_date = version_date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeteredPointFilter [id=");
		builder.append(id);
		builder.append(", pointType=");
		builder.append(pointType);
		builder.append(", systemCode=");
		builder.append(systemCode);
		builder.append(", zone=");
		builder.append(zone);
		builder.append(", area=");
		builder.append(area);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", idn_system=");
		builder.append(idn_system);
		builder.append(", version_date=");
		builder.append(version_date);
		builder.append("]");
		return builder.toString();
	}

	
	
}
