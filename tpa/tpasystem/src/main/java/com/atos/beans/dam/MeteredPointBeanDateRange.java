package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class MeteredPointBeanDateRange extends UserAudBean implements Serializable {

	private static final long serialVersionUID = 1923617399438288524L;
	
	private Date date_ini;
	private Date date_fin;
	private BigDecimal idn_system_point;
	private BigDecimal idn_system_point_param;
	private String type;
	private String language;
	private String user;
	private Integer errorCode;
	private String errorDesc;
	public Date getDate_ini() {
		return date_ini;
	}
	public void setDate_ini(Date date_ini) {
		this.date_ini = date_ini;
	}
	public Date getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}
	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}
	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
	}
	public BigDecimal getIdn_system_point_param() {
		return idn_system_point_param;
	}
	public void setIdn_system_point_param(BigDecimal idn_system_point_param) {
		this.idn_system_point_param = idn_system_point_param;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeteredPointBeanDateRange [date_ini=");
		builder.append(date_ini);
		builder.append(", date_fin=");
		builder.append(date_fin);
		builder.append(", idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", idn_system_point_param=");
		builder.append(idn_system_point_param);
		builder.append(", type=");
		builder.append(type);
		builder.append(", language=");
		builder.append(language);
		builder.append(", user=");
		builder.append(user);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", errorDesc=");
		builder.append(errorDesc);
		builder.append("]");
		return builder.toString();
	}

}
