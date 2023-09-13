package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class AreaBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5255211382691787452L;

	private String id;
	private String name;
	private String system;
	private String zone;

	private Date startDate;
	private Date endDate;

	private BigDecimal idn_area;
	private BigDecimal idn_pipeline_system;
	private BigDecimal idn_zone;

	public AreaBean() {
		super();
	}

	public AreaBean(String id, String name, String system, String zone, Date startDate, Date endDate,
			BigDecimal idn_area, BigDecimal idn_pipeline_system, BigDecimal idn_zone) {
		super();
		this.id = id;
		this.name = name;
		this.system = system;
		this.zone = zone;
		this.startDate = startDate;
		this.endDate = endDate;
		this.idn_area = idn_area;
		this.idn_pipeline_system = idn_pipeline_system;
		this.idn_zone = idn_zone;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
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

	@Override
	public String toString() {
		return "Area [id=" + id + "]";
	}

}
