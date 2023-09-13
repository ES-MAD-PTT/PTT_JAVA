package com.atos.filters.booking;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReleaseCapacityManagementFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4598285634132756018L;
	
	private BigDecimal shipperId;
	private String statusCode;
	private BigDecimal idn_system;

	public ReleaseCapacityManagementFilter() {
		this.shipperId = null;
		this.statusCode = null;		
		this.idn_system=null;
		
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	@Override
	public String toString() {
		return "ReleaseCapacityManagementFilter [shipperId=" + shipperId + ", statusCode=" + statusCode
				+ ", idn_system=" + idn_system + "]";
	}

	
}
