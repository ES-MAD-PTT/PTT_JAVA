package com.atos.filters.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AllocationIntradayFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dateFrom;
	private Date dateTo;
	private BigDecimal shipperId;
	private BigDecimal contractId;
	private BigDecimal nomPointId;	
	private BigDecimal systemId;
	private boolean isShipper;
	private BigDecimal factorFromDefaultUnit;
	
	public AllocationIntradayFilter() {
		this.dateFrom = null;
		this.dateTo = null;
		this.shipperId = null;
		this.contractId = null;
		this.nomPointId = null;
		this.systemId = null;
		this.isShipper = false;
		this.factorFromDefaultUnit = null;
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

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	public boolean isShipper() {
		return isShipper;
	}

	public void setIsShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}

	public BigDecimal getFactorFromDefaultUnit() {
		return factorFromDefaultUnit;
	}

	public void setFactorFromDefaultUnit(BigDecimal factorFromDefaultUnit) {
		this.factorFromDefaultUnit = factorFromDefaultUnit;
	}
	
}
