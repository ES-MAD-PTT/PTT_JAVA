package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccumulatedImbalanceAllocationBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9070473207635992488L;

	private Date gasDay;
	private BigDecimal idnShipper;
	private String shipperCode;
	private BigDecimal idnZone;
	private String zoneCode;
	private BigDecimal accImbalanceMonth;
	private BigDecimal accImbalanceMonthCorrection;
	private String isEditable;
	private BigDecimal idn_active_system;
	private String user;
	private String language;
	private String errorDesc;
	private Integer errorCode;
	private String operator_comments;
	private BigDecimal idnNominationConcept;
	
	private BigDecimal allocatedAdjust;
	private BigDecimal pendingAdjust;
	private BigDecimal finalAccImbalanceStock;

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public BigDecimal getIdnShipper() {
		return idnShipper;
	}

	public void setIdnShipper(BigDecimal idnShipper) {
		this.idnShipper = idnShipper;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public BigDecimal getIdnZone() {
		return idnZone;
	}

	public void setIdnZone(BigDecimal idnZone) {
		this.idnZone = idnZone;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public BigDecimal getAccImbalanceMonth() {
		return accImbalanceMonth;
	}

	public void setAccImbalanceMonth(BigDecimal accImbalanceMonth) {
		this.accImbalanceMonth = accImbalanceMonth;
	}

	public BigDecimal getAccImbalanceMonthCorrection() {
		return accImbalanceMonthCorrection;
	}

	public void setAccImbalanceMonthCorrection(BigDecimal accImbalanceMonthCorrection) {
		this.accImbalanceMonthCorrection = accImbalanceMonthCorrection;
	}

	public String getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(String isEditable) {
		this.isEditable = isEditable;
	}

	public BigDecimal getIdn_active_system() {
		return idn_active_system;
	}

	public void setIdn_active_system(BigDecimal idn_active_system) {
		this.idn_active_system = idn_active_system;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getOperator_comments() {
		return operator_comments;
	}

	public void setOperator_comments(String comments) {
		this.operator_comments = comments;
	}
	
	public BigDecimal getIdnNominationConcept() {
		return idnNominationConcept;
	}

	public void setIdnNominationConcept(BigDecimal idnNominationConcept) {
		this.idnNominationConcept = idnNominationConcept;
	}

	public BigDecimal getAllocatedAdjust() {
		return allocatedAdjust;
	}

	public void setAllocatedAdjust(BigDecimal allocatedAdjust) {
		this.allocatedAdjust = allocatedAdjust;
	}

	public BigDecimal getPendingAdjust() {
		return pendingAdjust;
	}

	public void setPendingAdjust(BigDecimal pendingAdjust) {
		this.pendingAdjust = pendingAdjust;
	}

	public BigDecimal getFinalAccImbalanceStock() {
		return finalAccImbalanceStock;
	}

	public void setFinalAccImbalanceStock(BigDecimal finalAccImbalanceStock) {
		this.finalAccImbalanceStock = finalAccImbalanceStock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accImbalanceMonth == null) ? 0 : accImbalanceMonth.hashCode());
		result = prime * result + ((accImbalanceMonthCorrection == null) ? 0 : accImbalanceMonthCorrection.hashCode());
		result = prime * result + ((allocatedAdjust == null) ? 0 : allocatedAdjust.hashCode());
		result = prime * result + ((errorCode == null) ? 0 : errorCode.hashCode());
		result = prime * result + ((errorDesc == null) ? 0 : errorDesc.hashCode());
		result = prime * result + ((finalAccImbalanceStock == null) ? 0 : finalAccImbalanceStock.hashCode());
		result = prime * result + ((gasDay == null) ? 0 : gasDay.hashCode());
		result = prime * result + ((idnNominationConcept == null) ? 0 : idnNominationConcept.hashCode());
		result = prime * result + ((idnShipper == null) ? 0 : idnShipper.hashCode());
		result = prime * result + ((idnZone == null) ? 0 : idnZone.hashCode());
		result = prime * result + ((idn_active_system == null) ? 0 : idn_active_system.hashCode());
		result = prime * result + ((isEditable == null) ? 0 : isEditable.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((operator_comments == null) ? 0 : operator_comments.hashCode());
		result = prime * result + ((pendingAdjust == null) ? 0 : pendingAdjust.hashCode());
		result = prime * result + ((shipperCode == null) ? 0 : shipperCode.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((zoneCode == null) ? 0 : zoneCode.hashCode());
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
		AccumulatedImbalanceAllocationBean other = (AccumulatedImbalanceAllocationBean) obj;
		if (accImbalanceMonth == null) {
			if (other.accImbalanceMonth != null)
				return false;
		} else if (!accImbalanceMonth.equals(other.accImbalanceMonth))
			return false;
		if (accImbalanceMonthCorrection == null) {
			if (other.accImbalanceMonthCorrection != null)
				return false;
		} else if (!accImbalanceMonthCorrection.equals(other.accImbalanceMonthCorrection))
			return false;
		if (allocatedAdjust == null) {
			if (other.allocatedAdjust != null)
				return false;
		} else if (!allocatedAdjust.equals(other.allocatedAdjust))
			return false;
		if (errorCode == null) {
			if (other.errorCode != null)
				return false;
		} else if (!errorCode.equals(other.errorCode))
			return false;
		if (errorDesc == null) {
			if (other.errorDesc != null)
				return false;
		} else if (!errorDesc.equals(other.errorDesc))
			return false;
		if (finalAccImbalanceStock == null) {
			if (other.finalAccImbalanceStock != null)
				return false;
		} else if (!finalAccImbalanceStock.equals(other.finalAccImbalanceStock))
			return false;
		if (gasDay == null) {
			if (other.gasDay != null)
				return false;
		} else if (!gasDay.equals(other.gasDay))
			return false;
		if (idnNominationConcept == null) {
			if (other.idnNominationConcept != null)
				return false;
		} else if (!idnNominationConcept.equals(other.idnNominationConcept))
			return false;
		if (idnShipper == null) {
			if (other.idnShipper != null)
				return false;
		} else if (!idnShipper.equals(other.idnShipper))
			return false;
		if (idnZone == null) {
			if (other.idnZone != null)
				return false;
		} else if (!idnZone.equals(other.idnZone))
			return false;
		if (idn_active_system == null) {
			if (other.idn_active_system != null)
				return false;
		} else if (!idn_active_system.equals(other.idn_active_system))
			return false;
		if (isEditable == null) {
			if (other.isEditable != null)
				return false;
		} else if (!isEditable.equals(other.isEditable))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (operator_comments == null) {
			if (other.operator_comments != null)
				return false;
		} else if (!operator_comments.equals(other.operator_comments))
			return false;
		if (pendingAdjust == null) {
			if (other.pendingAdjust != null)
				return false;
		} else if (!pendingAdjust.equals(other.pendingAdjust))
			return false;
		if (shipperCode == null) {
			if (other.shipperCode != null)
				return false;
		} else if (!shipperCode.equals(other.shipperCode))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (zoneCode == null) {
			if (other.zoneCode != null)
				return false;
		} else if (!zoneCode.equals(other.zoneCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccumulatedImbalanceAllocationBean [gasDay=" + gasDay + ", idnShipper=" + idnShipper + ", shipperCode="
				+ shipperCode + ", idnZone=" + idnZone + ", zoneCode=" + zoneCode + ", accImbalanceMonth="
				+ accImbalanceMonth + ", accImbalanceMonthCorrection=" + accImbalanceMonthCorrection + ", isEditable="
				+ isEditable + ", idn_active_system=" + idn_active_system + ", user=" + user + ", language=" + language
				+ ", errorDesc=" + errorDesc + ", errorCode=" + errorCode + ", operator_comments=" + operator_comments
				+ ", idnNominationConcept=" + idnNominationConcept + ", allocatedAdjust=" + allocatedAdjust
				+ ", pendingAdjust=" + pendingAdjust + ", finalAccImbalanceStock=" + finalAccImbalanceStock + "]";
	}

}
