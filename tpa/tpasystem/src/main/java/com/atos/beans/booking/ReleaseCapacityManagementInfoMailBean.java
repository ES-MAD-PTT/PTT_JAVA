package com.atos.beans.booking;

import java.util.Date;

public class ReleaseCapacityManagementInfoMailBean {
	private Date start_date;
	private Date end_date;
	private String term_desc;
	
	
	
	public ReleaseCapacityManagementInfoMailBean() {
		super();
		this.start_date = null;
		this.end_date = null;
		this.term_desc = null;
	}

	public ReleaseCapacityManagementInfoMailBean(Date start_date, Date end_date, String term_desc) {
		super();
		this.start_date = start_date;
		this.end_date = end_date;
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

	public String getTerm_desc() {
		return term_desc;
	}

	public void setTerm_desc(String term_desc) {
		this.term_desc = term_desc;
	}
	
}
