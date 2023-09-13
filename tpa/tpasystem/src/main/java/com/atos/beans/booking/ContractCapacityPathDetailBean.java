package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractCapacityPathDetailBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4993238080696362938L;

	private BigDecimal idn_area;
	private String area_code;
	private BigDecimal idn_area_tech_capacity;
	private Date start_date;
	private Date end_date;
	private BigDecimal technical_capacity;
	private BigDecimal remain_booked;
	private BigDecimal booked_capacity;
	private BigDecimal available;
	private String is_entry_area;

	private ArrayList<BigDecimal> list_values_remain_booked = new ArrayList<BigDecimal>();;
	private ArrayList<BigDecimal> list_values_available = new ArrayList<BigDecimal>();;
	
	public BigDecimal getIdn_area() {
		return idn_area;
	}
	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public BigDecimal getIdn_area_tech_capacity() {
		return idn_area_tech_capacity;
	}
	public void setIdn_area_tech_capacity(BigDecimal idn_area_tech_capacity) {
		this.idn_area_tech_capacity = idn_area_tech_capacity;
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
	public BigDecimal getTechnical_capacity() {
		return technical_capacity;
	}
	public void setTechnical_capacity(BigDecimal technical_capacity) {
		this.technical_capacity = technical_capacity;
	}
	public BigDecimal getRemain_booked() {
		return remain_booked;
	}
	public void setRemain_booked(BigDecimal remain_booked) {
		this.remain_booked = remain_booked;
	}
	public String getIs_entry_area() {
		return is_entry_area;
	}
	public void setIs_entry_area(String is_entry_area) {
		this.is_entry_area = is_entry_area;
	}
	public BigDecimal getAvailable() {
		return this.available;
	}
	public void setAvailable(BigDecimal available) {
		this.available = available;
	}
	public BigDecimal getBooked_capacity() {
		return booked_capacity;
	}
	public void setBooked_capacity(BigDecimal booked_capacity) {
		this.booked_capacity = booked_capacity;
	}
	public ArrayList<BigDecimal> getList_values_remain_booked() {
		return list_values_remain_booked;
	}
	public void setList_values_remain_booked(ArrayList<BigDecimal> list_values_remain_booked) {
		this.list_values_remain_booked = list_values_remain_booked;
	}
	public ArrayList<BigDecimal> getList_values_available() {
		return list_values_available;
	}
	public BigDecimal getAll_value_available(){
		BigDecimal bd = new BigDecimal(0);
		for(int i=0;i<this.list_values_available.size();i++) {
			bd = bd.add(list_values_available.get(i));
		}
		return bd;	
	}
	public BigDecimal getTotal_available() {
		BigDecimal bd = new BigDecimal(0);
		bd = bd.add((this.available==null ? new BigDecimal(0): this.available)).subtract(getAll_value_available());
		return bd;
	}
	public BigDecimal getAll_value_remain_booked(){
		BigDecimal bd = new BigDecimal(0);
		for(int i=0;i<this.list_values_remain_booked.size();i++) {
			bd = bd.add(list_values_remain_booked.get(i));
		}
		return bd;	
	}
	public BigDecimal getTotal_remain_booked() {
		BigDecimal bd = new BigDecimal(0);
		bd = bd.add((this.remain_booked==null ? new BigDecimal(0) : this.remain_booked)).subtract(getAll_value_remain_booked());
		return bd;
	}
	
	public void setList_values_available(ArrayList<BigDecimal> list_values_available) {
		this.list_values_available = list_values_available;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathDetailBean [idn_area=");
		builder.append(idn_area);
		builder.append(", area_code=");
		builder.append(area_code);
		builder.append(", idn_area_tech_capacity=");
		builder.append(idn_area_tech_capacity);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", technical_capacity=");
		builder.append(technical_capacity);
		builder.append(", remain_booked=");
		builder.append(remain_booked);
		builder.append(", booked_capacity=");
		builder.append(booked_capacity);
		builder.append(", available=");
		builder.append(available);
		builder.append(", is_entry_area=");
		builder.append(is_entry_area);
		builder.append(", list_values_remain_booked=");
		builder.append(list_values_remain_booked);
		builder.append(", list_values_available=");
		builder.append(list_values_available);
		builder.append("]");
		return builder.toString();
	}
	
	
}
