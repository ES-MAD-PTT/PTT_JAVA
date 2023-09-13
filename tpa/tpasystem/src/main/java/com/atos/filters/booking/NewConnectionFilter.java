package com.atos.filters.booking;

import java.io.Serializable;
import java.math.BigDecimal;

public class NewConnectionFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2698341625000373050L;
	private BigDecimal shipperId;
	private BigDecimal idn_system;

	public NewConnectionFilter() {
		this.shipperId = null;
		this.idn_system= null;
	}
	
	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	@Override
	public String toString() {
		return "NewConnectionFilter [shipperId=" + shipperId + ", idn_system=" + idn_system + "]";
	}
	
}
