package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class OpTemplateBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6633513697705779837L;

	private BigDecimal opTemplateId;
	private BigDecimal opCategoryId;
	private String opCategoryCode;
	private BigDecimal opTermId;
	private String opTermCode;
	private String fileType;
	private byte[] binaryData;
	private String fileName;
	private BigDecimal xmlMapId;
	private BigDecimal systemId;
	
	public OpTemplateBean() {
		super();
		this.opTemplateId = null;
		this.opCategoryId = null;
		this.opCategoryCode = null;
		this.opTermId = null;
		this.opTermCode = null;
		this.fileType = null;
		this.binaryData = null;
		this.fileName = null;
		this.xmlMapId = null;
		this.systemId = null;
	}
	
	public BigDecimal getOpTemplateId() {
		return opTemplateId;
	}

	public void setOpTemplateId(BigDecimal opTemplateId) {
		this.opTemplateId = opTemplateId;
	}

	public BigDecimal getOpCategoryId() {
		return opCategoryId;
	}

	public void setOpCategoryId(BigDecimal opCategoryId) {
		this.opCategoryId = opCategoryId;
	}

	public String getOpCategoryCode() {
		return opCategoryCode;
	}

	public void setOpCategoryCode(String opCategoryCode) {
		this.opCategoryCode = opCategoryCode;
	}

	public BigDecimal getOpTermId() {
		return opTermId;
	}

	public void setOpTermId(BigDecimal opTermId) {
		this.opTermId = opTermId;
	}

	public String getOpTermCode() {
		return opTermCode;
	}

	public void setOpTermCode(String opTermCode) {
		this.opTermCode = opTermCode;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getXmlMapId() {
		return xmlMapId;
	}

	public void setXmlMapId(BigDecimal xmlMapId) {
		this.xmlMapId = xmlMapId;
	}

	
	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "OpTemplateBean [opTemplateId=" + opTemplateId + ", opCategoryId=" + opCategoryId + ", opCategoryCode="
				+ opCategoryCode + ", opTermId=" + opTermId + ", opTermCode=" + opTermCode + ", fileType=" + fileType
				+ ", fileName=" + fileName + ", xmlMapId=" + xmlMapId + ", systemId=" + systemId + "]";
	}
	
}
