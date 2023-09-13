package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AreaNominalCapacityFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	
	private BigDecimal area;
	private Date startDate;
	private Date endDate;
	private BigDecimal idn_system;//offshore
	
	

	public AreaNominalCapacityFilter() {
		
		this.area= null;
		this.startDate = null;
		this.endDate = null;
		this.idn_system=null;
	}

	public BigDecimal getArea() {
		return area;
	}


	public void setArea(BigDecimal area) {
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
		return "AreaNominalCapacityFilter [area=" + area + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", idn_system=" + idn_system + "]";
	}

}
