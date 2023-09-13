package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class ContractRejectedPointBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4016696092433662995L;

	private BigDecimal id;
	private BigDecimal contractRequestId;
	private BigDecimal systemPointId;
	
	public ContractRejectedPointBean() {
		this.id = null;
		this.contractRequestId = null;
		this.systemPointId = null;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public BigDecimal getContractRequestId() {
		return contractRequestId;
	}
	public void setContractRequestId(BigDecimal contractRequestId) {
		this.contractRequestId = contractRequestId;
	}
	public BigDecimal getSystemPointId() {
		return systemPointId;
	}
	public void setSystemPointId(BigDecimal systemPointId) {
		this.systemPointId = systemPointId;
	}
}
