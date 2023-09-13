package com.atos.filters.nominations;

import java.io.Serializable;
import java.util.Date;

public class IntermediateSubmissionFileFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3929974414344800858L;
	private Date start_date;
	private String contract_code;
	private String shipper_code;
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contract_code == null) ? 0 : contract_code.hashCode());
		result = prime * result + ((shipper_code == null) ? 0 : shipper_code.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
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
		IntermediateSubmissionFileFilter other = (IntermediateSubmissionFileFilter) obj;
		if (contract_code == null) {
			if (other.contract_code != null)
				return false;
		} else if (!contract_code.equals(other.contract_code))
			return false;
		if (shipper_code == null) {
			if (other.shipper_code != null)
				return false;
		} else if (!shipper_code.equals(other.shipper_code))
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IntermediateDailySubmissionFileFilter [start_date=" + start_date + ", contract_code=" + contract_code
				+ ", shipper_code=" + shipper_code + "]";
	}

	
}
