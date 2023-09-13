package com.atos.beans.booking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;

import com.atos.beans.UserAudBean;

import java.math.BigDecimal;

public class ContractQueryBean extends UserAudBean implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 997914440840741998L;
	
	private BigDecimal id;
	private String requestCode;
	private Date submittedTimestamp;
	private BigDecimal shipperId;
	private String shipperCode;
	private String shipperName;
	private BigDecimal contractTypeId;
	private String contractTypeCode;
	private BigDecimal contractId;
	private String contractCode;
	private String status;
	private Date contractStartDate;
	private Date contractEndDate;
	private BigDecimal idnOperationFile;
	private StreamedContent xlsFile;
	private String xlsFileName;
	private String submissionComments;
	private HashMap<BigDecimal, String> hmRequestedPoints;
	private List<String> requestedPointCodes;
	private List<String> rejectedPointCodes;
	private DualListModel<String> dualListPointCodes;
	private String managementComments;
	private Integer shadowTime;		// Months
	private Integer shadowPeriod;	// Months
	private StreamedContent signedContTemplDocxScFile;
	private String signedContTemplDocxFileName;
		
	private Date acceptanceTimestamp;
	// En este arbol se guardan los contractsAgreements asociados 
	// a la capacityRequest. Util al hacer insert en "cascada".
	private TreeMap<Integer, ContractAgreementBean> tmContractAgreements;
	private List<ContractAttachmentBean> additionalDocs;	
	
	//offshore
	private BigDecimal idn_system;
	
	public ContractQueryBean() {
		super();
		this.id = null;
		this.requestCode = null;
		this.submittedTimestamp = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.shipperName = null;
		this.contractTypeId = null;
		this.contractTypeCode = null;
		this.contractId = null;
		this.contractCode = null;
		this.status = null;
		this.contractStartDate = null;
		this.contractEndDate = null;
		this.idnOperationFile = null;
		this.xlsFile = null;
		this.xlsFileName = null;
		this.submissionComments = null;
		this.hmRequestedPoints = new HashMap<BigDecimal, String>();
		this.requestedPointCodes = new ArrayList<String>();
		this.rejectedPointCodes = new ArrayList<String>();
		this.dualListPointCodes = new DualListModel<String>(this.requestedPointCodes, this.rejectedPointCodes);
		this.managementComments = null;
		this.shadowTime = new Integer(0);
		this.shadowPeriod = new Integer(0);
		this.acceptanceTimestamp = null;
		this.tmContractAgreements = null;
		this.additionalDocs = null;
		this.signedContTemplDocxScFile = null;
		this.signedContTemplDocxFileName = null;
		this.idn_system=null;
	}
	
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
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


	public BigDecimal getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(BigDecimal contractTypeId) {
		this.contractTypeId = contractTypeId;
	}

	public String getContractTypeCode() {
		return contractTypeCode;
	}

	public void setContractTypeCode(String contractTypeCode) {
		this.contractTypeCode = contractTypeCode;
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public BigDecimal getIdnOperationFile() {
		return idnOperationFile;
	}

	public void setIdnOperationFile(BigDecimal idnOperationFile) {
		this.idnOperationFile = idnOperationFile;
	}
	
	public StreamedContent getXlsFile() {
		return xlsFile;
	}

	public void setXlsFile(StreamedContent xlsFile) {
		this.xlsFile = xlsFile;
		this.xlsFileName = xlsFile.getName();
	}

	public String getXlsFileName() {
		return xlsFileName;
	}

	// El nombre del fichero se debe generar a partir del propio fichero, no establecerse desde fuera.
//	public void setXlsFileName(String xlsFileName) {
//		this.xlsFileName = xlsFileName;
//	}

	public String getSubmissionComments() {
		return submissionComments;
	}

	public void setSubmissionComments(String submissionComments) {
		this.submissionComments = submissionComments;
	}

	public HashMap<BigDecimal, String> getHmRequestedPoints() {
		return hmRequestedPoints;
	}

	public void setHmRequestedPoints(HashMap<BigDecimal, String> hmRequestedPoints) {
		this.hmRequestedPoints = hmRequestedPoints;
	}

	public List<String> getRequestedPointCodes() {
		return requestedPointCodes;
	}

	public void setRequestedPointCodes(List<String> requestedPointCodes) {
		// Si se actualiza una lista individual, se actualiza tambien la dual.
		this.requestedPointCodes = requestedPointCodes;
		this.dualListPointCodes.setSource(requestedPointCodes);
	}
	
	public List<String> getRejectedPointCodes() {
		return rejectedPointCodes;
	}

	public void setRejectedPointCodes(List<String> rejectedPointCodes) {
		// Si se actualiza una lista individual, se actualiza tambien la dual.
		this.rejectedPointCodes = rejectedPointCodes;
		this.dualListPointCodes.setTarget(rejectedPointCodes);
	}

	public DualListModel<String> getDualListPointCodes() {
		return dualListPointCodes;
	}

	public void setDualListPointCodes(DualListModel<String> dualListPointCodes) {
		this.dualListPointCodes = dualListPointCodes;
		// Si se actualiza la lista dual desde la vista, actualizo las listas individuales.
		this.requestedPointCodes = dualListPointCodes.getSource();
		this.rejectedPointCodes = dualListPointCodes.getTarget();
	}

	public String getManagementComments() {
		return managementComments;
	}

	public void setManagementComments(String managementComments) {
		this.managementComments = managementComments;
	}

	public Integer getShadowTime() {
		return shadowTime;
	}

	public void setShadowTime(Integer shadowTime) {
		this.shadowTime = shadowTime;
	}

	public Integer getShadowPeriod() {
		return shadowPeriod;
	}

	public void setShadowPeriod(Integer shadowPeriod) {
		this.shadowPeriod = shadowPeriod;
	}

	public Date getAcceptanceTimestamp() {
		return acceptanceTimestamp;
	}

	public void setAcceptanceTimestamp(Date acceptanceTimestamp) {
		this.acceptanceTimestamp = acceptanceTimestamp;
	}	

	public TreeMap<Integer, ContractAgreementBean> getTmContractAgreements() {
		return tmContractAgreements;
	}

	public void setTmContractAgreements(TreeMap<Integer, ContractAgreementBean> tmContractAgreements) {
		this.tmContractAgreements = tmContractAgreements;
	}

    public List<ContractAttachmentBean> getAdditionalDocs() {
		return additionalDocs;
	}

	public void setAdditionalDocs(List<ContractAttachmentBean> additionalDocs) {
		this.additionalDocs = additionalDocs;
	}

	public StreamedContent getSignedContTemplDocxScFile() {
		return signedContTemplDocxScFile;
	}

	public void setSignedContTemplDocxScFile(StreamedContent signedContTemplDocxScFile) {
		this.signedContTemplDocxScFile = signedContTemplDocxScFile;
		this.signedContTemplDocxFileName = signedContTemplDocxScFile.getName();
	}

	public String getSignedContTemplDocxFileName() {
		return signedContTemplDocxFileName;
	}

	// El nombre del fichero se debe generar a partir del propio fichero, no establecerse desde fuera.
	//public void setSignedContTemplDocxFileName(String signedContTemplDocxFileName) {
	//	this.signedContTemplDocxFileName = signedContTemplDocxFileName;
	//}
	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}
	
	@Override
	public String toString() {
		return "ContractQueryBean [id=" + id + ", requestCode=" + requestCode + ", submittedTimestamp="
				+ submittedTimestamp + ", shipperId=" + shipperId + ", shipperCode=" + shipperCode + ", shipperName="
				+ shipperName + ", contractTypeId=" + contractTypeId + ", contractTypeCode=" + contractTypeCode
				+ ", contractId=" + contractId + ", contractCode=" + contractCode + ", status=" + status
				+ ", contractStartDate=" + contractStartDate + ", contractEndDate=" + contractEndDate
				+ ", idnOperationFile=" + idnOperationFile + ", xlsFile=" + xlsFile + ", xlsFileName=" + xlsFileName
				+ ", submissionComments=" + submissionComments + ", hmRequestedPoints=" + hmRequestedPoints
				+ ", requestedPointCodes=" + requestedPointCodes + ", rejectedPointCodes=" + rejectedPointCodes
				+ ", dualListPointCodes=" + dualListPointCodes + ", managementComments=" + managementComments
				+ ", shadowTime=" + shadowTime + ", shadowPeriod=" + shadowPeriod + ", signedContTemplDocxScFile="
				+ signedContTemplDocxScFile + ", signedContTemplDocxFileName=" + signedContTemplDocxFileName
				+ ", acceptanceTimestamp=" + acceptanceTimestamp + ", tmContractAgreements=" + tmContractAgreements
				+ ", additionalDocs=" + additionalDocs + ", idn_system=" + idn_system + "]";
	}

		
}
