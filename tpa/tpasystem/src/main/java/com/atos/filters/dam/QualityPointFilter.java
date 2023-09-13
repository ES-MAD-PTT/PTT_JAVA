package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QualityPointFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384739501695766356L;

	private BigDecimal pointCode; // private BigDecimal id;
	private String typeCode;// private String pointType;
	private Date startDate;
	private Date endDate;
	private BigDecimal idnPipelineSystem;// offshore

	public QualityPointFilter() {
		this.pointCode = null;
		this.typeCode = null;
		this.startDate = null;
		this.endDate = null;
		this.idnPipelineSystem = null;
	}

	public BigDecimal getPointCode() {
		return pointCode;
	}

	public void setPointCode(BigDecimal pointCode) {
		this.pointCode = pointCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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

	public BigDecimal getIdnPipelineSystem() {
		return idnPipelineSystem;
	}

	public void setIdnPipelineSystem(BigDecimal idnPipelineSystem) {
		this.idnPipelineSystem = idnPipelineSystem;
	}

}
