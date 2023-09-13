package com.atos.beans;

import java.io.Serializable;
import java.util.Date;

public class ValidateDeadlineBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8567029581425836313L;

	private int valid;
	private String category_code;
	private String term_code;
	private String deadline_type;
	private Date reference_date;
	private String user;
	private String language;
	private String error_desc;
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
	public String getDeadline_type() {
		return deadline_type;
	}
	public void setDeadline_type(String deadline_type) {
		this.deadline_type = deadline_type;
	}
	public Date getReference_date() {
		return reference_date;
	}
	public void setReference_date(Date reference_date) {
		this.reference_date = reference_date;
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
		result = prime * result + ((deadline_type == null) ? 0 : deadline_type.hashCode());
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((reference_date == null) ? 0 : reference_date.hashCode());
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
		ValidateDeadlineBean other = (ValidateDeadlineBean) obj;
		if (category_code == null) {
			if (other.category_code != null)
				return false;
		} else if (!category_code.equals(other.category_code))
			return false;
		if (deadline_type == null) {
			if (other.deadline_type != null)
				return false;
		} else if (!deadline_type.equals(other.deadline_type))
			return false;
		if (error_desc == null) {
			if (other.error_desc != null)
				return false;
		} else if (!error_desc.equals(other.error_desc))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (reference_date == null) {
			if (other.reference_date != null)
				return false;
		} else if (!reference_date.equals(other.reference_date))
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
		return "ValidateDeadlineBean [valid=" + valid + ", category_code=" + category_code + ", term_code=" + term_code
				+ ", deadline_type=" + deadline_type + ", reference_date=" + reference_date + ", user=" + user
				+ ", language=" + language + ", error_desc=" + error_desc + "]";
	}
	
	
}
