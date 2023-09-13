package com.atos.filters.dam;

import java.io.Serializable;
import java.util.Date;

public class OperatorFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private String id;
	private String name;
	private String division;
	private Date startDate;
	private Date endDate;
	

	public OperatorFilter() {
		
		this.id = null;
		this.name = null;
		this.division = null;
		this.startDate = null;
		this.endDate = null;
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
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
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
		return "OperatorFilter [id=" + id + ", name=" + name + ", division=" + division + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
	
}
