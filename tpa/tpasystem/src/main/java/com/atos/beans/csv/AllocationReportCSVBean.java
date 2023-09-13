package com.atos.beans.csv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AllocationReportCSVBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8196541292177032750L;
	
	private BigDecimal idn_allocation;
	private BigDecimal idn_contract;
	private BigDecimal idn_contract_point;
	private Date gas_day;
	private String shipper_code;
	private String shipper_name;
	private String contract_code;
	private String point_code;
	private String nomination_point;
	private String point_type_code;
	private BigDecimal contracted_value;
	private BigDecimal nominated_value;
	private BigDecimal allocated_value;
	private BigDecimal balancing_gas;
	private BigDecimal overusage;
	public BigDecimal getIdn_allocation() {
		return idn_allocation;
	}
	public void setIdn_allocation(BigDecimal idn_allocation) {
		this.idn_allocation = idn_allocation;
	}
	public BigDecimal getIdn_contract() {
		return idn_contract;
	}
	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}
	public BigDecimal getIdn_contract_point() {
		return idn_contract_point;
	}
	public void setIdn_contract_point(BigDecimal idn_contract_point) {
		this.idn_contract_point = idn_contract_point;
	}
	public Date getGas_day() {
		return gas_day;
	}
	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	public String getShipper_name() {
		return shipper_name;
	}
	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getPoint_code() {
		return point_code;
	}
	public void setPoint_code(String point_code) {
		this.point_code = point_code;
	}
	public String getNomination_point() {
		return nomination_point;
	}
	public void setNomination_point(String nomination_point) {
		this.nomination_point = nomination_point;
	}
	public String getPoint_type_code() {
		return point_type_code;
	}
	public void setPoint_type_code(String point_type_code) {
		this.point_type_code = point_type_code;
	}
	public BigDecimal getContracted_value() {
		return contracted_value;
	}
	public void setContracted_value(BigDecimal contracted_value) {
		this.contracted_value = contracted_value;
	}
	public BigDecimal getNominated_value() {
		return nominated_value;
	}
	public void setNominated_value(BigDecimal nominated_value) {
		this.nominated_value = nominated_value;
	}
	public BigDecimal getAllocated_value() {
		return allocated_value;
	}
	public void setAllocated_value(BigDecimal allocated_value) {
		this.allocated_value = allocated_value;
	}
	public BigDecimal getBalancing_gas() {
		return balancing_gas;
	}
	public void setBalancing_gas(BigDecimal balancing_gas) {
		this.balancing_gas = balancing_gas;
	}
	public BigDecimal getOverusage() {
		return overusage;
	}
	public void setOverusage(BigDecimal overusage) {
		this.overusage = overusage;
	}
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
			builder.append("gas_day;");
			builder.append("shipper_code;");
			builder.append("shipper_name;");
			builder.append("contract_code;");
			builder.append("point_code;");
			builder.append("point_type_code;");
			builder.append("contracted_value;");
			builder.append("nominated_value;");
			builder.append("allocated_value;");
			builder.append("balancing_gas");
			builder.append("overusage");
		return builder.toString();
	}
	
	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((gas_day==null ? "" : sdf.format(gas_day))+";");
		builder.append((shipper_code==null ? "" : shipper_code)+";");
		builder.append((shipper_name==null ? "" : shipper_name)+";");
		builder.append((contract_code==null ? "" : contract_code)+";");
		builder.append((point_code==null ? "" : point_code)+";");
		builder.append((point_type_code==null ? "" : point_type_code)+";");
		builder.append((contracted_value==null ? "" : contracted_value.doubleValue())+";");
		builder.append((nominated_value==null ? "" : nominated_value.doubleValue())+";");
		builder.append((allocated_value==null ? "" : allocated_value.doubleValue())+";");
		builder.append((balancing_gas==null ? "" : balancing_gas.doubleValue())+";");
		builder.append((overusage==null ? "" : overusage.doubleValue()));
		return builder.toString();
	}
	
	
}
