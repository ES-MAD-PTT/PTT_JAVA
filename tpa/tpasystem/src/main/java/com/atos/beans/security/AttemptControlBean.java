package com.atos.beans.security;

import java.io.Serializable;
import java.util.Date;

public class AttemptControlBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9010584766766080218L;

	private Integer userId;
	private String username;
	private Date loginPeriodStartTimestamp;
	private Date lastLoginAttemptTimestamp;
	private Integer loginAttemptCount;
	
	
	public AttemptControlBean() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginPeriodStartTimestamp() {
		return loginPeriodStartTimestamp;
	}

	public void setLoginPeriodStartTimestamp(Date loginPeriodStartTimestamp) {
		this.loginPeriodStartTimestamp = loginPeriodStartTimestamp;
	}

	public Date getLastLoginAttemptTimestamp() {
		return lastLoginAttemptTimestamp;
	}

	public void setLastLoginAttemptTimestamp(Date lastLoginAttemptTimestamp) {
		this.lastLoginAttemptTimestamp = lastLoginAttemptTimestamp;
	}

	public Integer getLoginAttemptCount() {
		return loginAttemptCount;
	}

	public void setLoginAttemptCount(Integer loginAttemptCount) {
		this.loginAttemptCount = loginAttemptCount;
	}

	@Override
	public String toString() {
		return "AttemptControlBean [userId=" + userId + ", username=" + username + ", loginPeriodStartTimestamp="
				+ loginPeriodStartTimestamp + ", lastLoginAttemptTimestamp=" + lastLoginAttemptTimestamp
				+ ", loginAttemptCount=" + loginAttemptCount + "]";
	}

}
