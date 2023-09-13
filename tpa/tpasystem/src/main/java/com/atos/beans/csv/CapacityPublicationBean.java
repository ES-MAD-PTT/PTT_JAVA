package com.atos.beans.csv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CapacityPublicationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4843796079783523974L;

	private BigDecimal idn_val_avail_capacity;
	
	private String area;
	private Date monthYear;
	private Date endDate;
	private BigDecimal tech_cap;
	private BigDecimal available_cap;
	private BigDecimal booked_cap;
	
	private String clave_unica;

	
	public BigDecimal getIdn_val_avail_capacity() {
		return idn_val_avail_capacity;
	}
	public void setIdn_val_avail_capacity(BigDecimal idn_val_avail_capacity) {
		this.idn_val_avail_capacity = idn_val_avail_capacity;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Date getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(Date monthYear) {
		this.monthYear = monthYear;
	}

	public BigDecimal getTech_cap() {
		return tech_cap;
	}

	public void setTech_cap(BigDecimal tech_cap) {
		this.tech_cap = tech_cap;
	}

	public BigDecimal getAvailable_cap() {
		return available_cap;
	}

	public void setAvailable_cap(BigDecimal available_cap) {
		this.available_cap = available_cap;
	}

	public BigDecimal getBooked_cap() {
		return booked_cap;
	}

	public void setBooked_cap(BigDecimal booked_cap) {
		this.booked_cap = booked_cap;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getClave_unica() {
		return clave_unica;
	}
	public void setClave_unica(String clave_unica) {
		this.clave_unica = clave_unica;
	}
	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append("idn_val_avail_capacity;area;dateFrom;dateTo;tech_cap;available_cap;booked_cap");
		return builder.toString();
	}

	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((idn_val_avail_capacity==null ? "" : idn_val_avail_capacity.intValue())+";");
		builder.append((area==null ? "" : area)+";");
		builder.append((monthYear==null ? "" : sdf.format(monthYear))+";");
		builder.append((endDate==null ? "" : sdf.format(endDate))+";");
		builder.append((tech_cap==null ? "" : tech_cap.doubleValue())+";");
		builder.append((available_cap==null ? "" : available_cap.doubleValue())+";");
		builder.append((booked_cap== null ? "" : booked_cap.doubleValue()));
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CapacityPublicationBean [idn_val_avail_capacity=");
		builder.append(idn_val_avail_capacity);
		builder.append(", area=");
		builder.append(area);
		builder.append(", monthYear=");
		builder.append(monthYear);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", tech_cap=");
		builder.append(tech_cap);
		builder.append(", available_cap=");
		builder.append(available_cap);
		builder.append(", booked_cap=");
		builder.append(booked_cap);
		builder.append("]");
		return builder.toString();
	}

	
	
}
