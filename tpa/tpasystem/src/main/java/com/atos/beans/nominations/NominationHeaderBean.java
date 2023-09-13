package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.atos.utils.Constants;

public class NominationHeaderBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1339649428470353456L;

	private BigDecimal idn_nomination;
	private String nomination_code;
	private Date start_date;
	private BigDecimal nomination_version;
	private BigDecimal idn_contract;
	private String contract_code;
	private BigDecimal idn_user_group;
	private String user_group_id;
	private String user_group_name;
	private BigDecimal idn_shipper_file;
	private String shipper_file_name;
	private BigDecimal idn_operator_file;
	private String operator_file_name;
	private String is_valid;
	private String is_responsed;
	private String shipper_comments;
    private String operator_comments;
    private String submission_comments;
    private String is_renomination;
    private Date aud_ins_date;

	private BigDecimal idn_zone; // we change this value to get the different zones

	public NominationHeaderBean(){
		this.idn_nomination = null;
		this.nomination_code = null;
		this.start_date = null;
		this.nomination_version = null;
		this.idn_contract = null;
		this.contract_code = null;
		this.idn_user_group = null;
		this.user_group_id = null;
		this.user_group_name = null;
		this.idn_shipper_file = null;
		this.shipper_file_name = null;
		this.idn_operator_file = null;
		this.operator_file_name = null;
		this.is_valid = null;
		this.is_responsed = null;
		this.shipper_comments = null;
		this.operator_comments = null;
		this.submission_comments = null;
		this.is_renomination = null;
		this.aud_ins_date = null;
		this.idn_zone = null;
	}
	
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

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public BigDecimal getNomination_version() {
		return nomination_version;
	}

	public void setNomination_version(BigDecimal nomination_version) {
		this.nomination_version = nomination_version;
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

	public String getUser_group_name() {
		return user_group_name;
	}

	public void setUser_group_name(String user_group_name) {
		this.user_group_name = user_group_name;
	}

	public BigDecimal getIdn_shipper_file() {
		return idn_shipper_file;
	}

	public void setIdn_shipper_file(BigDecimal idn_shipper_file) {
		this.idn_shipper_file = idn_shipper_file;
	}

	public String getShipper_file_name() {
		return shipper_file_name;
	}

	public void setShipper_file_name(String shipper_file_name) {
		this.shipper_file_name = shipper_file_name;
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

	// CH031 - 05/09/16 - PPM se genera una variable status en funcion de los valores de is_valid e is_responded.
	//	Valid    Respond   STATUS
	//	Y           N     Waiting for Response
	//	Y           Y     Accepted
	//	N           N     Waiting for Response
	//	N           Y     Rejected
	// Esta variable status no existe en BD ni en el bean. Se genera cada vez que se consulta.
    public String getStatus() {

    	String status = null;
    	// Segun restricciones de base de datos:
    	// - is_valid solo puede tener valores "Y" y "N".
    	// - is_responsed solo puede tener valores "Y", "N" y "P". "P" se utiliza en caso de una respuesta en un
    	// 	sistema (ON_SHORE, OFF_SHORE) pendiente de responder en el otro.
    	if("Y".equalsIgnoreCase(is_valid)) {
    		
    		if("N".equalsIgnoreCase(is_responsed))
        		status = Constants.WaitingForResponse;
    		// is_responsed == "P" se toma igual que si fuera "Y".
    		else if("Y".equalsIgnoreCase(is_responsed) || "P".equalsIgnoreCase(is_responsed))
        		status = Constants.Accepted;
    		
    	} else if("N".equalsIgnoreCase(is_valid)) {
    		
    		if("N".equalsIgnoreCase(is_responsed))
        		status = Constants.WaitingForResponse;
    		// is_responsed == "P" se toma igual que si fuera "Y".
    		else if("Y".equalsIgnoreCase(is_responsed) || "P".equalsIgnoreCase(is_responsed))
        		status = Constants.Rejected;    		
    	}
    	
    	return status;    	
	}
    
	public String getShipper_comments() {
		return shipper_comments;
	}

	public void setShipper_comments(String shipper_comments) {
		this.shipper_comments = shipper_comments;
	}

	public String getOperator_comments() {
		return operator_comments;
	}

	public void setOperator_comments(String operator_comments) {
		this.operator_comments = operator_comments;
	}

	public String getSubmission_comments() {
		return submission_comments;
	}

	public void setSubmission_comments(String submission_comments) {
		this.submission_comments = submission_comments;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	public String getIs_renomination() {
		if(this.is_renomination.equals("Y")){
			return "YES";
		} else {
			return "NO";
		}
	}

	public void setIs_renomination(String is_renomination) {
		this.is_renomination = is_renomination;
	}

	public Date getAud_ins_date() {
		return aud_ins_date;
	}

	public void setAud_ins_date(Date aud_ins_date) {
		this.aud_ins_date = aud_ins_date;
	}
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aud_ins_date == null) ? 0 : aud_ins_date.hashCode());
		result = prime * result + ((contract_code == null) ? 0 : contract_code.hashCode());
		result = prime * result + ((idn_contract == null) ? 0 : idn_contract.hashCode());
		result = prime * result + ((idn_nomination == null) ? 0 : idn_nomination.hashCode());
		result = prime * result + ((idn_operator_file == null) ? 0 : idn_operator_file.hashCode());
		result = prime * result + ((idn_shipper_file == null) ? 0 : idn_shipper_file.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((is_renomination == null) ? 0 : is_renomination.hashCode());
		result = prime * result + ((is_responsed == null) ? 0 : is_responsed.hashCode());
		result = prime * result + ((is_valid == null) ? 0 : is_valid.hashCode());
		result = prime * result + ((nomination_code == null) ? 0 : nomination_code.hashCode());
		result = prime * result + ((nomination_version == null) ? 0 : nomination_version.hashCode());
		result = prime * result + ((operator_comments == null) ? 0 : operator_comments.hashCode());
		result = prime * result + ((operator_file_name == null) ? 0 : operator_file_name.hashCode());
		result = prime * result + ((shipper_comments == null) ? 0 : shipper_comments.hashCode());
		result = prime * result + ((shipper_file_name == null) ? 0 : shipper_file_name.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((submission_comments == null) ? 0 : submission_comments.hashCode());
		result = prime * result + ((user_group_id == null) ? 0 : user_group_id.hashCode());
		result = prime * result + ((user_group_name == null) ? 0 : user_group_name.hashCode());
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
		NominationHeaderBean other = (NominationHeaderBean) obj;
		if (aud_ins_date == null) {
			if (other.aud_ins_date != null)
				return false;
		} else if (!aud_ins_date.equals(other.aud_ins_date))
			return false;
		if (contract_code == null) {
			if (other.contract_code != null)
				return false;
		} else if (!contract_code.equals(other.contract_code))
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
		if (idn_operator_file == null) {
			if (other.idn_operator_file != null)
				return false;
		} else if (!idn_operator_file.equals(other.idn_operator_file))
			return false;
		if (idn_shipper_file == null) {
			if (other.idn_shipper_file != null)
				return false;
		} else if (!idn_shipper_file.equals(other.idn_shipper_file))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
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
		if (shipper_comments == null) {
			if (other.shipper_comments != null)
				return false;
		} else if (!shipper_comments.equals(other.shipper_comments))
			return false;
		if (shipper_file_name == null) {
			if (other.shipper_file_name != null)
				return false;
		} else if (!shipper_file_name.equals(other.shipper_file_name))
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
		if (user_group_name == null) {
			if (other.user_group_name != null)
				return false;
		} else if (!user_group_name.equals(other.user_group_name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NominationHeaderBean [idn_nomination=" + idn_nomination + ", nomination_code=" + nomination_code
				+ ", start_date=" + start_date + ", nomination_version=" + nomination_version + ", idn_contract="
				+ idn_contract + ", contract_code=" + contract_code + ", idn_user_group=" + idn_user_group
				+ ", user_group_id=" + user_group_id + ", user_group_name=" + user_group_name + ", idn_shipper_file="
				+ idn_shipper_file + ", shipper_file_name=" + shipper_file_name + ", idn_operator_file="
				+ idn_operator_file + ", operator_file_name=" + operator_file_name + ", is_valid=" + is_valid
				+ ", is_responsed=" + is_responsed + ", shipper_comments=" + shipper_comments + ", operator_comments="
				+ operator_comments + ", submission_comments=" + submission_comments + ", is_renomination="
				+ is_renomination + ", aud_ins_date=" + aud_ins_date + ", idn_zone=" + idn_zone + "]";
	}

    
}
