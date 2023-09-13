package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class IntradayAccImbalanceInventoryAdjustmentBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5255211382691787452L;

	private String id;	
	private BigDecimal idn_system;
	private BigDecimal east;
	private BigDecimal west;
	private Date gasDay;
	private String comments;

	public IntradayAccImbalanceInventoryAdjustmentBean() {
		super();
	}

	public IntradayAccImbalanceInventoryAdjustmentBean(String id, BigDecimal system, BigDecimal east, BigDecimal west, Date gasDay, String comments) {
		super();
		this.id = id;
		this.idn_system = system;
		this.east = east;
		this.west = west;
		this.gasDay = gasDay;		
		this.comments = comments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public BigDecimal getEast() {
		return east;
	}

	public void setEast(BigDecimal east) {
		this.east = east;
	}

	public BigDecimal getWest() {
		return west;
	}

	public void setWest(BigDecimal west) {
		this.west = west;
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
