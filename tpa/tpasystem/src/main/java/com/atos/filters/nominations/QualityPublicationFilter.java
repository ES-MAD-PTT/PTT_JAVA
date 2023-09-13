package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class QualityPublicationFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1083577828909332260L;
	private BigDecimal[] areaId;
	private Date startDate;
	private Date endDate;
	private BigDecimal[] operationId;
	
	public QualityPublicationFilter() {
		super();
		this.areaId = null;
		this.startDate = null;
		this.endDate = null;
		this.operationId = null;
	}

	public BigDecimal[] getAreaId() {
		return areaId;
	}

	public void setAreaId(BigDecimal[] areaId) {
		this.areaId = areaId;
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

	public BigDecimal[] getOperationId() {
		return operationId;
	}

	public void setOperationId(BigDecimal[] operationId) {
		this.operationId = operationId;
	}

	@Override
	public String toString() {
		return "QualityPublicationFilter [areaId=" + Arrays.toString(areaId) + ", startDate=" + startDate + ", endDate="
				+ endDate + ", operationId=" + Arrays.toString(operationId) + "]";
	}

}
