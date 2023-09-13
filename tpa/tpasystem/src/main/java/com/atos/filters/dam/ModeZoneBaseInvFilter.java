package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ModeZoneBaseInvFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private String id;	
	private BigDecimal idn_zone;
	private BigDecimal idn_mode;
	private Date startDate;
	private BigDecimal idn_system;//offshore	
	

	public ModeZoneBaseInvFilter() {
		
		this.id = null;
		this.idn_mode = null;
		this.idn_zone= null;
		this.startDate = null;		
		this.idn_system=null;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public BigDecimal getIdn_mode() {
		return idn_mode;
	}

	public void setIdn_mode(BigDecimal mode) {
		this.idn_mode = mode;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal zone) {
		this.idn_zone = zone;
	}

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}	
	
}
