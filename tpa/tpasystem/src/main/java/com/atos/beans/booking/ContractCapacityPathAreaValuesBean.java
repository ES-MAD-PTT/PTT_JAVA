package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.atos.beans.UserAudBean;
import com.atos.utils.DateUtil;

public class ContractCapacityPathAreaValuesBean extends UserAudBean implements Serializable{
 
	private static final long serialVersionUID = 4838512256405218787L;

	private BigDecimal idn_capacity_path;
	private BigDecimal idn_area;
	private BigDecimal quantity;
	private BigDecimal path_step;
	private Date start_date;
	private Date end_date;
	
	public BigDecimal getIdn_capacity_path() {
		return idn_capacity_path;
	}
	public void setIdn_capacity_path(BigDecimal idn_capacity_path) {
		this.idn_capacity_path = idn_capacity_path;
	}
	public BigDecimal getIdn_area() {
		return idn_area;
	}
	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPath_step() {
		return path_step;
	}
	public void setPath_step(BigDecimal path_step) {
		this.path_step = path_step;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathAreaValuesBean [idn_capacity_path=");
		builder.append(idn_capacity_path);
		builder.append(", idn_area=");
		builder.append(idn_area);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", path_step=");
		builder.append(path_step);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append("]");
		return builder.toString();
	}
	

	
}
