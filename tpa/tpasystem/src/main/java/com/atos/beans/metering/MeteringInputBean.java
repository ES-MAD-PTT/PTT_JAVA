package com.atos.beans.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class MeteringInputBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -438591936813390482L;

	private BigDecimal meteringInputId;
	private String inputCode;
	private Date inputDate;
	private String fileName;
	private byte[] binaryData;
	private String xmlData;
	private String status;
	private BigDecimal webserviceLogId;
	
	public MeteringInputBean(String _username) {
		super(_username);
		this.meteringInputId = null;
		this.inputCode = null;
		this.inputDate = null;
		this.fileName = null;
		this.binaryData = null;
		this.xmlData = null;
		this.status = null;
		this.webserviceLogId = null;
	}

	public BigDecimal getMeteringInputId() {
		return meteringInputId;
	}

	public void setMeteringInputId(BigDecimal meteringInputId) {
		this.meteringInputId = meteringInputId;
	}

	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}

	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getWebserviceLogId() {
		return webserviceLogId;
	}

	public void setWebserviceLogId(BigDecimal webserviceLogId) {
		this.webserviceLogId = webserviceLogId;
	}

	@Override
	public String toString() {
		return "MeteringInputBean [meteringInputId=" + meteringInputId + ", inputCode=" + inputCode + ", inputDate="
				+ inputDate + ", fileName=" + fileName + ", binaryData=" + Arrays.toString(binaryData) + ", xmlData="
				+ xmlData + ", status=" + status + ", webserviceLogId=" + webserviceLogId + "]";
	}

	
}
