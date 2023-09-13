package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProcessIntermediateBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8323458460426083510L;

	private Integer integer_exit;
	private String term_code;
	private BigDecimal idn_operation_file;
	private BigDecimal systemId;
	private BigDecimal idn_nomination;
	private String shipper_comments;
	private String user_id;
	private String language;
	private String warnings;
	private String error_desc;
	public ProcessIntermediateBean(){
		this.integer_exit = null;
		this.term_code = null;
		this.idn_operation_file = null;
		this.systemId = null;
		this.idn_nomination = null;
		this.shipper_comments = null;
		this.user_id = null;
		this.language = null;
		this.warnings = null;
		this.error_desc = null;
	}
	public Integer getInteger_exit() {
		return integer_exit;
	}
	public void setInteger_exit(Integer integer_exit) {
		this.integer_exit = integer_exit;
	}
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	public BigDecimal getIdn_operation_file() {
		return idn_operation_file;
	}
	public void setIdn_operation_file(BigDecimal idn_operation_file) {
		this.idn_operation_file = idn_operation_file;
	}
	public BigDecimal getSystemId() {
		return systemId;
	}
	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}
	public String getShipper_comments() {
		return shipper_comments;
	}
	public void setShipper_comments(String shipper_comments) {
		this.shipper_comments = shipper_comments;
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
	public String getWarnings() {
		return warnings;
	}
	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}
	public String getError_desc() {
		return error_desc;
	}
	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}
	public BigDecimal getIdn_nomination() {
		return idn_nomination;
	}
	public void setIdn_nomination(BigDecimal idn_nomination) {
		this.idn_nomination = idn_nomination;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((idn_nomination == null) ? 0 : idn_nomination.hashCode());
		result = prime * result + ((idn_operation_file == null) ? 0 : idn_operation_file.hashCode());
		result = prime * result + ((integer_exit == null) ? 0 : integer_exit.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((shipper_comments == null) ? 0 : shipper_comments.hashCode());
		result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
		result = prime * result + ((term_code == null) ? 0 : term_code.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		result = prime * result + ((warnings == null) ? 0 : warnings.hashCode());
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
		ProcessIntermediateBean other = (ProcessIntermediateBean) obj;
		if (error_desc == null) {
			if (other.error_desc != null)
				return false;
		} else if (!error_desc.equals(other.error_desc))
			return false;
		if (idn_nomination == null) {
			if (other.idn_nomination != null)
				return false;
		} else if (!idn_nomination.equals(other.idn_nomination))
			return false;
		if (idn_operation_file == null) {
			if (other.idn_operation_file != null)
				return false;
		} else if (!idn_operation_file.equals(other.idn_operation_file))
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
		if (shipper_comments == null) {
			if (other.shipper_comments != null)
				return false;
		} else if (!shipper_comments.equals(other.shipper_comments))
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
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		if (warnings == null) {
			if (other.warnings != null)
				return false;
		} else if (!warnings.equals(other.warnings))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProcessIntermediateBean [integer_exit=" + integer_exit + ", term_code=" + term_code
				+ ", idn_operation_file=" + idn_operation_file + ", systemId=" + systemId + ", idn_nomination="
				+ idn_nomination + ", shipper_comments=" + shipper_comments + ", user_id=" + user_id + ", language="
				+ language + ", warnings=" + warnings + ", error_desc=" + error_desc + "]";
	}
	
}
