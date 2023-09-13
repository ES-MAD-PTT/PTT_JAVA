package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class QueryShipperNominationFileFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2300744601553291995L;

	private String shipper_code;
	private Date start_date;
	private Date end_date;
	private String contract_code;
	private String user;
	private String category_code;
	private String term_code;
	private BigDecimal systemId;
	
	public QueryShipperNominationFileFilter(){
		this.shipper_code = null;
		this.start_date = null;
		this.end_date = null;
		this.contract_code = null;
		this.user = null;
		this.category_code = null;
		this.term_code = null;
		this.systemId = null;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		if(start_date!=null){
			this.start_date = start_date;
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.start_date);
			cal.add(Calendar.DAY_OF_MONTH, 6);
			this.setEnd_date(cal.getTime());
		} else {
			this.setEnd_date(null);
		}
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
	public BigDecimal getSystemId() {
		return systemId;
	}
	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_code == null) ? 0 : category_code.hashCode());
		result = prime * result + ((contract_code == null) ? 0 : contract_code.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((shipper_code == null) ? 0 : shipper_code.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
		result = prime * result + ((term_code == null) ? 0 : term_code.hashCode());
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
		QueryShipperNominationFileFilter other = (QueryShipperNominationFileFilter) obj;
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
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		if (term_code == null) {
			if (other.term_code != null)
				return false;
		} else if (!term_code.equals(other.term_code))
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
		return "QueryShipperNominationFileFilter [shipper_code=" + shipper_code + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", contract_code=" + contract_code + ", user=" + user + ", category_code="
				+ category_code + ", term_code=" + term_code + ", systemId=" + systemId + "]";
	}	
}
