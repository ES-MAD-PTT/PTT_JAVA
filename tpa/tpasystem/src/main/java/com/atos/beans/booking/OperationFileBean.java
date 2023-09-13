package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;


public class OperationFileBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7817477922091486386L;
	
	private BigDecimal idnOperationFile;
	private BigDecimal idnOperationCategory;
	private BigDecimal idnOperationTerm;
	private String fileName;
	private Date versionDate;
	private byte[] binaryData;
	private String xmlData;


	public OperationFileBean() {
		super();
		this.idnOperationFile = null;
		this.idnOperationCategory = null;
		this.idnOperationTerm = null;
		this.fileName = null;
		this.versionDate = null;
		this.binaryData = null;
		this.xmlData = null;
	}

	public BigDecimal getIdnOperationFile() {
		return idnOperationFile;
	}

	public void setIdnOperationFile(BigDecimal idnOperationFile) {
		this.idnOperationFile = idnOperationFile;
	}

	public BigDecimal getIdnOperationCategory() {
		return idnOperationCategory;
	}

	public void setIdnOperationCategory(BigDecimal idnOperationCategory) {
		this.idnOperationCategory = idnOperationCategory;
	}

	public BigDecimal getIdnOperationTerm() {
		return idnOperationTerm;
	}

	public void setIdnOperationTerm(BigDecimal idnOperationTerm) {
		this.idnOperationTerm = idnOperationTerm;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
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
		
}
