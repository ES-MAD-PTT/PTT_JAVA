package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MailServerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8498811022984725198L;
	private BigDecimal idn_server;
	private String server_code; 
	private String server_desc; 
	private Date start_date; 
	private Date end_date; 
	private String connection_url; 
	private Integer connection_port; 
	private String connection_user; 
	private String connection_pwd;
	public BigDecimal getIdn_server() {
		return idn_server;
	}
	public void setIdn_server(BigDecimal idn_server) {
		this.idn_server = idn_server;
	}
	public String getServer_code() {
		return server_code;
	}
	public void setServer_code(String server_code) {
		this.server_code = server_code;
	}
	public String getServer_desc() {
		return server_desc;
	}
	public void setServer_desc(String server_desc) {
		this.server_desc = server_desc;
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
	public String getConnection_url() {
		return connection_url;
	}
	public void setConnection_url(String connection_url) {
		this.connection_url = connection_url;
	}
	public Integer getConnection_port() {
		return connection_port;
	}
	public void setConnection_port(Integer connection_port) {
		this.connection_port = connection_port;
	}
	public String getConnection_user() {
		return connection_user;
	}
	public void setConnection_user(String connection_user) {
		this.connection_user = connection_user;
	}
	public String getConnection_pwd() {
		return connection_pwd;
	}
	public void setConnection_pwd(String connection_pwd) {
		this.connection_pwd = connection_pwd;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MailServerBean [idn_server=");
		builder.append(idn_server);
		builder.append(", server_code=");
		builder.append(server_code);
		builder.append(", server_desc=");
		builder.append(server_desc);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", connection_url=");
		builder.append(connection_url);
		builder.append(", connection_port=");
		builder.append(connection_port);
		builder.append(", connection_user=");
		builder.append(connection_user);
		builder.append(", connection_pwd=");
		builder.append(connection_pwd);
		builder.append("]");
		return builder.toString();
	} 

	
}
