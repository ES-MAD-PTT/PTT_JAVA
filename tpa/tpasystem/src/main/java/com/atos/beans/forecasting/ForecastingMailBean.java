package com.atos.beans.forecasting;

import java.io.Serializable;
import java.util.Date;

public class ForecastingMailBean implements Serializable {
	
	private static final long serialVersionUID = 3177630646408970911L;

	private String user_group_id;
	private String term_desc;
	private Date start_date;
	private Date end_date;
	
	public ForecastingMailBean() {
		super();
		this.user_group_id = null;
		this.term_desc = null;
		this.start_date = null;
		this.end_date = null;
	}

	public ForecastingMailBean(String user_group_id, String term_desc, Date start_date, Date end_date) {
		super();
		this.user_group_id = user_group_id;
		this.term_desc = term_desc;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}

	public String getTerm_desc() {
		return term_desc;
	}

	public void setTerm_desc(String term_desc) {
		this.term_desc = term_desc;
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
