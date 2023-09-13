package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class WebServiceInputBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5386591285052560437L;
	
	private BigDecimal webserviceInputId;
	private String webservice;
	private Date inputDate;
	private String filename;
	private byte[] binaryData;
	private String xmlData;
	private String status;
	private BigDecimal webserviceLogId;
	
	
	public WebServiceInputBean(String _username) {
		super(_username);
		this.webserviceInputId = null;
		this.webservice = null;
		this.inputDate = null;
		this.filename = null;
		this.binaryData = null;
		this.xmlData = null;
		this.status = null;
		this.webserviceLogId = null;
	}
	
	public BigDecimal getWebserviceInputId() {
		return webserviceInputId;
	}
	public void setWebserviceInputId(BigDecimal webserviceInputId) {
		this.webserviceInputId = webserviceInputId;
	}
	public String getWebservice() {
		return webservice;
	}
	public void setWebservice(String webservice) {
		this.webservice = webservice;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
		StringBuilder builder = new StringBuilder();
		builder.append("WebServiceInput [webserviceInputId=");
		builder.append(webserviceInputId);
		builder.append(", webservice=");
		builder.append(webservice);
		builder.append(", inputDate=");
		builder.append(inputDate);
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", binaryData=");
		builder.append(Arrays.toString(binaryData));
		builder.append(", xmlData=");
		builder.append(xmlData);
		builder.append(", status=");
		builder.append(status);
		builder.append(", webserviceLogId=");
		builder.append(webserviceLogId);
		builder.append("]");
		return builder.toString();
	}

	
}
