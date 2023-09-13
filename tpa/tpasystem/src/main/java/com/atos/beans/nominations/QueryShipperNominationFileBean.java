package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QueryShipperNominationFileBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 794445512368821925L;

	private BigDecimal idn_nomination;
	private String nomination_code;
	private BigDecimal nomination_version;
	private BigDecimal idn_operation;
	private BigDecimal idn_user_group;
	private String user_group_id;
	private BigDecimal idn_contract;
	private String contract_code;
	private String is_contracted;
	private Date start_date;
	private Date end_date;
	private String is_renomination;
	private String is_valid;
	private String is_responsed;
	private Date feasibility_date;
	private String operator_comments;
	private String is_matched;
	private Date matching_date;
	private String submission_comments;
	private BigDecimal idn_operator_file;
	private String operator_file_name;
	public BigDecimal getIdn_nomination() {
		return idn_nomination;
	}
	public void setIdn_nomination(BigDecimal idn_nomination) {
		this.idn_nomination = idn_nomination;
	}
	public String getNomination_code() {
		return nomination_code;
	}
	public void setNomination_code(String nomination_code) {
		this.nomination_code = nomination_code;
	}
	public BigDecimal getNomination_version() {
		return nomination_version;
	}
	public void setNomination_version(BigDecimal nomination_version) {
		this.nomination_version = nomination_version;
	}
	public BigDecimal getIdn_operation() {
		return idn_operation;
	}
	public void setIdn_operation(BigDecimal idn_operation) {
		this.idn_operation = idn_operation;
	}
	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}
	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}
	public String getUser_group_id() {
		return user_group_id;
	}
	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}
	public BigDecimal getIdn_contract() {
		return idn_contract;
	}
	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getIs_contracted() {
		return is_contracted;
	}
	public void setIs_contracted(String is_contracted) {
		this.is_contracted = is_contracted;
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
	public String getIs_renomination() {
		return is_renomination;
	}
	public void setIs_renomination(String is_renomination) {
		this.is_renomination = is_renomination;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getIs_responsed() {
		return is_responsed;
	}
	public void setIs_responsed(String is_responsed) {
		this.is_responsed = is_responsed;
	}
	public Date getFeasibility_date() {
		return feasibility_date;
	}
	public void setFeasibility_date(Date feasibility_date) {
		this.feasibility_date = feasibility_date;
	}
	public String getOperator_comments() {
		return operator_comments;
	}
	public void setOperator_comments(String operator_comments) {
		this.operator_comments = operator_comments;
	}
	public String getIs_matched() {
		return is_matched;
	}
	public void setIs_matched(String is_matched) {
		this.is_matched = is_matched;
	}
	public Date getMatching_date() {
		return matching_date;
	}
	public void setMatching_date(Date matching_date) {
		this.matching_date = matching_date;
	}
	public String getSubmission_comments() {
		return submission_comments;
	}
	public void setSubmission_comments(String submission_comments) {
		this.submission_comments = submission_comments;
	}
	public BigDecimal getIdn_operator_file() {
		return idn_operator_file;
	}
	public void setIdn_operator_file(BigDecimal idn_operator_file) {
		this.idn_operator_file = idn_operator_file;
	}
	public String getOperator_file_name() {
		return operator_file_name;
	}
	public void setOperator_file_name(String operator_file_name) {
		this.operator_file_name = operator_file_name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contract_code == null) ? 0 : contract_code.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((feasibility_date == null) ? 0 : feasibility_date.hashCode());
		result = prime * result + ((idn_contract == null) ? 0 : idn_contract.hashCode());
		result = prime * result + ((idn_nomination == null) ? 0 : idn_nomination.hashCode());
		result = prime * result + ((idn_operation == null) ? 0 : idn_operation.hashCode());
		result = prime * result + ((idn_operator_file == null) ? 0 : idn_operator_file.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((is_contracted == null) ? 0 : is_contracted.hashCode());
		result = prime * result + ((is_matched == null) ? 0 : is_matched.hashCode());
		result = prime * result + ((is_renomination == null) ? 0 : is_renomination.hashCode());
		result = prime * result + ((is_responsed == null) ? 0 : is_responsed.hashCode());
		result = prime * result + ((is_valid == null) ? 0 : is_valid.hashCode());
		result = prime * result + ((matching_date == null) ? 0 : matching_date.hashCode());
		result = prime * result + ((nomination_code == null) ? 0 : nomination_code.hashCode());
		result = prime * result + ((nomination_version == null) ? 0 : nomination_version.hashCode());
		result = prime * result + ((operator_comments == null) ? 0 : operator_comments.hashCode());
		result = prime * result + ((operator_file_name == null) ? 0 : operator_file_name.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((submission_comments == null) ? 0 : submission_comments.hashCode());
		result = prime * result + ((user_group_id == null) ? 0 : user_group_id.hashCode());
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
		QueryShipperNominationFileBean other = (QueryShipperNominationFileBean) obj;
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
		if (feasibility_date == null) {
			if (other.feasibility_date != null)
				return false;
		} else if (!feasibility_date.equals(other.feasibility_date))
			return false;
		if (idn_contract == null) {
			if (other.idn_contract != null)
				return false;
		} else if (!idn_contract.equals(other.idn_contract))
			return false;
		if (idn_nomination == null) {
			if (other.idn_nomination != null)
				return false;
		} else if (!idn_nomination.equals(other.idn_nomination))
			return false;
		if (idn_operation == null) {
			if (other.idn_operation != null)
				return false;
		} else if (!idn_operation.equals(other.idn_operation))
			return false;
		if (idn_operator_file == null) {
			if (other.idn_operator_file != null)
				return false;
		} else if (!idn_operator_file.equals(other.idn_operator_file))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (is_contracted == null) {
			if (other.is_contracted != null)
				return false;
		} else if (!is_contracted.equals(other.is_contracted))
			return false;
		if (is_matched == null) {
			if (other.is_matched != null)
				return false;
		} else if (!is_matched.equals(other.is_matched))
			return false;
		if (is_renomination == null) {
			if (other.is_renomination != null)
				return false;
		} else if (!is_renomination.equals(other.is_renomination))
			return false;
		if (is_responsed == null) {
			if (other.is_responsed != null)
				return false;
		} else if (!is_responsed.equals(other.is_responsed))
			return false;
		if (is_valid == null) {
			if (other.is_valid != null)
				return false;
		} else if (!is_valid.equals(other.is_valid))
			return false;
		if (matching_date == null) {
			if (other.matching_date != null)
				return false;
		} else if (!matching_date.equals(other.matching_date))
			return false;
		if (nomination_code == null) {
			if (other.nomination_code != null)
				return false;
		} else if (!nomination_code.equals(other.nomination_code))
			return false;
		if (nomination_version == null) {
			if (other.nomination_version != null)
				return false;
		} else if (!nomination_version.equals(other.nomination_version))
			return false;
		if (operator_comments == null) {
			if (other.operator_comments != null)
				return false;
		} else if (!operator_comments.equals(other.operator_comments))
			return false;
		if (operator_file_name == null) {
			if (other.operator_file_name != null)
				return false;
		} else if (!operator_file_name.equals(other.operator_file_name))
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (submission_comments == null) {
			if (other.submission_comments != null)
				return false;
		} else if (!submission_comments.equals(other.submission_comments))
			return false;
		if (user_group_id == null) {
			if (other.user_group_id != null)
				return false;
		} else if (!user_group_id.equals(other.user_group_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QueryShipperNominationFileBean [idn_nomination=" + idn_nomination + ", nomination_code="
				+ nomination_code + ", nomination_version=" + nomination_version + ", idn_operation=" + idn_operation
				+ ", idn_user_group=" + idn_user_group + ", user_group_id=" + user_group_id + ", idn_contract="
				+ idn_contract + ", contract_code=" + contract_code + ", is_contracted=" + is_contracted
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", is_renomination=" + is_renomination
				+ ", is_valid=" + is_valid + ", is_responsed=" + is_responsed + ", feasibility_date=" + feasibility_date
				+ ", operator_comments=" + operator_comments + ", is_matched=" + is_matched + ", matching_date="
				+ matching_date + ", submission_comments=" + submission_comments + ", idn_operator_file="
				+ idn_operator_file + ", operator_file_name=" + operator_file_name + "]";
	}


}
