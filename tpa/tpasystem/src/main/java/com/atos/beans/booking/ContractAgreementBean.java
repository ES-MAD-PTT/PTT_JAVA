package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TreeMap;

import com.atos.beans.UserAudBean;

public class ContractAgreementBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4311712921945284016L;

	private BigDecimal idnContractAgreement;
	private BigDecimal idnContractRequest;
	private Date startDate; 
	private Date endDate;

    // En este arbol se guardan los contractPoints asociados 
	// al contractAgreement. Util al hacer insert en "cascada".
	private TreeMap<Integer, ContractPointBean> tmContractPoints;	

	public ContractAgreementBean(BigDecimal idnContractRequest, Date startDate,
			Date endDate) {
		super();
		this.idnContractAgreement = null;		
		this.idnContractRequest = idnContractRequest;
		this.startDate = startDate;
		this.endDate = endDate;
		this.tmContractPoints = null;
	}

	public ContractAgreementBean() {
		this.idnContractAgreement = null;
		this.idnContractRequest = null;
		this.startDate = null; 
		this.endDate = null;
		this.tmContractPoints = null;		
	}

	public BigDecimal getIdnContractAgreement() {
		return idnContractAgreement;
	}

	public void setIdnContractAgreement(BigDecimal idnContractAgreement) {
		this.idnContractAgreement = idnContractAgreement;
	}

	public BigDecimal getIdnContractRequest() {
		return idnContractRequest;
	}

	public void setIdnContractRequest(BigDecimal idnContractRequest) {
		this.idnContractRequest = idnContractRequest;
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

	
	public TreeMap<Integer, ContractPointBean> getTmContractPoints() {
		return tmContractPoints;
	}

	public void setTmContractPoints(TreeMap<Integer, ContractPointBean> tmContractPoints) {
		this.tmContractPoints = tmContractPoints;
	}

	@Override
	public String toString() {
		return "ContractAgreementBean [idnContractAgreement=" + idnContractAgreement + ", idnContractRequest="
				+ idnContractRequest + ", startDate=" + startDate + ", endDate=" + endDate + ", tmContractPoints="
				+ tmContractPoints + "]";
	}	
}
