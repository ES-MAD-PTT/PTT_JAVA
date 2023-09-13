package com.atos.filters.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.atos.utils.DateUtil;

public class ContractCapacityPathFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3259112589933771483L;
	
	private BigDecimal idn_booking;
	private String period;
	private BigDecimal idn_system;
	private Date start_date;
	private Date end_date;
	private BigDecimal idn_area_orig;
	private BigDecimal idn_area_dest;
	private BigDecimal idn_shipper;
	
	public BigDecimal getIdn_booking() {
		return idn_booking;
	}
	public void setIdn_booking(BigDecimal idn_booking) {
		this.idn_booking = idn_booking;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
		if(this.period!=null && !this.period.equals("")) {
			String start = this.period.substring(0, this.period.indexOf(" - "));
			String end = this.period.substring(this.period.indexOf(" - ")+3);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				this.start_date = sdf.parse(start);
				this.end_date = sdf.parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	public BigDecimal getIdn_system() {
		return idn_system;
	}
	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public String getStart_date_string() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (this.start_date!=null ? sdf.format(start_date) : null);
	}
	public void setStart_date_string(String start_date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if(start_date==null || start_date.equals("")) {
				this.start_date = null;
			} else {
				this.start_date = sdf.parse(start_date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getEnd_date_string() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (this.end_date!=null ? sdf.format(end_date) : null);
	}
	public void setEnd_date_string(String end_date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if(end_date==null || end_date.equals("")) {
				this.end_date=null;
			} else {
				this.end_date = sdf.parse(end_date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public BigDecimal getIdn_area_orig() {
		return idn_area_orig;
	}
	public void setIdn_area_orig(BigDecimal idn_area_orig) {
		this.idn_area_orig = idn_area_orig;
	}
	public BigDecimal getIdn_area_dest() {
		return idn_area_dest;
	}
	public void setIdn_area_dest(BigDecimal idn_area_dest) {
		this.idn_area_dest = idn_area_dest;
	}
	public BigDecimal getIdn_shipper() {
		return idn_shipper;
	}
	public void setIdn_shipper(BigDecimal idn_shipper) {
		this.idn_shipper = idn_shipper;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathFilter [idn_booking=");
		builder.append(idn_booking);
		builder.append(", period=");
		builder.append(period);
		builder.append(", idn_system=");
		builder.append(idn_system);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", idn_area_orig=");
		builder.append(idn_area_orig);
		builder.append(", idn_area_dest=");
		builder.append(idn_area_dest);
		builder.append(", idn_shipper=");
		builder.append(idn_shipper);
		builder.append("]");
		return builder.toString();
	}
	
	
}
