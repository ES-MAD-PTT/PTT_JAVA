package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShippersNominationsReportsFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2300744601553291995L;
	
	private String shipper_code;
	private BigDecimal shipper_id;
	private Date start_date;
	private Date end_date;
	private BigDecimal idn_system;
	private boolean isShipper;
	
	public ShippersNominationsReportsFilter() {
		this.shipper_code = null;
		this.start_date = null;
		this.end_date = null;
		this.idn_system = null;
		this.isShipper = false;
		this.shipper_id = null;
	}
	
	public ShippersNominationsReportsFilter(String shipper_code, Date start_date, Date end_date, 
			BigDecimal systemId, boolean isShipper, BigDecimal shipper_id) {
		super();
		this.shipper_code = shipper_code;
		this.start_date = start_date;
		this.end_date = end_date;
		this.idn_system = systemId;
		this.isShipper = isShipper;
		this.shipper_id = shipper_id;
	}

	public String getShipper_code() {
		return shipper_code;
	}

	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
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

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public boolean isShipper() {
		return isShipper;
	}

	public void setIsShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}

	public BigDecimal getShipper_id() {
		return shipper_id;
	}

	public void setShipper_id(BigDecimal shipper_id) {
		this.shipper_id = shipper_id;
	}
	
	
}
