package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculateAllocationBalanceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5949992103753661660L;

	private String allocationTypeCode;		//	"COMMERCIAL"
	private String balanceClosingTypeCode;	//	"DEFINITIVE"
	private String updateBalance; 			// "Y"/"N"
	private String userName;
	private String language;
	private Integer errorCode;
	private String errorDesc;
	private BigDecimal idnSystem;
	
	public CalculateAllocationBalanceBean() {
		this.allocationTypeCode = null;
		this.balanceClosingTypeCode = null;
		this.updateBalance = null;
		this.userName = null;
		this.language = null;
		this.errorCode = null;
		this.errorDesc = null;
	}

	public String getAllocationTypeCode() {
		return allocationTypeCode;
	}

	public void setAllocationTypeCode(String allocationTypeCode) {
		this.allocationTypeCode = allocationTypeCode;
	}

	public String getBalanceClosingTypeCode() {
		return balanceClosingTypeCode;
	}

	public void setBalanceClosingTypeCode(String balanceClosingTypeCode) {
		this.balanceClosingTypeCode = balanceClosingTypeCode;
	}

	public String getUpdateBalance() {
		return updateBalance;
	}

	public void setUpdateBalance(String updateBalance) {
		this.updateBalance = updateBalance;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Override
	public String toString() {
		return "AllocationBalanceCalculateBean [allocationTypeCode=" + allocationTypeCode + ", balanceClosingTypeCode="
				+ balanceClosingTypeCode + ", updateBalance=" + updateBalance + ", userName=" + userName + ", language="
				+ language + ", errorCode=" + errorCode + ", errorDesc=" + errorDesc + "]";
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}
	
}
