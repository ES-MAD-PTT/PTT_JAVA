package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignedContractTemplateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 841228555134338192L;

	private static final String shortNonFirmContractDesc = "Short term (non firm) contract";
	private static final String shortFirmContractDesc= "Short term (firm) contract";
	private static final String mediumContractDesc= "Medium term contract";
	private static final String longContractDesc= "Long term contract";
		
	private BigDecimal capacityRequestId;
	private String shipperName;
	private String shipperSAPId;
	private String contractCode;
	private String strCommencementDate;
	private String strEndDate;
	private String operationDesc;
	private BigDecimal totalEntry;
	private BigDecimal totalExit;
	private BigDecimal minEntryTemp;
	private BigDecimal maxEntryTemp;
	private BigDecimal minExitTemp;
	private BigDecimal maxExitTemp;
	private Date renewalDeadline;
	private String contractType;
	private List<SignedContractTempPeriodBean> periods;
	
	public SignedContractTemplateBean() {
		this.capacityRequestId = null;
		this.shipperName = null;
		this.shipperSAPId = null;
		this.contractCode = null;
		this.strCommencementDate = null;
		this.strEndDate = null;
		this.operationDesc = null;
		this.totalEntry = null;
		this.totalExit = null;
		this.minEntryTemp = null;
		this.maxEntryTemp = null;
		this.minExitTemp = null;
		this.maxExitTemp = null;
		this.renewalDeadline = null;
		this.contractType = null;
		this.periods = new ArrayList<SignedContractTempPeriodBean>();
	}

	public BigDecimal getCapacityRequestId() {
		return capacityRequestId;
	}

	public void setCapacityRequestId(BigDecimal capacityRequestId) {
		this.capacityRequestId = capacityRequestId;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getShipperSAPId() {
		return shipperSAPId;
	}

	public void setShipperSAPId(String shipperSAPId) {
		this.shipperSAPId = shipperSAPId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getStrCommencementDate() {
		return strCommencementDate;
	}

	public void setStrCommencementDate(String strCommencementDate) {
		this.strCommencementDate = strCommencementDate;
	}

	public String getStrEndDate() {
		return strEndDate;
	}

	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}

	public String getOperationDesc() {
		return operationDesc;
	}

	public void setOperationDesc(String operationDesc) {
		this.operationDesc = operationDesc;
	}

	public BigDecimal getTotalEntry() {
		return totalEntry;
	}

	public void setTotalEntry(BigDecimal totalEntry) {
		this.totalEntry = totalEntry;
	}

	public BigDecimal getTotalExit() {
		return totalExit;
	}

	public void setTotalExit(BigDecimal totalExit) {
		this.totalExit = totalExit;
	}

	public BigDecimal getMinEntryTemp() {
		return minEntryTemp;
	}

	public void setMinEntryTemp(BigDecimal minEntryTemp) {
		this.minEntryTemp = minEntryTemp;
	}

	public BigDecimal getMaxEntryTemp() {
		return maxEntryTemp;
	}

	public void setMaxEntryTemp(BigDecimal maxEntryTemp) {
		this.maxEntryTemp = maxEntryTemp;
	}

	public BigDecimal getMinExitTemp() {
		return minExitTemp;
	}

	public void setMinExitTemp(BigDecimal minExitTemp) {
		this.minExitTemp = minExitTemp;
	}

	public BigDecimal getMaxExitTemp() {
		return maxExitTemp;
	}

	public void setMaxExitTemp(BigDecimal maxExitTemp) {
		this.maxExitTemp = maxExitTemp;
	}

	public Date getRenewalDeadline() {
		return renewalDeadline;
	}

	public void setRenewalDeadline(Date renewalDeadline) {
		this.renewalDeadline = renewalDeadline;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public List<SignedContractTempPeriodBean> getPeriods() {
		return periods;
	}

	public void setPeriods(List<SignedContractTempPeriodBean> periods) {
		this.periods = periods;
	}

	@Override
	public String toString() {
		return "SignedContractTemplateBean [capacityRequestId=" + capacityRequestId + ", shipperName=" + shipperName
				+ ", shipperSAPId=" + shipperSAPId + ", contractCode=" + contractCode + ", strCommencementDate="
				+ strCommencementDate + ", strEndDate=" + strEndDate + ", operationDesc=" + operationDesc
				+ ", totalEntry=" + totalEntry + ", totalExit=" + totalExit + ", minEntryTemp=" + minEntryTemp
				+ ", maxEntryTemp=" + maxEntryTemp + ", minExitTemp=" + minExitTemp + ", maxExitTemp=" + maxExitTemp
				+ ", renewalDeadline=" + renewalDeadline + ", contractType=" + contractType + ", periods=" + periods
				+ "]";
	}
	
	public boolean isFirm() throws Exception {

		if(this.operationDesc==null)
			throw new Exception("Operation description not established.");
		
		return (shortFirmContractDesc.equalsIgnoreCase(this.operationDesc) ||
				mediumContractDesc.equalsIgnoreCase(this.operationDesc) ||
				longContractDesc.equalsIgnoreCase(this.operationDesc));
	}
	
	public boolean isShort() throws Exception {

		if(this.operationDesc==null)
			throw new Exception("Operation description not established.");
		
		return (shortNonFirmContractDesc.equalsIgnoreCase(this.operationDesc) ||
				shortFirmContractDesc.equalsIgnoreCase(this.operationDesc));
	}
}
