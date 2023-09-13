package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class WebServiceProcBean extends UserAudBean implements Serializable{

	
	private static final long serialVersionUID = -6734659519164993273L;

	private BigDecimal webserviceInputId;
	private String userId;
	private String languageCode;
	private String warningsFlag;	// 'Y' en caso de que se hayan incluido registros en la tabla de warnings, y 'N' en caso contrario.
	private Integer totalMeasurements;
	private Integer savedMeasurements;
	private BigDecimal errorCode;
	private String errorDesc;

	public BigDecimal getWebserviceInputId() {
		return webserviceInputId;
	}
	public void setWebserviceInputId(BigDecimal webserviceInputId) {
		this.webserviceInputId = webserviceInputId;
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
}
