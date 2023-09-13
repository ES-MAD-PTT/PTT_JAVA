package com.atos.beans.metering;

import java.io.Serializable;
import java.math.BigDecimal;

public class MeteringProcBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7807251941793291042L;

	private BigDecimal meteringInputId;
	private String userId;
	private String languageCode;
	private String warningsFlag;	// 'Y' en caso de que se hayan incluido registros en la tabla de warnings, y 'N' en caso contrario.
	private Integer totalMeasurements;
	private Integer savedMeasurements;
	private BigDecimal errorCode;
	private String errorDesc;
	
	public MeteringProcBean() {
		this.meteringInputId = null;
		this.userId = null;
		this.languageCode = null;
		this.warningsFlag = null;
		this.totalMeasurements = null;
		this.savedMeasurements = null;
		this.errorCode = null;
		this.errorDesc = null;
	}

	public BigDecimal getMeteringInputId() {
		return meteringInputId;
	}

	public void setMeteringInputId(BigDecimal meteringInputId) {
		this.meteringInputId = meteringInputId;
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

	public String getWarningsFlag() {
		return warningsFlag;
	}

	public void setWarningsFlag(String warningsFlag) {
		this.warningsFlag = warningsFlag;
	}

	public Integer getTotalMeasurements() {
		return totalMeasurements;
	}

	public void setTotalMeasurements(Integer totalMeasurements) {
		this.totalMeasurements = totalMeasurements;
	}

	public Integer getSavedMeasurements() {
		return savedMeasurements;
	}

	public void setSavedMeasurements(Integer savedMeasurements) {
		this.savedMeasurements = savedMeasurements;
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

	@Override
	public String toString() {
		return "MeteringProcBean [meteringInputId=" + meteringInputId + ", userId=" + userId + ", languageCode="
				+ languageCode + ", warningsFlag=" + warningsFlag + ", totalMeasurements=" + totalMeasurements
				+ ", savedMeasurements=" + savedMeasurements + ", errorCode=" + errorCode + ", errorDesc=" + errorDesc
				+ "]";
	}	
}
