package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractCapacityPathInsertBean extends UserAudBean implements Serializable{

	private static final long serialVersionUID = -5882918417124373362L;
	
	private BigDecimal idn_contract_capacity_path;
	private BigDecimal idn_contract;
	private Date start_date;
	private Date end_date;
	private BigDecimal entry_point;
	private BigDecimal exit_point;
	private BigDecimal idn_capacity_path;
	private BigDecimal assigned_quantity;
	private Date version_date;
	private String username;
	
	private BigDecimal idn_contract_agreement;
	private BigDecimal idn_contract_point_orig;
	private BigDecimal idn_contract_point_dest;
	public BigDecimal getIdn_contract_capacity_path() {
		return idn_contract_capacity_path;
	}
	public void setIdn_contract_capacity_path(BigDecimal idn_contract_capacity_path) {
		this.idn_contract_capacity_path = idn_contract_capacity_path;
	}
	public BigDecimal getIdn_contract() {
		return idn_contract;
	}
	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public BigDecimal getEntry_point() {
		return entry_point;
	}
	public void setEntry_point(BigDecimal entry_point) {
		this.entry_point = entry_point;
	}
	public BigDecimal getExit_point() {
		return exit_point;
	}
	public void setExit_point(BigDecimal exit_point) {
		this.exit_point = exit_point;
	}
	public BigDecimal getIdn_capacity_path() {
		return idn_capacity_path;
	}
	public void setIdn_capacity_path(BigDecimal idn_capacity_path) {
		this.idn_capacity_path = idn_capacity_path;
	}
	public BigDecimal getAssigned_quantity() {
		return assigned_quantity;
	}
	public void setAssigned_quantity(BigDecimal assigned_quantity) {
		this.assigned_quantity = assigned_quantity;
	}
	public Date getVersion_date() {
		return version_date;
	}
	public void setVersion_date(Date version_date) {
		this.version_date = version_date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigDecimal getIdn_contract_agreement() {
		return idn_contract_agreement;
	}
	public void setIdn_contract_agreement(BigDecimal idn_contract_agreement) {
		this.idn_contract_agreement = idn_contract_agreement;
	}
	public BigDecimal getIdn_contract_point_orig() {
		return idn_contract_point_orig;
	}
	public void setIdn_contract_point_orig(BigDecimal idn_contract_point_orig) {
		this.idn_contract_point_orig = idn_contract_point_orig;
	}
	public BigDecimal getIdn_contract_point_dest() {
		return idn_contract_point_dest;
	}
	public void setIdn_contract_point_dest(BigDecimal idn_contract_point_dest) {
		this.idn_contract_point_dest = idn_contract_point_dest;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathInsertBean [idn_contract_capacity_path=");
		builder.append(idn_contract_capacity_path);
		builder.append(", idn_contract=");
		builder.append(idn_contract);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", entry_point=");
		builder.append(entry_point);
		builder.append(", exit_point=");
		builder.append(exit_point);
		builder.append(", idn_capacity_path=");
		builder.append(idn_capacity_path);
		builder.append(", assigned_quantity=");
		builder.append(assigned_quantity);
		builder.append(", version_date=");
		builder.append(version_date);
		builder.append(", username=");
		builder.append(username);
		builder.append(", idn_contract_agreement=");
		builder.append(idn_contract_agreement);
		builder.append(", idn_contract_point_orig=");
		builder.append(idn_contract_point_orig);
		builder.append(", idn_contract_point_dest=");
		builder.append(idn_contract_point_dest);
		builder.append("]");
		return builder.toString();
	}
	

}
