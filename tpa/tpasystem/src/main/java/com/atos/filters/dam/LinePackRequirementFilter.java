package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LinePackRequirementFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	
	private BigDecimal zone;
	private Date startDate;
	private Date endDate;
	private BigDecimal idn_system;//offshore
	

	public LinePackRequirementFilter() {
		
		this.zone= null;
		this.startDate = null;
		this.endDate = null;
		this.idn_system= null;
	}



	public BigDecimal getZone() {
		return zone;
	}



	public void setZone(BigDecimal zone) {
		this.zone = zone;
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
		return "LinePackRequirementFilter [zone=" + zone + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", idn_system=" + idn_system + "]";
	}

		
	
}
