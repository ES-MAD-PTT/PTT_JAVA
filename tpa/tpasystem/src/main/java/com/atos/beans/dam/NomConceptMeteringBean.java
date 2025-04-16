package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NomConceptMeteringBean implements Serializable{

	private static final long serialVersionUID = -8656173404538438983L;
	
	private BigDecimal idn_system_point;
	private Date start_date;
	private BigDecimal idn_nomination_concept;
	private String username;
	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}
	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public BigDecimal getIdn_nomination_concept() {
		return idn_nomination_concept;
	}
	public void setIdn_nomination_concept(BigDecimal idn_nomination_concept) {
		this.idn_nomination_concept = idn_nomination_concept;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NomConceptMeteringBean [idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", idn_nomination_concept=");
		builder.append(idn_nomination_concept);
		builder.append(", username=");
		builder.append(username);
		builder.append("]");
		return builder.toString();
	}

	
}
