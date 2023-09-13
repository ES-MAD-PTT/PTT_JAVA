package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AreaFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private String id;
	private String name;
	//private String system;
	private String zone;
	private Date startDate;
	private Date endDate;
	private BigDecimal idn_system;//offshore
	private BigDecimal idn_area;
	

	public AreaFilter() {
		
		this.id = null;
		this.name = null;
		//this.system = null;
		this.zone= null;
		this.startDate = null;
		this.endDate = null;
		this.idn_system=null;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	/*public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
*/
	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	
	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	@Override
	public String toString() {
		return "AreaFilter [id=" + id + ", name=" + name + ", zone=" + zone + ", startDate=" + startDate + ", endDate="
				+ endDate + ", idn_system=" + idn_system + "]";
	}
		
	
	
}
