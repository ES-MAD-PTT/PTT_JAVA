package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class QualityPubShipperFilter implements Serializable {
	
	private static final long serialVersionUID = 7576068653024948905L;
	
	private BigDecimal[] areaId;
	private Date startDate;
	private Date endDate;
	
	public QualityPubShipperFilter() {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QualityPubShipperFilter [areaId=");
		builder.append(Arrays.toString(areaId));
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}


}
