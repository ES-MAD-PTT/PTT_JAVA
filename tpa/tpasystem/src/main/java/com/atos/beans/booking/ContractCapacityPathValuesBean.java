package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.atos.beans.UserAudBean;
import com.atos.utils.DateUtil;

public class ContractCapacityPathValuesBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7669363338301535020L;

	private BigDecimal idn_system_point;
	private Date start_date;
	private Date end_date;
	private String month_year;
	private BigDecimal quantity;

	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}
	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
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
	public String getMonth_year() {
		return month_year;
	}
	public void setMonth_year(String month_year) {
		this.month_year = month_year;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathValuesBean [idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", month_year=");
		builder.append(month_year);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
