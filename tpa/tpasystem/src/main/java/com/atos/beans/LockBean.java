package com.atos.beans;

import java.io.Serializable;

public class LockBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2278056123254490822L;

	private String processCode;
	private String userId;
	private Integer integerExit;
	
	public LockBean() {
		this.processCode = null;
		this.userId = null;
		this.integerExit = null;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIntegerExit() {
		return integerExit;
	}

	public void setIntegerExit(Integer integerExit) {
		this.integerExit = integerExit;
	}

	@Override
	public String toString() {
		return "LockBean [processCode=" + processCode + ", userId=" + userId + ", integerExit=" + integerExit + "]";
	}

}
