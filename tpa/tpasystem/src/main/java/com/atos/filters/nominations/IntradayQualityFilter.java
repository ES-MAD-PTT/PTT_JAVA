package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class IntradayQualityFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1083577828909332260L;
	private BigDecimal[] areaId;
	private Date startDate;
	private Date endDate;
	
	public IntradayQualityFilter() {
		super();
		this.areaId = null;
		this.startDate = null;
		this.endDate = null;
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

}
