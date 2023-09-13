package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class ContractCapacityConnectionPathsBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2362763704900332839L;
	
	private BigDecimal idn_capacity_path;
	private String path_desc;
	private BigDecimal booked;
	private String edited = "N";
	public BigDecimal getIdn_capacity_path() {
		return idn_capacity_path;
	}
	public void setIdn_capacity_path(BigDecimal idn_capacity_path) {
		this.idn_capacity_path = idn_capacity_path;
	}
	public String getPath_desc() {
		return path_desc;
	}
	public void setPath_desc(String path_desc) {
		this.path_desc = path_desc;
	}
	public BigDecimal getBooked() {
		return booked;
	}
	public void setBooked(BigDecimal booked) {
		this.booked = booked;
	}
	public String getEdited() {
		return edited;
	}
	public void setEdited(String edited) {
		this.edited = edited;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityConnectionPathsBean [idn_capacity_path=");
		builder.append(idn_capacity_path);
		builder.append(", path_desc=");
		builder.append(path_desc);
		builder.append(", booked=");
		builder.append(booked);
		builder.append(", edited=");
		builder.append(edited);
		builder.append("]");
		return builder.toString();
	}
	
	
}
