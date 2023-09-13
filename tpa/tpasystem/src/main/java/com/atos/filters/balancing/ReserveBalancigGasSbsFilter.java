package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class ReserveBalancigGasSbsFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8741334525216688454L;
	
	private BigDecimal shipperId;
	private BigDecimal reserveBalId;
	private BigDecimal capContractId;
	private BigDecimal idnZone;
	private BigDecimal idnSystem;
	private Date fromDate;
	private Date toDate;
	private boolean shipper;
	

	public BigDecimal getCapContractId() {
		return capContractId;
	}

	public void setCapContractId(BigDecimal capContractId) {
		this.capContractId = capContractId;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {

			this.fromDate = fromDate;
			
	}

	public Date getToDate() {
		return toDate;
	}


	@Override
	public String toString() {
		return "ReserveBalancigGasFilter [shipperId=" + shipperId + ", fromDate=" + fromDate + "]";
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getIdnZone() {
		return idnZone;
	}

	public void setIdnZone(BigDecimal idnZone) {
		this.idnZone = idnZone;
	}

	public BigDecimal getReserveBalId() {
		return reserveBalId;
	}

	public void setReserveBalId(BigDecimal reserveBalId) {
		this.reserveBalId = reserveBalId;
	}

	public boolean isShipper() {
		return shipper;
	}

	public void setShipper(boolean shipper) {
		this.shipper = shipper;
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}
	

}
