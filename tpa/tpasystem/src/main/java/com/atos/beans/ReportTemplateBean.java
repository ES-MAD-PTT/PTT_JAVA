package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class ReportTemplateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2675305384328789187L;

	private BigDecimal repTemplateId;
	private String tempCode;
	private String tempDesc;
	private byte[] binaryData;
	private String fileName;
	private BigDecimal systemId;
	
	public ReportTemplateBean() {
		this.repTemplateId = null;
		this.tempCode = null;
		this.tempDesc = null;
		this.binaryData = null;
		this.fileName = null;
		this.systemId = null;
	}

	public BigDecimal getRepTemplateId() {
		return repTemplateId;
	}

	public void setRepTemplateId(BigDecimal repTemplateId) {
		this.repTemplateId = repTemplateId;
	}

	public String getTempCode() {
		return tempCode;
	}

	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}

	public String getTempDesc() {
		return tempDesc;
	}

	public void setTempDesc(String tempDesc) {
		this.tempDesc = tempDesc;
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

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "ReportTemplateBean [repTemplateId=" + repTemplateId + ", tempCode=" + tempCode + ", tempDesc="
				+ tempDesc + ", binaryData=" + Arrays.toString(binaryData) + ", fileName=" + fileName + ", systemId="
				+ systemId + "]";
	}

}
