package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ForecastingDeadlineFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private String submissionManagement;
	private String type;
	private BigDecimal gasDay;
	private Date startDate;
	private Date endDate;
	

	public ForecastingDeadlineFilter() {
	
		this.type = null;
		this.startDate = null;
		this.endDate = null;
	}


	public String getSubmissionManagement() {
		return submissionManagement;
	}


	public void setSubmissionManagement(String submissionManagement) {
		this.submissionManagement = submissionManagement;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public BigDecimal getGasDay() {
		return gasDay;
	}


	public void setGasDay(BigDecimal gasDay) {
		this.gasDay = gasDay;
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
		return "ForecastingDeadlineFilter [submissionManagement=" + submissionManagement + ", type=" + type
				+ ", gasDay=" + gasDay + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}


	
	
}
