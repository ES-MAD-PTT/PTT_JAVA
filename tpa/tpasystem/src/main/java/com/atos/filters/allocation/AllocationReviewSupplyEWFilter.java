package com.atos.filters.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class AllocationReviewSupplyEWFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5513559587022754819L;
	
	private Date gasDay;
	private BigDecimal shipperId;
	private BigDecimal contractId;
	private String[] statusCode;
	private String reviewCode;
	private BigDecimal factorFromDefaultUnit;	// Para que en la consulta se transforme la unidad de energia.
	private BigDecimal nominationConceptId;
	private BigDecimal systemId;
	private String system;
	
	public AllocationReviewSupplyEWFilter() {
		this.gasDay = null;
		this.shipperId = null;
		this.contractId = null;
		this.statusCode = null;
		this.reviewCode = null;
		this.factorFromDefaultUnit = null;
		this.nominationConceptId = null;
		this.systemId = null;
	}

	public AllocationReviewSupplyEWFilter(AllocationReviewSupplyEWFilter _filter) {
		this.gasDay = _filter.getGasDay();
		this.shipperId = _filter.getShipperId();
		this.contractId = _filter.getContractId();
		if(_filter.getStatusCode()!= null) {
			this.statusCode = new String[_filter.getStatusCode().length];
			System.arraycopy( _filter.getStatusCode(), 0, this.statusCode, 0, _filter.getStatusCode().length );
		}
		this.reviewCode = _filter.getReviewCode();
		this.factorFromDefaultUnit = _filter.getFactorFromDefaultUnit();
		this.nominationConceptId = _filter.getNominationConceptId();
		this.systemId = _filter.getSystemId();
		this.system = _filter.getSystem();
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
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

	public BigDecimal getNominationConceptId() {
		return nominationConceptId;
	}

	public void setNominationConceptId(BigDecimal nominationConceptId) {
		this.nominationConceptId = nominationConceptId;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "AllocationReviewSupplyEWFilter [gasDay=" + gasDay + ", shipperId=" + shipperId + ", contractId="
				+ contractId + ", statusCode=" + Arrays.toString(statusCode) + ", reviewCode=" + reviewCode
				+ ", factorFromDefaultUnit=" + factorFromDefaultUnit + ", nominationConceptId=" + nominationConceptId
				+ ", systemId=" + systemId + "]";
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

}
