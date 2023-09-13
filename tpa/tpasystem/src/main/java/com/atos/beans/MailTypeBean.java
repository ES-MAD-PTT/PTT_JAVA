package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MailTypeBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7794541960225534736L;
	
	private BigDecimal idn_email_type; 
	private String type_code;
	private String type_desc; 
	private Date start_date; 
	private Date end_date; 
	private String email_module; 
	private BigDecimal idn_permission; 
	private BigDecimal idn_message_subject; 
	private String body_template; 
	private String is_enabled; 
	private String is_group_restricted;
	
	private BigDecimal idn_user_group;
	private BigDecimal idn_system;
	private String language;
	
	public BigDecimal getIdn_email_type() {
		return idn_email_type;
	}
	public void setIdn_email_type(BigDecimal idn_email_type) {
		this.idn_email_type = idn_email_type;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_desc() {
		return type_desc;
	}
	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getEmail_module() {
		return email_module;
	}
	public void setEmail_module(String email_module) {
		this.email_module = email_module;
	}
	public BigDecimal getIdn_permission() {
		return idn_permission;
	}
	public void setIdn_permission(BigDecimal idn_permission) {
		this.idn_permission = idn_permission;
	}
	public BigDecimal getIdn_message_subject() {
		return idn_message_subject;
	}
	public void setIdn_message_subject(BigDecimal idn_message_subject) {
		this.idn_message_subject = idn_message_subject;
	}
	public String getBody_template() {
		return body_template;
	}
	public void setBody_template(String body_template) {
		this.body_template = body_template;
	}
	public String getIs_enabled() {
		return is_enabled;
	}
	public void setIs_enabled(String is_enabled) {
		this.is_enabled = is_enabled;
	}
	public String getIs_group_restricted() {
		return is_group_restricted;
	}
	public void setIs_group_restricted(String is_group_restricted) {
		this.is_group_restricted = is_group_restricted;
	}
	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}
	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}
	public BigDecimal getIdn_system() {
		return idn_system;
	}
	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MailTypeBean [idn_email_type=");
		builder.append(idn_email_type);
		builder.append(", type_code=");
		builder.append(type_code);
		builder.append(", type_desc=");
		builder.append(type_desc);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", email_module=");
		builder.append(email_module);
		builder.append(", idn_permission=");
		builder.append(idn_permission);
		builder.append(", idn_message_subject=");
		builder.append(idn_message_subject);
		builder.append(", body_template=");
		builder.append(body_template);
		builder.append(", is_enabled=");
		builder.append(is_enabled);
		builder.append(", is_group_restricted=");
		builder.append(is_group_restricted);
		builder.append(", idn_user_group=");
		builder.append(idn_user_group);
		builder.append(", idn_system=");
		builder.append(idn_system);
		builder.append(", language=");
		builder.append(language);
		builder.append("]");
		return builder.toString();
	}
	
}
