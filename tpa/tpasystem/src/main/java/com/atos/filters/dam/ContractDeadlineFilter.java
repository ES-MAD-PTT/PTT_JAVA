package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ContractDeadlineFilter implements Serializable {

	private static final long serialVersionUID = -5384739501695766356L;

	private String processType;
	private String type;
	private Date startDate;
	private Date endDate;

	public ContractDeadlineFilter() {

		this.processType = null;
		this.type = null;
		this.startDate = null;
		this.endDate = null;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
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
		return "ContractDeadlineFilter [submissionManagement=" + processType + ", type=" + type + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}

}
