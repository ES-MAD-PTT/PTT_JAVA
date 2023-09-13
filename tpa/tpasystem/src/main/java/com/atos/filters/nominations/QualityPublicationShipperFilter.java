package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class QualityPublicationShipperFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1083577828909332260L;
	private BigDecimal[] areaId;
	private Date startDate;
	private Date endDate;
	private BigDecimal shipper;
	
	public QualityPublicationShipperFilter() {
		super();
		this.areaId = null;
		this.startDate = null;
		this.endDate = null;
		this.shipper = null;
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

	public BigDecimal getShipper() {
		return shipper;
	}

	public void setShipper(BigDecimal shipper) {
		this.shipper = shipper;
	}

	@Override
	public String toString() {
		return "QualityPublicationShipperFilter [areaId=" + Arrays.toString(areaId) + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", shipper=" + shipper + "]";
	}
}
