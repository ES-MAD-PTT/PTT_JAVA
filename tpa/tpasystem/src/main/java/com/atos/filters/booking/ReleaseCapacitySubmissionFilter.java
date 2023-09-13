package com.atos.filters.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReleaseCapacitySubmissionFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3659231494702225841L;
	private BigDecimal contractId;
	private BigDecimal systemPointId;
	// No se guarda directamente el aggreementStartDate porque al final, el usuario va a escoger un año concreto para filtrar.
	// Si hubieran luego dos agreementes distintos (distintos dia y/o mes) con el mismo año, en la tabla de datos
	// aparecerian los registros de los dos agreements.
	private Date from;
	private Date to;
	
	
	public ReleaseCapacitySubmissionFilter() {
		this.contractId = null;
		this.systemPointId = null;
		this.from  = null;
		this.to = null;
	}
	
	public BigDecimal getcontractId() {
		return contractId;
	}

	public void setcontractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public BigDecimal getSystemPointId() {
		return systemPointId;
	}

	public void setSystemPointId(BigDecimal systemPointId) {
		this.systemPointId = systemPointId;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "ReleaseCapacitySubmissionFilter [contractId=" + contractId + ", systemPointId=" + systemPointId
				+ ", from=" + from + ", to=" + to + "]";
	}

}
