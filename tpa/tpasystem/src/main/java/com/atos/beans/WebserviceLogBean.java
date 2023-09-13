package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WebserviceLogBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378171325996663859L;

	private BigDecimal webserviceLogId;
	private String webserviceName;
	private Date callDate;
	private String callXml;
	private Date responseDate;
	private String responseXml;
	private String status;			// RUNNING, OK, ERROR.
	
	public WebserviceLogBean(String _username) {
		super(_username);
		this.webserviceLogId = null;
		this.webserviceName = null;
		this.callDate = null;
		this.callXml = null;
		this.responseDate = null;
		this.responseXml = null;
		this.status = null;
	}

	public BigDecimal getWebserviceLogId() {
		return webserviceLogId;
	}

	public void setWebserviceLogId(BigDecimal webserviceLogId) {
		this.webserviceLogId = webserviceLogId;
	}

	public String getWebserviceName() {
		return webserviceName;
	}

	public void setWebserviceName(String webserviceName) {
		this.webserviceName = webserviceName;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getCallXml() {
		return callXml;
	}

	public void setCallXml(String callXml) {
		this.callXml = callXml;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WebserviceLogBean [webserviceLogId=" + webserviceLogId + ", webserviceName=" + webserviceName
				+ ", callDate=" + callDate + ", callXml=" + callXml + ", responseDate=" + responseDate
				+ ", responseXml=" + responseXml + ", status=" + status + "]";
	}

	
}
