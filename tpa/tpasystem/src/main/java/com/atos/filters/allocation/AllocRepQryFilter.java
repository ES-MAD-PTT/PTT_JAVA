package com.atos.filters.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class AllocRepQryFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dateFrom;
	private Date dateTo;
	private BigDecimal shipperId;
	private BigDecimal contractId;
	private BigDecimal nomPointId;
	private String[] statusCode;
	private String reviewCode;
	private BigDecimal factorFromDefaultUnit; // Para que en la consulta se
												// transforme la unidad de
												// energia.
	private BigDecimal systemId;
	
	public AllocRepQryFilter() {
		this.dateFrom = null;
		this.dateTo = null;
		this.shipperId = null;
		this.contractId = null;
		this.nomPointId = null;
		this.statusCode = null;
		this.reviewCode = null;
		this.factorFromDefaultUnit = null;
		this.systemId = null;
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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

	public BigDecimal getNomPointId() {
		return nomPointId;
	}

	public void setNomPointId(BigDecimal nomPointId) {
		this.nomPointId = nomPointId;
	}

	public String[] getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String[] statusCode) {
		this.statusCode = statusCode;
	}

	public String getReviewCode() {
		return reviewCode;
	}

	public void setReviewCode(String reviewCode) {
		this.reviewCode = reviewCode;
	}

	public BigDecimal getFactorFromDefaultUnit() {
		return factorFromDefaultUnit;
	}

	public void setFactorFromDefaultUnit(BigDecimal factorFromDefaultUnit) {
		this.factorFromDefaultUnit = factorFromDefaultUnit;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "AllocRepQryFilter [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", shipperId=" + shipperId
				+ ", contractId=" + contractId + ", nomPointId=" + nomPointId + ", statusCode="
				+ Arrays.toString(statusCode) + ", reviewCode=" + reviewCode + ", factorFromDefaultUnit="
				+ factorFromDefaultUnit + ", systemId=" + systemId + "]";
	}

}
