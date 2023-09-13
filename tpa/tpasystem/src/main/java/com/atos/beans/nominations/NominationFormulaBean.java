package com.atos.beans.nominations;

import java.io.Serializable;

public class NominationFormulaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8033291392732656437L;

	private String concept_code;
	private String parameter_code;
	private String used_point;
	public String getConcept_code() {
		return concept_code;
	}
	public void setConcept_code(String concept_code) {
		this.concept_code = concept_code;
	}
	public String getParameter_code() {
		return parameter_code;
	}
	public void setParameter_code(String parameter_code) {
		this.parameter_code = parameter_code;
	}
	public String getUsed_point() {
		return used_point;
	}
	public void setUsed_point(String used_point) {
		this.used_point = used_point;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concept_code == null) ? 0 : concept_code.hashCode());
		result = prime * result + ((parameter_code == null) ? 0 : parameter_code.hashCode());
		result = prime * result + ((used_point == null) ? 0 : used_point.hashCode());
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
		NominationFormulaBean other = (NominationFormulaBean) obj;
		if (concept_code == null) {
			if (other.concept_code != null)
				return false;
		} else if (!concept_code.equals(other.concept_code))
			return false;
		if (parameter_code == null) {
			if (other.parameter_code != null)
				return false;
		} else if (!parameter_code.equals(other.parameter_code))
			return false;
		if (used_point == null) {
			if (other.used_point != null)
				return false;
		} else if (!used_point.equals(other.used_point))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NominationFormulaBean [concept_code=" + concept_code + ", parameter_code=" + parameter_code
				+ ", used_point=" + used_point + "]";
	}

	
}
