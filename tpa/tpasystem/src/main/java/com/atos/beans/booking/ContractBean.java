package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1610238166306776506L;

	private BigDecimal idnContract;
	private String contractCode;
	private BigDecimal idnOperation;
	private BigDecimal idnUserGroup;
	private Date startDate; 
	private Date endDate;
	private String isGranfathering;
	private Date signatureDate;
	private BigDecimal systemId;

	public ContractBean(String contractCode, BigDecimal idnOperation, BigDecimal idnUserGroup,
			Date startDate, Date endDate, BigDecimal systemId) {
		super();
		this.idnContract = null;
		this.contractCode = contractCode;
		this.idnOperation = idnOperation;
		this.idnUserGroup = idnUserGroup;
		this.startDate = startDate;
		this.endDate = endDate;
		// Este campo marca si el contrato procede de la carga inicial. Como no es el caso, marcamos siempre "N".
		this.isGranfathering = "N";	
		this.signatureDate = null;
		this.systemId = systemId;
	}

	public ContractBean() {
		this.idnContract = null;
		this.contractCode = null;
		this.idnOperation = null;
		this.idnUserGroup = null;
		this.startDate = null; 
		this.endDate = null;
		this.isGranfathering = null;
		this.signatureDate = null;
		this.systemId = null;
	}

	public BigDecimal getIdnContract() {
		return idnContract;
	}

	public void setIdnContract(BigDecimal idnContract) {
		this.idnContract = idnContract;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public BigDecimal getIdnOperation() {
		return idnOperation;
	}

	public void setIdnOperation(BigDecimal idnOperation) {
		this.idnOperation = idnOperation;
	}

	public BigDecimal getIdnUserGroup() {
		return idnUserGroup;
	}

	public void setIdnUserGroup(BigDecimal idnUserGroup) {
		this.idnUserGroup = idnUserGroup;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsGranfathering() {
		return isGranfathering;
	}

	public void setIsGranfathering(String isGranfathering) {
		this.isGranfathering = isGranfathering;
	}

	public Date getSignatureDate() {
		return signatureDate;
	}

	public void setSignatureDate(Date signatureDate) {
		this.signatureDate = signatureDate;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "ContractBean [idnContract=" + idnContract + ", contractCode=" + contractCode + ", idnOperation="
				+ idnOperation + ", idnUserGroup=" + idnUserGroup + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", isGranfathering=" + isGranfathering + ", signatureDate=" + signatureDate + ", systemId=" + systemId
				+ "]";
	}
}
