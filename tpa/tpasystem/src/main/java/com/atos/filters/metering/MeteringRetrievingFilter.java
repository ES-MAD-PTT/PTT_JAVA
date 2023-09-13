package com.atos.filters.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class MeteringRetrievingFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 689265227361385489L;

	private Date generatedDayFrom;
	private Date generatedDayTo;
	private String inputCode;
	private BigDecimal idnMeteringInput;
	
	public MeteringRetrievingFilter() {
		this.generatedDayFrom = null;
		this.generatedDayTo = null;
		this.inputCode = null;
		this.idnMeteringInput = null;
	}

	public Date getGeneratedDayFrom() {
		return generatedDayFrom;
	}

	public void setGeneratedDayFrom(Date generatedDayFrom) {
		this.generatedDayFrom = generatedDayFrom;
	}

	public Date getGeneratedDayTo() {
		return generatedDayTo;
	}

	public void setGeneratedDayTo(Date generatedDayTo) {
		this.generatedDayTo = generatedDayTo;
	}

	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

	public BigDecimal getIdnMeteringInput() {
		return idnMeteringInput;
	}

	public void setIdnMeteringInput(BigDecimal idnMeteringInput) {
		this.idnMeteringInput = idnMeteringInput;
	}

	@Override
	public String toString() {
		return "MeteringRetrievingFilter [generatedDayFrom=" + generatedDayFrom + ", generatedDayTo=" + generatedDayTo
				+ ", inputCode=" + inputCode + ", idnMeteringInput=" + idnMeteringInput + "]";
	}

	
}
