package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class RenominationIntradayProrateBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = 1923617399438288524L;
	
	private BigDecimal idn_intraday_renom_cab;
	private BigDecimal hour;
	private String user;
	private String language;
	private Integer errorCode;
	private String errorDesc;
	
	
	
	public RenominationIntradayProrateBean() {
		super();
		this.idn_intraday_renom_cab = null;
		this.hour = null;
		this.user = null;
		this.language = null;
		this.errorCode = null;
		this.errorDesc = null;
	}
	
	public RenominationIntradayProrateBean(BigDecimal idn_intraday_renom_cab, BigDecimal hour, String user,
			String language, Integer errorCode, String errorDesc) {
		super();
		this.idn_intraday_renom_cab = idn_intraday_renom_cab;
		this.hour = hour;
		this.user = user;
		this.language = language;
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
	public BigDecimal getIdn_intraday_renom_cab() {
		return idn_intraday_renom_cab;
	}
	public void setIdn_intraday_renom_cab(BigDecimal idn_intraday_renom_cab) {
		this.idn_intraday_renom_cab = idn_intraday_renom_cab;
	}
	public BigDecimal getHour() {
		return hour;
	}
	public void setHour(BigDecimal hour) {
		this.hour = hour;
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

}
