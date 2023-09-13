package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;

public class NominationDeadlineBean implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1548499176220091674L;
	private String deadlineTypeCode;
    private String termCode;
	private String sHour;
	private Integer daysBefore;
	
	public NominationDeadlineBean() {
		super();
		this.deadlineTypeCode = null;
		this.termCode = null;
		this.sHour = null;
		this.daysBefore = null;
	}

	public String getDeadlineTypeCode() {
		return deadlineTypeCode;
	}

	public void setDeadlineTypeCode(String deadlineTypeCode) {
		this.deadlineTypeCode = deadlineTypeCode;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getsHour() {
		return sHour;
	}

	public void setsHour(String sHour) {
		this.sHour = sHour;
	}

	public Integer getDaysBefore() {
		return daysBefore;
	}

	public void setDaysBefore(Integer daysBefore) {
		this.daysBefore = daysBefore;
	}

	@Override
	public String toString() {
		return "NominationDeadlineBean [deadlineTypeCode=" + deadlineTypeCode + ", termCode=" + termCode + ", sHour="
				+ sHour + ", daysBefore=" + daysBefore + "]";
	}
	
}
