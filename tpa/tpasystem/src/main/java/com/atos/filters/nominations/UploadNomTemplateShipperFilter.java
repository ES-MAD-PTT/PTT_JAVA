package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class UploadNomTemplateShipperFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal shipperId;
	private BigDecimal contractId;

	private BigDecimal systemId;
	
	public UploadNomTemplateShipperFilter() {
		this.shipperId = null;
		this.contractId = null;
		this.systemId = null;
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

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "TemplateForShipperFilter [shipperId=" + shipperId
				+ ", contractId=" + contractId + ", systemId=" + systemId + "]";
	}

}
