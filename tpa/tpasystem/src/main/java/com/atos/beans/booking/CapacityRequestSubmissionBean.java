package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

public class CapacityRequestSubmissionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2835475399550216420L;

	private String shipperCode;
	private BigDecimal systemId;
	private String termCode;
	private BigDecimal operationFileId;
	private String userId;
	private String languageCode;
	private String warnings;
	private BigDecimal errorCode;
	private String errorDesc;
	private BigDecimal idn_contract_request;
	
	public CapacityRequestSubmissionBean() {
		super();
		this.shipperCode = null;
		this.systemId = null;
		this.termCode = null;
		this.operationFileId = null;
		this.userId = null;
		this.languageCode = null;
		this.warnings = null;
		this.errorCode = null;		
		this.errorDesc = null;
		this.idn_contract_request = null;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public BigDecimal getOperationFileId() {
		return operationFileId;
	}

	public void setOperationFileId(BigDecimal operationFileId) {
		this.operationFileId = operationFileId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public BigDecimal getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(BigDecimal errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public BigDecimal getIdn_contract_request() {
		return idn_contract_request;
	}

	public void setIdn_contract_request(BigDecimal idn_contract_request) {
		this.idn_contract_request = idn_contract_request;
	}

	@Override
	public String toString() {
		return "CapacityRequestSubmissionBean [shipperCode=" + shipperCode + ", systemId=" + systemId + ", termCode="
				+ termCode + ", operationFileId=" + operationFileId + ", userId=" + userId + ", languageCode="
				+ languageCode + ", warnings=" + warnings + ", errorCode=" + errorCode + ", errorDesc=" + errorDesc
				+ ", idn_contract_request=" + idn_contract_request + "]";
	}
	
	
}
