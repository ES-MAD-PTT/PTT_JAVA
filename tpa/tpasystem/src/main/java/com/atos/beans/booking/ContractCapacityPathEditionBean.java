package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.atos.beans.UserAudBean;

public class ContractCapacityPathEditionBean extends UserAudBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5433961919610271319L;
	
	private BigDecimal idn_shipper;
	private String shipper_code;
	private String shortName;
	private String type_contract;
	private BigDecimal idn_booking;
	private String contract_code;
	private BigDecimal idn_area_orig;
	private String area_code_orig;
	private BigDecimal idn_area_dest;
	private String area_code_dest;
	private BigDecimal booked;
	private BigDecimal idn_capacity_path;
	private String path;
	private Date start_date;
	private Date end_date;

	private ArrayList<String> list_path = new ArrayList<String>();
	private ArrayList<BigDecimal> list_booked = new ArrayList<BigDecimal>();
	private List<ContractCapacityConnectionPathsBean> list_paths = new ArrayList<ContractCapacityConnectionPathsBean>();
	public BigDecimal getIdn_shipper() {
		return idn_shipper;
	}
	public void setIdn_shipper(BigDecimal idn_shipper) {
		this.idn_shipper = idn_shipper;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getType_contract() {
		return type_contract;
	}
	public void setType_contract(String type_contract) {
		this.type_contract = type_contract;
	}
	public BigDecimal getIdn_booking() {
		return idn_booking;
	}
	public void setIdn_booking(BigDecimal idn_booking) {
		this.idn_booking = idn_booking;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public BigDecimal getIdn_area_orig() {
		return idn_area_orig;
	}
	public void setIdn_area_orig(BigDecimal idn_area_orig) {
		this.idn_area_orig = idn_area_orig;
	}
	public String getArea_code_orig() {
		return area_code_orig;
	}
	public void setArea_code_orig(String area_code_orig) {
		this.area_code_orig = area_code_orig;
	}
	public BigDecimal getIdn_area_dest() {
		return idn_area_dest;
	}
	public void setIdn_area_dest(BigDecimal idn_area_dest) {
		this.idn_area_dest = idn_area_dest;
	}
	public String getArea_code_dest() {
		return area_code_dest;
	}
	public void setArea_code_dest(String area_code_dest) {
		this.area_code_dest = area_code_dest;
	}
	public BigDecimal getBooked() {
		return booked;
	}
	public void setBooked(BigDecimal booked) {
		this.booked = booked;
	}
	public BigDecimal getIdn_capacity_path() {
		return idn_capacity_path;
	}
	public void setIdn_capacity_path(BigDecimal idn_capacity_path) {
		this.idn_capacity_path = idn_capacity_path;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public ArrayList<String> getList_path() {
		return list_path;
	}
	public void setList_path(ArrayList<String> list_path) {
		this.list_path = list_path;
	}
	public ArrayList<BigDecimal> getList_booked() {
		return list_booked;
	}
	public void setList_booked(ArrayList<BigDecimal> list_booked) {
		this.list_booked = list_booked;
	}
	public List<ContractCapacityConnectionPathsBean> getList_paths() {
		return list_paths;
	}
	public void setList_paths(List<ContractCapacityConnectionPathsBean> list_paths) {
		this.list_paths = list_paths;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathEditionBean [idn_shipper=");
		builder.append(idn_shipper);
		builder.append(", shipper_short_Name=");
		builder.append(shortName);
		builder.append(", shipper_code=");		
		builder.append(shipper_code);
		builder.append(", type_contract=");
		builder.append(type_contract);
		builder.append(", idn_booking=");
		builder.append(idn_booking);
		builder.append(", contract_code=");
		builder.append(contract_code);
		builder.append(", idn_area_orig=");
		builder.append(idn_area_orig);
		builder.append(", area_code_orig=");
		builder.append(area_code_orig);
		builder.append(", idn_area_dest=");
		builder.append(idn_area_dest);
		builder.append(", area_code_dest=");
		builder.append(area_code_dest);
		builder.append(", booked=");
		builder.append(booked);
		builder.append(", idn_capacity_path=");
		builder.append(idn_capacity_path);
		builder.append(", path=");
		builder.append(path);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", list_path=");
		builder.append(list_path);
		builder.append(", list_booked=");
		builder.append(list_booked);
		builder.append(", list_paths=");
		builder.append(list_paths);
		builder.append("]");
		return builder.toString();
	}
}
