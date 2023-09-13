package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InstructedFlowExPostFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;


	private BigDecimal idn_user_group;
	private BigDecimal idn_contract;
	private Date startDate;
	private Date endDate;
	private BigDecimal idn_zone;
	private BigDecimal idn_system;//offshore
	
	public InstructedFlowExPostFilter() {
		
		this.idn_user_group = null;
		this.idn_contract = null;
		this.startDate = null;
		this.endDate = null;
		this.idn_zone = null;
		this.idn_system= null;
	}
	
	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public BigDecimal getIdn_contract() {
		return idn_contract;
	}

	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
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

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	@Override
	public String toString() {
		return "InstructedFlowExPostFilter [idn_user_group=" + idn_user_group + ", idn_contract=" + idn_contract
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", idn_zone=" + idn_zone + ", idn_system="
				+ idn_system + "]";
	}
	
	
}
