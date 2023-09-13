package com.atos.beans;

import java.io.Serializable;
import java.util.Date;

public class ValidateIntervalBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8253565974975165984L;
	
	private int valid;
	private String category_code;
	private String term_code;
	private Date interval_start;
	private Date interval_end;
	private String user;
	private String language;
	private String error_desc;
	
	private Date min_end_date;
	private Date max_end_date;
		
	public Date getMin_end_date() {
		return min_end_date;
	}
	public void setMin_end_date(Date min_end_date) {
		this.min_end_date = min_end_date;
	}
	public Date getMax_end_date() {
		return max_end_date;
	}
	public void setMax_end_date(Date max_end_date) {
		this.max_end_date = max_end_date;
	}
	
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
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
	public Date getInterval_start() {
		return interval_start;
	}
	public void setInterval_start(Date interval_start) {
		this.interval_start = interval_start;
	}
	public Date getInterval_end() {
		return interval_end;
	}
	public void setInterval_end(Date interval_end) {
		this.interval_end = interval_end;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getError_desc() {
		return error_desc;
	}
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_code == null) ? 0 : category_code.hashCode());
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((interval_end == null) ? 0 : interval_end.hashCode());
		result = prime * result + ((interval_start == null) ? 0 : interval_start.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((max_end_date == null) ? 0 : max_end_date.hashCode());
		result = prime * result + ((min_end_date == null) ? 0 : min_end_date.hashCode());
		result = prime * result + ((term_code == null) ? 0 : term_code.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + valid;
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
		ValidateIntervalBean other = (ValidateIntervalBean) obj;
		if (category_code == null) {
			if (other.category_code != null)
				return false;
		} else if (!category_code.equals(other.category_code))
			return false;
		if (error_desc == null) {
			if (other.error_desc != null)
				return false;
		} else if (!error_desc.equals(other.error_desc))
			return false;
		if (interval_end == null) {
			if (other.interval_end != null)
				return false;
		} else if (!interval_end.equals(other.interval_end))
			return false;
		if (interval_start == null) {
			if (other.interval_start != null)
				return false;
		} else if (!interval_start.equals(other.interval_start))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (max_end_date == null) {
			if (other.max_end_date != null)
				return false;
		} else if (!max_end_date.equals(other.max_end_date))
			return false;
		if (min_end_date == null) {
			if (other.min_end_date != null)
				return false;
		} else if (!min_end_date.equals(other.min_end_date))
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
		if (valid != other.valid)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ValidateIntervalBean [valid=" + valid + ", category_code=" + category_code + ", term_code=" + term_code
				+ ", interval_start=" + interval_start + ", interval_end=" + interval_end + ", user=" + user
				+ ", language=" + language + ", error_desc=" + error_desc + ", min_end_date=" + min_end_date
				+ ", max_end_date=" + max_end_date + "]";
	}


}
