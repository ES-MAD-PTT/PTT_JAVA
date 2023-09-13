package com.atos.beans.nominations;

import java.io.Serializable;
import java.util.Date;

public class IntermediateNomFileMailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3177630646408970911L;

	private String user_group_id;
	private String contract_code;
	private Date start_date;
	
	public IntermediateNomFileMailBean() {
		this.user_group_id = null;
		this.contract_code = null;
		this.start_date = null;
	}
	
	public IntermediateNomFileMailBean(String user_group_id, String contract_code, Date start_date) {
		super();
		this.user_group_id = user_group_id;
		this.contract_code = contract_code;
		this.start_date = start_date;
	}

	public String getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}

	public String getContract_code() {
		return contract_code;
	}

	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
}
