package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ValidateShipperReviewBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7609940340820710742L;
	
	private List<BigDecimal> allocationReviewIds;
	private String userName;
	private String languageCode;
	private Integer errorCode;
	private String warning;
	private String errorDesc;
	private BigDecimal systemId;
    
	public ValidateShipperReviewBean() {
		this.allocationReviewIds = new ArrayList<BigDecimal>();
		this.userName = null;
		this.languageCode = null;
		this.errorCode = null;
		this.warning = null;
		this.errorDesc = null;
	}

	public List<BigDecimal> getAllocationReviewIds() {
		return allocationReviewIds;
	}

	public void setAllocationReviewIds(List<BigDecimal> allocationReviewIds) {
		this.allocationReviewIds = allocationReviewIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Override
	public String toString() {
		return "ValidateShipperReviewBean [allocationReviewIds=" + allocationReviewIds + ", userName=" + userName
				+ ", languageCode=" + languageCode + ", errorCode=" + errorCode + ", warning=" + warning
				+ ", errorDesc=" + errorDesc + "]";
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}
	
}
