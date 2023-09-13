package com.atos.beans.booking;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.primefaces.model.DefaultStreamedContent;

import com.atos.beans.UserAudBean;

public class ContractAttachmentBean extends UserAudBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5255335809968287159L;
	
	private BigDecimal contractAttachmentId;
	private BigDecimal contractRequestId;
	private String fileName;
	private Date submissionDate;
	private BigDecimal contractAttachTypeId;
	private String comments;
	private String strIsDelete;
	private Date deleteDate;
	// binaryData y scFile deben tener la misma info. scFile se genera a partir de binaryData.
	// binaryData necesita metodo set (desde vista o desde consulta a BD) y set (insert a BD)
	// y scFile solo tendra metodo get (desde la vista), y se genera a partir de binaryData.
	// BinaryData no se pinta en el toString();
	private byte[] binaryData;	
	transient private DefaultStreamedContent scFile;

	
	public ContractAttachmentBean() {
		super();
		this.contractAttachmentId = null;
		this.contractRequestId = null;
		this.fileName = null;
		this.submissionDate = null;		
		this.comments = null;
		this.strIsDelete = null;
		this.deleteDate = null;
		this.binaryData = null;
		this.scFile = null;
	}


	public BigDecimal getContractAttachmentId() {
		return contractAttachmentId;
	}

	public void setContractAttachmentId(BigDecimal contractAttachmentId) {
		this.contractAttachmentId = contractAttachmentId;
	}

	public BigDecimal getContractRequestId() {
		return contractRequestId;
	}

	public void setContractRequestId(BigDecimal contractRequestId) {
		this.contractRequestId = contractRequestId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public BigDecimal getContractAttachTypeId() {
		return contractAttachTypeId;
	}

	public void setContractAttachTypeId(BigDecimal contractAttachTypeId) {
		this.contractAttachTypeId = contractAttachTypeId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStrIsDelete() {
		return strIsDelete;
	}

	public void setStrIsDelete(String strIsDelete) {
		this.strIsDelete = strIsDelete;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public byte[] getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}

	public DefaultStreamedContent getScFile() throws Exception {
		
		if( binaryData != null ) {
			ByteArrayInputStream bais = new ByteArrayInputStream(binaryData);
			scFile = new DefaultStreamedContent(bais);
			scFile.setName(fileName);
			// No se conoce el contentType.
			//scFile.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		}
		
		return scFile;
	}

// scFile solo tendra metodo get (desde la vista), y se genera a partir de binaryData.
//	public void setScFile(StreamedContent scFile) {
//		this.scFile = scFile;
//	}

	@Override
	public String toString() {
		return "ContractAttachmentBean [contractAttachmentId=" + contractAttachmentId + ", contractRequestId="
				+ contractRequestId + ", fileName=" + fileName + ", submissionDate=" + submissionDate
				+ ", contractAttachTypeId=" + contractAttachTypeId + ", comments=" + comments + ", strIsDelete="
				+ strIsDelete + ", deleteDate=" + deleteDate + ", binaryData=" + Arrays.toString(binaryData)
				+ ", scFile=" + scFile + "]";
	}


}
