package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NominationFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6164414217990922096L;
	// Filters
	private String shipper_code;
	private String contract_code;
	private Date start_date;
	private Date end_date;
	private String not_meet;
	private String user;
	private String category_code; // NOMINATION
	private String term_code; // DAILY, WEEKLY
	private String type_code; // SHIPPER
	private BigDecimal idn_pipeline_system;
	
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
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
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getNot_meet() {
		return not_meet;
	}
	public void setNot_meet(String not_meet) {
		this.not_meet = not_meet;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}
	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_code == null) ? 0 : category_code.hashCode());
		result = prime * result + ((contract_code == null) ? 0 : contract_code.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((idn_pipeline_system == null) ? 0 : idn_pipeline_system.hashCode());
		result = prime * result + ((not_meet == null) ? 0 : not_meet.hashCode());
		result = prime * result + ((shipper_code == null) ? 0 : shipper_code.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((term_code == null) ? 0 : term_code.hashCode());
		result = prime * result + ((type_code == null) ? 0 : type_code.hashCode());
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
		NominationFilter other = (NominationFilter) obj;
		if (category_code == null) {
			if (other.category_code != null)
				return false;
		} else if (!category_code.equals(other.category_code))
			return false;
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
		if (idn_pipeline_system == null) {
			if (other.idn_pipeline_system != null)
				return false;
		} else if (!idn_pipeline_system.equals(other.idn_pipeline_system))
			return false;
		if (not_meet == null) {
			if (other.not_meet != null)
				return false;
		} else if (!not_meet.equals(other.not_meet))
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
		if (term_code == null) {
			if (other.term_code != null)
				return false;
		} else if (!term_code.equals(other.term_code))
			return false;
		if (type_code == null) {
			if (other.type_code != null)
				return false;
		} else if (!type_code.equals(other.type_code))
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
		return "NominationFilter [shipper_code=" + shipper_code + ", contract_code=" + contract_code + ", start_date="
				+ start_date + ", end_date=" + end_date + ", not_meet=" + not_meet 
				+ ", user=" + user + ", category_code=" + category_code + ", term_code=" + term_code + ", type_code="
				+ type_code + ", idn_pipeline_system=" + idn_pipeline_system + "]";
	}
	
}
