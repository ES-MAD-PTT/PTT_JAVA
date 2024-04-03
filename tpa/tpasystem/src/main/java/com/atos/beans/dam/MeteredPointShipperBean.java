package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class MeteredPointShipperBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idnMeterdPointShipper;
	private BigDecimal idnGroup;
	private String group;
	private Date startDate;
	private Date endDate;
	private BigDecimal idnMeteringPoint;
	private String meteringPoint;
	private BigDecimal idnCustomerType;
	private String customerType;
	private BigDecimal idnShipper;
	private String shipper;

	private BigDecimal compositeKey;
	private BigDecimal idnSystem;
	private String userName;
	
	
	public MeteredPointShipperBean() {}
	
	public MeteredPointShipperBean(BigDecimal idnMeterdPointShipper, String shipper, Date startDate, Date endDate, String meteringPoint,
			String customerType, String group) {
		this.idnMeterdPointShipper = idnMeterdPointShipper;
		this.shipper = shipper;
		this.startDate = startDate;
		this.endDate = endDate;
		this.meteringPoint = meteringPoint;
		this.customerType = customerType;
		this.group = group;
	}
	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(BigDecimal compositeKey) {
		this.compositeKey = compositeKey;
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

	public BigDecimal getIdnMeterdPointShipper() {
		return idnMeterdPointShipper;
	}
	public void setIdnMeterdPointShipper(BigDecimal idnMeterdPointShipper) {
		this.idnMeterdPointShipper = idnMeterdPointShipper;
	}
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
	public BigDecimal getIdnMeteringPoint() {
		return idnMeteringPoint;
	}
	public void setIdnMeteringPoint(BigDecimal idnMeteringPoint) {
		this.idnMeteringPoint = idnMeteringPoint;
	}
	public String getMeteringPoint() {
		return meteringPoint;
	}
	public void setMeteringPoint(String meteringPoint) {
		this.meteringPoint = meteringPoint;
	}
	public BigDecimal getIdnCustomerType() {
		return idnCustomerType;
	}
	public void setIdnCustomerType(BigDecimal idnCustomerType) {
		this.idnCustomerType = idnCustomerType;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public BigDecimal getIdnGroup() {
		return idnGroup;
	}
	public void setIdnGroup(BigDecimal idnGroup) {
		this.idnGroup = idnGroup;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	@Override
	public String toString() {
		return "MeteredPointShipperBean [idnShipper=" + idnShipper + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", idnMeteringPoint=" + idnMeteringPoint + ", meteringPoint=" + meteringPoint + ", idnCustomerType="
				+ idnCustomerType + ", customerType=" + customerType + ", idnGroup=" + idnGroup + ", group=" + group
				+ "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(customerType, endDate, group, idnCustomerType, idnGroup, idnMeteringPoint, idnShipper,
				meteringPoint, startDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeteredPointShipperBean other = (MeteredPointShipperBean) obj;
		return Objects.equals(customerType, other.customerType) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(group, other.group) && Objects.equals(idnCustomerType, other.idnCustomerType)
				&& Objects.equals(idnGroup, other.idnGroup) && Objects.equals(idnMeteringPoint, other.idnMeteringPoint)
				&& Objects.equals(idnShipper, other.idnShipper) && Objects.equals(meteringPoint, other.meteringPoint)
				&& Objects.equals(startDate, other.startDate);
	}
	
	
}
