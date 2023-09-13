package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TravellingTimeFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private BigDecimal idn_OriginPoint;
	private BigDecimal idn_DestginPoint;

	private String destination;
	private Date startDate;
	private Date endDate;
	private BigDecimal idn_system;//offshore
	
	

	public TravellingTimeFilter() {
		
		this.idn_OriginPoint = null;
		this.idn_DestginPoint = null;
		this.destination = null;
	
		this.startDate = null;
		this.endDate = null;
		this.idn_system=null;
	}
	
	
	

	public BigDecimal getIdn_OriginPoint() {
		return idn_OriginPoint;
	}


	public void setIdn_OriginPoint(BigDecimal idn_OriginPoint) {
		this.idn_OriginPoint = idn_OriginPoint;
	}

	public BigDecimal getIdn_DestginPoint() {
		return idn_DestginPoint;
	}

	public void setIdn_DestginPoint(BigDecimal idn_DestginPoint) {
		this.idn_DestginPoint = idn_DestginPoint;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
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
		return "TravellingTimeFilter [idn_OriginPoint=" + idn_OriginPoint + ", idn_DestginPoint=" + idn_DestginPoint
				+ ", destination=" + destination + ", startDate=" + startDate + ", endDate=" + endDate + ", idn_system="
				+ idn_system + "]";
	}


	
	
	
}
