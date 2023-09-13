package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignedContractTempPeriodBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5714807479765865223L;

	private Date pageDate;
	private BigDecimal contractAgreementId;
	private BigDecimal contractRequestId;					// Necesario para utilizarlo en la consulta en puntos exit.
	private List<SignedContractTempPointBean> entryPoints;
	private List<SignedContractTempPointBean> exitPoints;
	
	public SignedContractTempPeriodBean() {
		this.pageDate = null;
		this.contractAgreementId = null;
		this.contractRequestId = null;
		this.entryPoints = new ArrayList<SignedContractTempPointBean>();
		this.exitPoints = new ArrayList<SignedContractTempPointBean>();
	}

	public Date getPageDate() {
		return pageDate;
	}

	public void setPageDate(Date pageDate) {
		this.pageDate = pageDate;
	}

	public BigDecimal getContractAgreementId() {
		return contractAgreementId;
	}

	public void setContractAgreementId(BigDecimal contractAgreementId) {
		this.contractAgreementId = contractAgreementId;
	}

	public BigDecimal getContractRequestId() {
		return contractRequestId;
	}

	public void setContractRequestId(BigDecimal contractRequestId) {
		this.contractRequestId = contractRequestId;
	}

	public List<SignedContractTempPointBean> getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(List<SignedContractTempPointBean> entryPoints) {
		this.entryPoints = entryPoints;
	}

	public List<SignedContractTempPointBean> getExitPoints() {
		return exitPoints;
	}

	public void setExitPoints(List<SignedContractTempPointBean> exitPoints) {
		this.exitPoints = exitPoints;
	}

	@Override
	public String toString() {
		return "SignedContractTempPeriodBean [pageDate=" + pageDate + ", contractAgreementId=" + contractAgreementId
				+ ", contractRequestId=" + contractRequestId + ", entryPoints=" + entryPoints + ", exitPoints="
				+ exitPoints + "]";
	}
}
