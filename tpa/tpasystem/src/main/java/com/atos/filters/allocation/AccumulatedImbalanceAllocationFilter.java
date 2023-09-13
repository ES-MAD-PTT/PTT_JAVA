package com.atos.filters.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccumulatedImbalanceAllocationFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8390777904195954092L;
	
	private Date startDate;
	private Date endDate;
	private BigDecimal shipperId;
	private BigDecimal zoneId;
	private boolean isShipper;
	private BigDecimal idn_active_system;
	
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
	public BigDecimal getShipperId() {
		return shipperId;
	}
	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}
	public BigDecimal getZoneId() {
		return zoneId;
	}
	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}
	public BigDecimal getIdn_active_system() {
		return idn_active_system;
	}
	public void setIdn_active_system(BigDecimal idn_active_system) {
		this.idn_active_system = idn_active_system;
	}
	public boolean isShipper() {
		return isShipper;
	}

	public void setIsShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((idn_active_system == null) ? 0 : idn_active_system.hashCode());
		result = prime * result + (isShipper ? 1231 : 1237);
		result = prime * result + ((shipperId == null) ? 0 : shipperId.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((zoneId == null) ? 0 : zoneId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccumulatedImbalanceAllocationFilter other = (AccumulatedImbalanceAllocationFilter) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (idn_active_system == null) {
			if (other.idn_active_system != null)
				return false;
		} else if (!idn_active_system.equals(other.idn_active_system))
			return false;
		if (isShipper != other.isShipper)
			return false;
		if (shipperId == null) {
			if (other.shipperId != null)
				return false;
		} else if (!shipperId.equals(other.shipperId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (zoneId == null) {
			if (other.zoneId != null)
				return false;
		} else if (!zoneId.equals(other.zoneId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AdjustmentAllocationFilter [startDate=" + startDate + ", endDate=" + endDate + ", shipperId="
				+ shipperId + ", zoneId=" + zoneId + ", isShipper=" + isShipper + ", idn_active_system="
				+ idn_active_system + "]";
	}

}
