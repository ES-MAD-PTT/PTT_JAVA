package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ModeZoneBaseInvBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5255211382691787452L;

	private String id;	
	private String system;
	private String zone;
	private String mode;

	private Date startDate;	

	private BigDecimal idn_mode;
	private BigDecimal idn_pipeline_system;
	private BigDecimal idn_zone;

	public ModeZoneBaseInvBean() {
		super();
	}

	public ModeZoneBaseInvBean(String id, String mode, String system, String zone, Date startDate, 
			BigDecimal idn_mode, BigDecimal idn_pipeline_system, BigDecimal idn_zone) {
		super();
		this.id = id;
		this.mode = mode;
		this.system = system;
		this.zone = zone;
		this.startDate = startDate;		
		this.idn_mode = idn_mode;
		this.idn_pipeline_system = idn_pipeline_system;
		this.idn_zone = idn_zone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
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

	public void setIdn_mode(BigDecimal idn_mode) {
		this.idn_mode = idn_mode;
	}

	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}

	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

}
