package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CapacityRequestMailBean implements Serializable{
	
	private static final long serialVersionUID = 1610238166306776506L;
	
	private BigDecimal idn_user_group;
	private String user_group_id;
	private String operation_desc;
	private String contract_num;
	private Date start_date;
	private Date end_date;
	
	
	
	public CapacityRequestMailBean() {
		super();
		this.idn_user_group = null;
		this.user_group_id = null;
		this.operation_desc = null;
		this.contract_num = null;
		this.start_date = null;
		this.end_date = null;
	}

	public CapacityRequestMailBean(BigDecimal idn_user_group, String user_group_id, String operation_desc,
			String contract_num, Date start_date, Date end_date) {
		super();
		this.idn_user_group = idn_user_group;
		this.user_group_id = user_group_id;
		this.operation_desc = operation_desc;
		this.contract_num = contract_num;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public String getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}

	public String getOperation_desc() {
		return operation_desc;
	}

	public void setOperation_desc(String operation_desc) {
		this.operation_desc = operation_desc;
	}

	public String getContract_num() {
		return contract_num;
	}

	public void setContract_num(String contract_num) {
		this.contract_num = contract_num;
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
	
}
