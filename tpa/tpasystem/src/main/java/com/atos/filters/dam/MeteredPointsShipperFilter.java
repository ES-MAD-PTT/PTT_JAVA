package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class MeteredPointsShipperFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5384739501695766356L;

	private BigDecimal idnShipper;
	private Date startDate;
	private Date endDate;
	private BigDecimal idnMeteredPoint;
	private BigDecimal idnCustomerType;
	private BigDecimal idnGroup;
	
	
	
	public BigDecimal getIdnShipper() {
		return idnShipper;
	}
	public void setIdnShipper(BigDecimal idnShipper) {
		this.idnShipper = idnShipper;
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
	public BigDecimal getIdnMeteredPoint() {
		return idnMeteredPoint;
	}
	public void setIdnMeteredPoint(BigDecimal idnMeteredPoint) {
		this.idnMeteredPoint = idnMeteredPoint;
	}
	public BigDecimal getIdnCustomerType() {
		return idnCustomerType;
	}
	public void setIdnCustomerType(BigDecimal idnCustomerType) {
		this.idnCustomerType = idnCustomerType;
	}
	public BigDecimal getIdnGroup() {
		return idnGroup;
	}
	public void setIdnGroup(BigDecimal idnGroup) {
		this.idnGroup = idnGroup;
	}
	
	
	
	@Override
	public String toString() {
		return "MeteredPointsShipperFilter [idnShipper=" + idnShipper + ", startDate=" + startDate + ", endDate="
				+ endDate + ", idnMeteredPoint=" + idnMeteredPoint + ", idnCustomerType=" + idnCustomerType
				+ ", idnGroup=" + idnGroup + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(endDate, idnCustomerType, idnGroup, idnMeteredPoint, idnShipper, startDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeteredPointsShipperFilter other = (MeteredPointsShipperFilter) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(idnCustomerType, other.idnCustomerType)
				&& Objects.equals(idnGroup, other.idnGroup) && Objects.equals(idnMeteredPoint, other.idnMeteredPoint)
				&& Objects.equals(idnShipper, other.idnShipper) && Objects.equals(startDate, other.startDate);
	}
	
}
