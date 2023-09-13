package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class PimsGraphicMenuBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6173267354263864183L;
	
	private BigDecimal idn_parameter;
	private BigDecimal idn_parameter_value;
	private BigDecimal idn_parameter_module;
	private String parameter_desc;
	private String parameter_value;
	private Date startDate;
	private Date endDate;
	private String parameter_code;

	private int valid;
	private String user;
	private String language;
	private String error_desc;

	public PimsGraphicMenuBean() {
		super();
	}

	public PimsGraphicMenuBean(BigDecimal idn_parameter, BigDecimal idn_parameter_value, String parameter_desc,
			String parameter_value, Date startDate, Date endDate, String parameter_code, int valid, String user,
			String language, String error_desc) {
		super();
		this.idn_parameter = idn_parameter;
		this.idn_parameter_value = idn_parameter_value;
		this.parameter_desc = parameter_desc;
		this.parameter_value = parameter_value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parameter_code = parameter_code;
		this.valid = valid;
		this.user = user;
		this.language = language;
		this.error_desc = error_desc;
	}

	public BigDecimal getIdn_parameter() {
		return idn_parameter;
	}

	public void setIdn_parameter(BigDecimal idn_parameter) {
		this.idn_parameter = idn_parameter;
	}

	public BigDecimal getIdn_parameter_value() {
		return idn_parameter_value;
	}

	public void setIdn_parameter_value(BigDecimal idn_parameter_value) {
		this.idn_parameter_value = idn_parameter_value;
	}

	public BigDecimal getIdn_parameter_module() {
		return idn_parameter_module;
	}

	public void setIdn_parameter_module(BigDecimal idn_parameter_module) {
		this.idn_parameter_module = idn_parameter_module;
	}

	public String getParameter_desc() {
		return parameter_desc;
	}

	public void setParameter_desc(String parameter_desc) {
		this.parameter_desc = parameter_desc;
	}

	public String getParameter_value() {
		return parameter_value;
	}

	public void setParameter_value(String parameter_value) {
		this.parameter_value = parameter_value;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getParameter_code() {
		return parameter_code;
	}

	public void setParameter_code(String parameter_code) {
		this.parameter_code = parameter_code;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
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
	public String toString() {
		return "PimsGraphicMenuBean [idn_parameter=" + idn_parameter + ", idn_parameter_value=" + idn_parameter_value
				+ ", idn_parameter_module=" + idn_parameter_module + ", parameter_desc=" + parameter_desc
				+ ", parameter_value=" + parameter_value + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", parameter_code=" + parameter_code + ", valid=" + valid + ", user=" + user + ", language="
				+ language + ", error_desc=" + error_desc + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((idn_parameter == null) ? 0 : idn_parameter.hashCode());
		result = prime * result + ((idn_parameter_module == null) ? 0 : idn_parameter_module.hashCode());
		result = prime * result + ((idn_parameter_value == null) ? 0 : idn_parameter_value.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((parameter_code == null) ? 0 : parameter_code.hashCode());
		result = prime * result + ((parameter_desc == null) ? 0 : parameter_desc.hashCode());
		result = prime * result + ((parameter_value == null) ? 0 : parameter_value.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		PimsGraphicMenuBean other = (PimsGraphicMenuBean) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (error_desc == null) {
			if (other.error_desc != null)
				return false;
		} else if (!error_desc.equals(other.error_desc))
			return false;
		if (idn_parameter == null) {
			if (other.idn_parameter != null)
				return false;
		} else if (!idn_parameter.equals(other.idn_parameter))
			return false;
		if (idn_parameter_module == null) {
			if (other.idn_parameter_module != null)
				return false;
		} else if (!idn_parameter_module.equals(other.idn_parameter_module))
			return false;
		if (idn_parameter_value == null) {
			if (other.idn_parameter_value != null)
				return false;
		} else if (!idn_parameter_value.equals(other.idn_parameter_value))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (parameter_code == null) {
			if (other.parameter_code != null)
				return false;
		} else if (!parameter_code.equals(other.parameter_code))
			return false;
		if (parameter_desc == null) {
			if (other.parameter_desc != null)
				return false;
		} else if (!parameter_desc.equals(other.parameter_desc))
			return false;
		if (parameter_value == null) {
			if (other.parameter_value != null)
				return false;
		} else if (!parameter_value.equals(other.parameter_value))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
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

}
