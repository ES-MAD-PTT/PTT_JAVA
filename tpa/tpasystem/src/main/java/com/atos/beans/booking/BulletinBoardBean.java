package com.atos.beans.booking;

import java.io.Serializable;

public class BulletinBoardBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3177630646408970911L;

	// CH011 - 05/09/16 - Se anade a esta pagina la funcionalidad de CRSubmission de descarga de template y envio ficheros excel.	
	private String operationTermCode;
	private String contractType;
	private String shipperSubmissionTime;
	private String submissionEnd;
	private String announcement;
	private String contractStart;
	private String user;
	private String language;
	
	public BulletinBoardBean() {
		this.operationTermCode = null;
		this.contractType = null;
		this.shipperSubmissionTime = null;
		this.submissionEnd = null;
		this.announcement = null;
		this.contractStart = null;
		this.user = null;
		this.language = null;
	}

	public String getOperationTermCode() {
		return operationTermCode;
	}

	public void setOperationTermCode(String operationTermCode) {
		this.operationTermCode = operationTermCode;
	}	
	
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getShipperSubmissionTime() {
		return shipperSubmissionTime;
	}

	public void setShipperSubmissionTime(String shipperSubmissionTime) {
		this.shipperSubmissionTime = shipperSubmissionTime;
	}

	public String getSubmissionEnd() {
		return submissionEnd;
	}

	public void setSubmissionEnd(String submissionEnd) {
		this.submissionEnd = submissionEnd;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getContractStart() {
		return contractStart;
	}

	public void setContractStart(String contractStart) {
		this.contractStart = contractStart;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "BulletinBoardBean [operationTermCode=" + operationTermCode + ", contractType=" + contractType
				+ ", shipperSubmissionTime=" + shipperSubmissionTime + ", submissionEnd=" + submissionEnd
				+ ", announcement=" + announcement + ", contractStart=" + contractStart + ", user=" + user
				+ ", language=" + language + "]";
	}
}
