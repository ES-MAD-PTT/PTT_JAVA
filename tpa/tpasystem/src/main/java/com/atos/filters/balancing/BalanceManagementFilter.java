package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BalanceManagementFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8806926991068788393L;

	private String allocTypeCode;
	private String closingTypeCode;
	private Date closingDate;
	private String user;
	private String lang;
	private Integer errorCode;
	private String errorDesc;
	private String errorDetail;
	private BigDecimal idnSystem;
	private String codSystem;
	
	public BalanceManagementFilter(){
		this.allocTypeCode = null;
		this.closingTypeCode = null;
		this.closingDate = null;
		this.user = null;
		this.lang = null;
		this.errorCode = null;
		this.errorDesc = null;
		this.errorDetail = null;
	}

	public String getAllocTypeCode() {
		return allocTypeCode;
	}

	public void setAllocTypeCode(String allocTypeCode) {
		this.allocTypeCode = allocTypeCode;
	}

	public String getClosingTypeCode() {
		return closingTypeCode;
	}

	public void setClosingTypeCode(String closingTypeCode) {
		this.closingTypeCode = closingTypeCode;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	@Override
	public String toString() {
		return "BalanceManagementFilter [allocTypeCode=" + allocTypeCode + ", closingTypeCode=" + closingTypeCode
				+ ", closingDate=" + closingDate + ", user=" + user + ", lang=" + lang + ", errorCode=" + errorCode
				+ ", errorDesc=" + errorDesc + ", errorDetail=" + errorDetail + "]";
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

	public String getCodSystem() {
		return codSystem;
	}

	public void setCodSystem(String codSystem) {
		this.codSystem = codSystem;
	}
	
}
