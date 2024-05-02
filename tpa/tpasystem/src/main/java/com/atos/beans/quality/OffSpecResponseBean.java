package com.atos.beans.quality;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;

import com.atos.beans.UserAudBean;

public class OffSpecResponseBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4054490038623107092L;
	public static final String isRespondedYes = "Y";
	public static final String isRespondedNo = "N";	
	
	private BigDecimal incidentResponseId;
	private BigDecimal incidentId;
	private BigDecimal groupId;
	private String groupCode;	
	private String isResponded; 	// Valores posibles "Y" y "N".
	private String responseValue;	// Valores posibles "", "OK" y "KO".
	private Date responseDate;
	private String userComments;
	private String fileName;
	private byte[] attachedFile;
	private String fileOnStart;
	private String fileOnResponse;
	private BigDecimal userId;
	transient private DefaultStreamedContent scFile;
	
	private BigDecimal idnAction;
	private String action;
	private List<OffSpecActionFileBean> filesAction = new ArrayList<OffSpecActionFileBean>();
	
	//CH706
	private String operatorComments; // en realidad es Transporter Response comment
	
	public OffSpecResponseBean(BigDecimal incidentId, BigDecimal groupId, String isResponded, String responseValue, Date responseDate,
			String userComments, BigDecimal userId) {
		this.incidentId = incidentId;
		this.groupId = groupId;
		this.isResponded = isResponded;
		this.responseValue = responseValue;
		this.responseDate = responseDate;
		this.userComments = userComments;
		this.userId = userId;
	}

	public OffSpecResponseBean() {
		this.incidentResponseId = null;
		this.incidentId = null;
		this.groupId = null;
		this.groupCode = null;		
		this.isResponded = null;
		this.responseValue = null;
		this.responseDate = null;
		this.userComments = null;
		this.fileName = null;
		this.attachedFile = null;	
		this.fileOnStart = null;
		this.fileOnResponse = null;
		this.userId = null;
		this.scFile = null;
	}

	public BigDecimal getIncidentResponseId() {
		return incidentResponseId;
	}

	public void setIncidentResponseId(BigDecimal incidentResponseId) {
		this.incidentResponseId = incidentResponseId;
	}

	public BigDecimal getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(BigDecimal incidentId) {
		this.incidentId = incidentId;
	}

	public BigDecimal getGroupId() {
		return groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public List<OffSpecActionFileBean> getFilesAction() {
		return filesAction;
	}

	public void setFilesAction(List<OffSpecActionFileBean> filesAction) {
		this.filesAction = filesAction;
	}

	public BigDecimal getIdnAction() {
		return idnAction;
	}

	public void setIdnAction(BigDecimal idnAction) {
		this.idnAction = idnAction;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getIsResponded() {
		return isResponded;
	}

	public void setIsResponded(String isResponded) {
		this.isResponded = isResponded;
	}

	public String getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getUserComments() {
		return userComments;
	}

	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public String getOperatorComments() {
		return operatorComments;
	}

	public void setOperatorComments(String operatorComments) {
		this.operatorComments = operatorComments;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(byte[] attachedFile) {
		this.attachedFile = attachedFile;
	}

	public String getFileOnStart() {
		return fileOnStart;
	}

	public void setFileOnStart(String fileOnStart) {
		this.fileOnStart = fileOnStart;
	}

	public String getFileOnResponse() {
		return fileOnResponse;
	}

	public void setFileOnResponse(String fileOnResponse) {
		this.fileOnResponse = fileOnResponse;
	}
	
	public DefaultStreamedContent getScFile() {
		if( attachedFile != null ) {
			ByteArrayInputStream bais = new ByteArrayInputStream(attachedFile);
			scFile = new DefaultStreamedContent(bais);
			scFile.setName(fileName);
			// No se conoce el contentType.
			//scFile.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		}
		return scFile;
	}

	@Override
	public String toString() {
		return "OffSpecResponseBean [incidentResponseId=" + incidentResponseId + ", incidentId=" + incidentId
				+ ", groupId=" + groupId + ", groupCode=" + groupCode + ", isResponded=" + isResponded
				+ ", responseValue=" + responseValue + ", responseDate=" + responseDate + ", userComments="
				+ userComments + ", fileName=" + fileName + ", attachedFile=" + Arrays.toString(attachedFile)
				+ ", fileOnStart=" + fileOnStart + ", fileOnResponse=" + fileOnResponse + ", userId=" + userId
				+ ", operatorComments=" + operatorComments + "]";
	}
	
}
