package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ZoneFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	private String id;
	private BigDecimal idn_zone;
	private String name;
	private String system;
	private BigDecimal idn_system;//offshore
	

	public ZoneFilter() {
		
		this.id = null;
		this.name = null;
		this.system = null;
		this.idn_system=null;
		this.idn_zone=null;
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
	

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	
	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	@Override
	public String toString() {
		return "ZoneFilter [id=" + id + ", name=" + name + ", system=" + system + "]";
	}
	
	
	
}
