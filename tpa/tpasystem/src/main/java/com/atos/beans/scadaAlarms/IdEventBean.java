package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class IdEventBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -8210572551497660753L;

	private String type;
	private Date date;
	private String p_id;
	private String error_desc;
	private BigDecimal valid;
	
	
	public IdEventBean() {
		super();
		this.type = null;
		this.date = null;
		this.p_id = null;
		this.error_desc = null;
		this.valid = null;
	}

	public IdEventBean(String type, Date date, String p_id, String error_desc, BigDecimal valid) {
		super();
		this.type = type;
		this.date = date;
		this.p_id = p_id;
		this.error_desc = error_desc;
		this.valid = valid;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getP_id() {
		return p_id;
	}
	
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	
	public String getError_desc() {
		return error_desc;
	}
	
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	
	public BigDecimal getValid() {
		return valid;
	}
	
	public void setValid(BigDecimal valid) {
		this.valid = valid;
	}
}
