package com.atos.beans.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;

public class ValidateForecastingXmlBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3314328722121315332L;
	
	private BigDecimal codOperationFile;
	private String parType;
	private String user;
	private String language;
	private BigDecimal idnSystem;
	
	private BigDecimal idn_forecasting;
	private String errorDesc;
	private int valid;
	
	public ValidateForecastingXmlBean() {
		super();
		this.codOperationFile = null;
		this.parType = null;
		this.user = null;
		this.language = null;
		this.idn_forecasting = null;
		this.errorDesc = null;
	
	}

	
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public BigDecimal getCodOperationFile() {
		return codOperationFile;
	}
	public void setCodOperationFile(BigDecimal codOperationFile) {
		this.codOperationFile = codOperationFile;
	}
	public String getParType() {
		return parType;
	}
	public void setParType(String parType) {
		this.parType = parType;
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
	
	@Override
	public String toString() {
		return "ValidateForecastingXmlBean [valid=" + valid + ",codOperationFile=" + codOperationFile + ", parType=" + parType
				+ ",user="+ user + ",language=" + language + ", errorDesc=" + errorDesc + "]";
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}


	public BigDecimal getIdn_forecasting() {
		return idn_forecasting;
	}


	public void setIdn_forecasting(BigDecimal idn_forecasting) {
		this.idn_forecasting = idn_forecasting;
	}
	
	
}
