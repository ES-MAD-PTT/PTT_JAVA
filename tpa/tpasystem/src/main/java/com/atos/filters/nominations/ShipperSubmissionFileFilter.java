package com.atos.filters.nominations;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ShipperSubmissionFileFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8324814390526307291L;
	private Date start_date;
	private Date end_date;
	private String contract_code;
	private String user;
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.start_date);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		this.setEnd_date(cal.getTime());
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contract_code == null) ? 0 : contract_code.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipperSubmissionFileFilter other = (ShipperSubmissionFileFilter) obj;
		if (contract_code == null) {
			if (other.contract_code != null)
				return false;
		} else if (!contract_code.equals(other.contract_code))
			return false;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ShipperSubmissionFileFilter [start_date=" + start_date + ", end_date=" + end_date + ", contract_code="
				+ contract_code + ", user=" + user + "]";
	}

	
	

	
}
