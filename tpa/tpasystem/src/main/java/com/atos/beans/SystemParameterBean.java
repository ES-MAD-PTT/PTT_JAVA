package com.atos.beans;

import java.io.Serializable;
import java.util.Date;

public class SystemParameterBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4941704143942246597L;
	
	private Integer integer_exit;
	private String string_exit;
	private Float float_exit;
	private Date date_exit;
	private String parameter_name;
	private Date date;
	private String user_id;
	private String language;
	private Integer error_code;
	private String error_desc;
	public Integer getInteger_exit() {
		return integer_exit;
	}
	public void setInteger_exit(Integer integer_exit) {
		this.integer_exit = integer_exit;
	}
	public String getString_exit() {
		return string_exit;
	}
	public void setString_exit(String string_exit) {
		this.string_exit = string_exit;
	}
	public Float getFloat_exit() {
		return float_exit;
	}
	public void setFloat_exit(Float float_exit) {
		this.float_exit = float_exit;
	}
	public Date getDate_exit() {
		return date_exit;
	}
	public void setDate_exit(Date date_exit) {
		this.date_exit = date_exit;
	}
	public String getParameter_name() {
		return parameter_name;
	}
	public void setParameter_name(String parameter_name) {
		this.parameter_name = parameter_name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Integer getError_code() {
		return error_code;
	}
	public void setError_code(Integer error_code) {
		this.error_code = error_code;
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
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((date_exit == null) ? 0 : date_exit.hashCode());
		result = prime * result + ((error_code == null) ? 0 : error_code.hashCode());
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((float_exit == null) ? 0 : float_exit.hashCode());
		result = prime * result + ((integer_exit == null) ? 0 : integer_exit.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((parameter_name == null) ? 0 : parameter_name.hashCode());
		result = prime * result + ((string_exit == null) ? 0 : string_exit.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
		SystemParameterBean other = (SystemParameterBean) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (date_exit == null) {
			if (other.date_exit != null)
				return false;
		} else if (!date_exit.equals(other.date_exit))
			return false;
		if (error_code == null) {
			if (other.error_code != null)
				return false;
		} else if (!error_code.equals(other.error_code))
			return false;
		if (error_desc == null) {
			if (other.error_desc != null)
				return false;
		} else if (!error_desc.equals(other.error_desc))
			return false;
		if (float_exit == null) {
			if (other.float_exit != null)
				return false;
		} else if (!float_exit.equals(other.float_exit))
			return false;
		if (integer_exit == null) {
			if (other.integer_exit != null)
				return false;
		} else if (!integer_exit.equals(other.integer_exit))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (parameter_name == null) {
			if (other.parameter_name != null)
				return false;
		} else if (!parameter_name.equals(other.parameter_name))
			return false;
		if (string_exit == null) {
			if (other.string_exit != null)
				return false;
		} else if (!string_exit.equals(other.string_exit))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SystemParameterBean [integer_exit=" + integer_exit + ", string_exit=" + string_exit + ", float_exit="
				+ float_exit + ", date_exit=" + date_exit + ", parameter_name=" + parameter_name + ", date=" + date
				+ ", user_id=" + user_id + ", language=" + language + ", error_code=" + error_code + ", error_desc="
				+ error_desc + "]";
	}
	

}
