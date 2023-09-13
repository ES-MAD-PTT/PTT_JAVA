package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReserveBalancingGasContractsFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 264145137591204825L;

	private BigDecimal shipperId;
	private BigDecimal resBalGasContractId;
	private Date startDate;
	private Date endDate;
	private BigDecimal zoneId;
	private BigDecimal idnSystem;
	
	public ReserveBalancingGasContractsFilter() {
		this.shipperId = null;
		this.resBalGasContractId = null;
		this.startDate = null;
		this.endDate = null;
		this.zoneId = null;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public BigDecimal getResBalGasContractId() {
		return resBalGasContractId;
	}

	public void setResBalGasContractId(BigDecimal resBalGasContractId) {
		this.resBalGasContractId = resBalGasContractId;
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

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public String toString() {
		return "ReserveBalancingGasContractsFilter [shipperId=" + shipperId + ", resBalGasContractId="
				+ resBalGasContractId + ", startDate=" + startDate + ", endDate=" + endDate + ", zoneId=" + zoneId
				+ "]";
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

}
