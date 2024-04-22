package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.primefaces.model.StreamedContent;

public class NewConnectionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6054978220555542408L;

	private BigDecimal capacityRequestId;
	private Date submittedTimestamp;
	private BigDecimal shipperId;
	private String shipperCode;
	private String shipperName;
	private String shortName;
	private BigDecimal operationFileId;
	private String xlsFileName;
	private StreamedContent xlsFile;
	private StreamedContent pdfFile;

	public NewConnectionBean() {
		super();
		this.capacityRequestId = null;
		this.submittedTimestamp = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.shipperName = null;
		this.shortName = null;
		this.operationFileId = null;
		this.xlsFileName = null;
		this.xlsFile = null;
		this.pdfFile = null;		
	}

	public BigDecimal getCapacityRequestId() {
		return capacityRequestId;
	}

	public void setCapacityRequestId(BigDecimal capacityRequestId) {
		this.capacityRequestId = capacityRequestId;
	}
	
	public Date getSubmittedTimestamp() {
		return submittedTimestamp;
	}

	public void setSubmittedTimestamp(Date submittedTimestamp) {
		this.submittedTimestamp = submittedTimestamp;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}
	
	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public BigDecimal getOperationFileId() {
		return operationFileId;
	}

	public void setOperationFileId(BigDecimal operationFileId) {
		this.operationFileId = operationFileId;
	}

	public String getXlsFileName() {
		return xlsFileName;
	}

	public void setXlsFileName(String xlsFileName) {
		this.xlsFileName = xlsFileName;
	}

	public StreamedContent getXlsFile() {
		return xlsFile;
	}

	public void setXlsFile(StreamedContent xlsFile) {
		this.xlsFile = xlsFile;
	}
	
	public StreamedContent getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(StreamedContent pdfFile) {
		this.pdfFile = pdfFile;
	}

	@Override
	public String toString() {
		return "NewConnectionBean [capacityRequestId=" + capacityRequestId + ", submittedTimestamp="
				+ submittedTimestamp + ", shipperId=" + shipperId + ", shipperCode=" + shipperCode + ", shipperName="
				+ shipperName + ", shortName=" + shortName + ", operationFileId=" + operationFileId + ", xlsFileName="
				+ xlsFileName + ", xlsFile=" + xlsFile + ", pdfFile=" + pdfFile + "]";
	}
	
	
}
