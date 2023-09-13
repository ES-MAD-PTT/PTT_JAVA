package com.atos.filters.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AllocationAdjustFilter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5310091707233120843L;
	/**
	 * 
	 */
	private Date startDate;
	private Date enDate;
	private BigDecimal shipperId;
	private BigDecimal contractId;
	private String[] statusCode;
	private String adjustmentCode;
	private BigDecimal idnSystem;

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEnDate() {
		return enDate;
	}
	public void setEnDate(Date enDate) {
		this.enDate = enDate;
	}
	public BigDecimal getShipperId() {
		return shipperId;
	}
	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}
	public BigDecimal getContractId() {
		return contractId;
	}
	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String[] getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String[] statusCode) {
		this.statusCode = statusCode;
	}

	public String getAdjustmentCode() {
		return adjustmentCode;
	}

	public void setAdjustmentCode(String adjustmentCode) {
		this.adjustmentCode = adjustmentCode;
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

}
