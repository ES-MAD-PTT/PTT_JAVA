package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShipperPointBean implements Serializable {

	private static final long serialVersionUID = 7877951917166903403L;

	private BigDecimal userGroupId;
	private BigDecimal sysPointId;
	private Integer version;
	private String sysPointCode;
	private Date startDate;
	// Si endDate es distinto de null, el punto esta caducado. No esta asociado al shipper.
	private Date endDate;

	public BigDecimal getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(BigDecimal userGroupId) {
		this.userGroupId = userGroupId;
	}
	public BigDecimal getSysPointId() {
		return sysPointId;
	}
	public void setSysPointId(BigDecimal sysPointId) {
		this.sysPointId = sysPointId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getSysPointCode() {
		return sysPointCode;
	}
	public void setSysPointCode(String sysPointCode) {
		this.sysPointCode = sysPointCode;
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
		return "ShipperPointBean [userGroupId=" + userGroupId + ", sysPointId=" + sysPointId + ", version=" + version
				+ ", sysPointCode=" + sysPointCode + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
