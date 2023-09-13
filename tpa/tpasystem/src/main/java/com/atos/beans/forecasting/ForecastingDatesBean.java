package com.atos.beans.forecasting;

import java.io.Serializable;
import java.util.Date;

public class ForecastingDatesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4155300700492065432L;
	private String term_code;
	private Date valid_date;
	private String user;
	private String language;
	private Date submission_deadline;
	private Date start_date;
	private Date end_date;
	private String error_desc;
	private int valid;
	
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	public Date getValid_date() {
		return valid_date;
	}
	public void setValid_date(Date valid_date) {
		this.valid_date = valid_date;
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
	public Date getSubmission_deadline() {
		return submission_deadline;
	}
	public void setSubmission_deadline(Date submission_deadline) {
		this.submission_deadline = submission_deadline;
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
	public String getError_desc() {
		return error_desc;
	}
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ForecastingDatesBean [term_code=");
		builder.append(term_code);
		builder.append(", valid_date=");
		builder.append(valid_date);
		builder.append(", user=");
		builder.append(user);
		builder.append(", language=");
		builder.append(language);
		builder.append(", submission_deadline=");
		builder.append(submission_deadline);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", error_desc=");
		builder.append(error_desc);
		builder.append(", valid=");
		builder.append(valid);
		builder.append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((submission_deadline == null) ? 0 : submission_deadline.hashCode());
		result = prime * result + ((term_code == null) ? 0 : term_code.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + valid;
		result = prime * result + ((valid_date == null) ? 0 : valid_date.hashCode());
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
		ForecastingDatesBean other = (ForecastingDatesBean) obj;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
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
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (submission_deadline == null) {
			if (other.submission_deadline != null)
				return false;
		} else if (!submission_deadline.equals(other.submission_deadline))
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
		if (valid_date == null) {
			if (other.valid_date != null)
				return false;
		} else if (!valid_date.equals(other.valid_date))
			return false;
		return true;
	}
	
}
