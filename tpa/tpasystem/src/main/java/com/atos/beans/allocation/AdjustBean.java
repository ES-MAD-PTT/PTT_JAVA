package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AdjustBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7328658366394817116L;

	private Date gasDay;
	private Date monthYear;
	private Date startDate;
	private Date endDate;
	private Date origContractStartDate;
	private Date origContractEndDate;
	private String operatorComments;
	private BigDecimal adjustValue;
	private BigDecimal destZone;
	private BigDecimal origZone;
	private BigDecimal origContractId;
	private BigDecimal destContractId;
	private BigDecimal destShipperId;
	private BigDecimal origShipperId;
	private String user;
	private String language;
	private String adjustCode;
	private String errorDesc;
	private Integer errorCode;
	private BigDecimal idnSystem;

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public String getOperatorComments() {
		return operatorComments;
	}

	public void setOperatorComments(String operatorComments) {
		this.operatorComments = operatorComments;
	}

	public BigDecimal getAdjustValue() {
		return adjustValue;
	}

	public void setAdjustValue(BigDecimal adjustValue) {
		this.adjustValue = adjustValue;
	}

	public BigDecimal getDestZone() {
		return destZone;
	}

	public void setDestZone(BigDecimal destZone) {
		this.destZone = destZone;
	}

	public BigDecimal getOrigZone() {
		return origZone;
	}

	public void setOrigZone(BigDecimal origZone) {
		this.origZone = origZone;
	}

	public BigDecimal getDestContractId() {
		return destContractId;
	}

	public void setDestContractId(BigDecimal destContractId) {
		this.destContractId = destContractId;
	}

	public BigDecimal getDestShipperId() {
		return destShipperId;
	}

	public void setDestShipperId(BigDecimal destShipperId) {
		this.destShipperId = destShipperId;
	}

	public Date getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(Date monthYear) {
		this.monthYear = monthYear;
	}

	public BigDecimal getOrigContractId() {
		return origContractId;
	}

	public void setOrigContractId(BigDecimal origContractId) {
		this.origContractId = origContractId;
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

	public BigDecimal getOrigShipperId() {
		return origShipperId;
	}

	public void setOrigShipperId(BigDecimal origShipperId) {
		this.origShipperId = origShipperId;
	}

	public Date getOrigContractStartDate() {
		return origContractStartDate;
	}

	public void setOrigContractStartDate(Date origContractStartDate) {
		this.origContractStartDate = origContractStartDate;
	}

	public Date getOrigContractEndDate() {
		return origContractEndDate;
	}

	public void setOrigContractEndDate(Date origContractEndDate) {
		this.origContractEndDate = origContractEndDate;
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

	public String getAdjustCode() {
		return adjustCode;
	}

	public void setAdjustCode(String adjustCode) {
		this.adjustCode = adjustCode;
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

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

}
