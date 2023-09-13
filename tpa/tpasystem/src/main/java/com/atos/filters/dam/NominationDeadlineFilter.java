package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NominationDeadlineFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private String submisionManage;
	private String type;
//	private BigDecimal gasDay;
	private Date startDate;
	private Date endDate;
	

	public NominationDeadlineFilter() {
		
		this.submisionManage = null;
		this.type = null;
		this.startDate = null;
		this.endDate = null;
	}



	public String getSubmisionManage() {
		return submisionManage;
	}



	public void setSubmisionManage(String submisionManage) {
		this.submisionManage = submisionManage;
	}





	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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
		return "NominationDeadlineFilter [submissionManagement=" + submisionManage + ", type=" + type + 
				", startDate=" + startDate + ", endDate=" + endDate + "]";
	}


	
	
}
