package com.atos.beans;

import java.io.Serializable;
import java.util.Date;

public class ElementIdBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6261176078766663195L;
	private String generationCode;
	private Date date;
	private String user;
	private String language;
	private String id;
	private Integer integerExit;
	private String errorDesc;

	public ElementIdBean() {
		this.generationCode = null;
		this.date = null;
		this.user = null;
		this.language = null;
		this.id = null;
		this.integerExit = null;
		this.errorDesc = null;
	}

	public String getGenerationCode() {
		return generationCode;
	}

	public void setGenerationCode(String generationCode) {
		this.generationCode = generationCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIntegerExit() {
		return integerExit;
	}

	public void setIntegerExit(Integer integerExit) {
		this.integerExit = integerExit;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	@Override
	public String toString() {
		return "ElementIdBean [generationCode=" + generationCode + ", date=" + date + ", user=" + user
				+ ", language=" + language + ", id=" + id + ", integerExit=" + integerExit + ", errorDesc=" + errorDesc
				+ "]";
	}
}
