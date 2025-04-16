package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SystemPointConnectBean implements Serializable{

	private static final long serialVersionUID = 5498298799508405348L;
	
	private BigDecimal idn_system_point_connect;
	private BigDecimal idn_system_point;
	private BigDecimal idn_nomination_concept;
    private Date start_date;
    private String username;
	public BigDecimal getIdn_system_point_connect() {
		return idn_system_point_connect;
	}
	public void setIdn_system_point_connect(BigDecimal idn_system_point_connect) {
		this.idn_system_point_connect = idn_system_point_connect;
	}
	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}
	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
	}
	public BigDecimal getIdn_nomination_concept() {
		return idn_nomination_concept;
	}
	public void setIdn_nomination_concept(BigDecimal idn_nomination_concept) {
		this.idn_nomination_concept = idn_nomination_concept;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
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
		builder.append("SystemPointConnectBean [idn_system_point_connect=");
		builder.append(idn_system_point_connect);
		builder.append(", idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", idn_nomination_concept=");
		builder.append(idn_nomination_concept);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", username=");
		builder.append(username);
		builder.append("]");
		return builder.toString();
	}
    
    
}
