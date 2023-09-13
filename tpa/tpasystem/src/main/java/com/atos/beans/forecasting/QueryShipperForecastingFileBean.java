package com.atos.beans.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QueryShipperForecastingFileBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7273786045350418930L;
	
	private BigDecimal idn_forecasting;
	private String forecasting_code;
	private BigDecimal idn_operation;
	private BigDecimal idn_user_group;
	private String user_group_id;
	private String term_code;
	private Date start_date;
	private Date end_date;
	private BigDecimal idn_shipper_file;
	private String shipper_file_name;
	private BigDecimal idn_operator_file;
	private String operator_file_name;
	private Date shipper_file_date;
	private Date operator_file_date;

	
	public QueryShipperForecastingFileBean() {
		super();
		this.idn_forecasting = null;
		this.forecasting_code = null;
		this.idn_operation = null;
		this.idn_user_group = null;
		this.user_group_id = null;
		this.term_code = null;
		this.start_date = null;
		this.end_date = null;
		this.idn_shipper_file = null;
		this.shipper_file_name = null;
		this.idn_operator_file = null;
		this.operator_file_name = null;
		this.shipper_file_date = null;
		this.operator_file_date = null;
	}
	
	public BigDecimal getIdn_forecasting() {
		return idn_forecasting;
	}
	public void setIdn_forecasting(BigDecimal idn_forecasting) {
		this.idn_forecasting = idn_forecasting;
	}
	public String getForecasting_code() {
		return forecasting_code;
	}
	public void setForecasting_code(String forecasting_code) {
		this.forecasting_code = forecasting_code;
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
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
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
	public Date getShipper_file_date() {
		return shipper_file_date;
	}
	public void setShipper_file_date(Date shipper_file_date) {
		this.shipper_file_date = shipper_file_date;
	}
	public Date getOperator_file_date() {
		return operator_file_date;
	}
	public void setOperator_file_date(Date operator_file_date) {
		this.operator_file_date = operator_file_date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((idn_operation == null) ? 0 : idn_operation.hashCode());
		result = prime * result + ((idn_operator_file == null) ? 0 : idn_operator_file.hashCode());
		result = prime * result + ((idn_shipper_file == null) ? 0 : idn_shipper_file.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((operator_file_date == null) ? 0 : operator_file_date.hashCode());
		result = prime * result + ((operator_file_name == null) ? 0 : operator_file_name.hashCode());
		result = prime * result + ((shipper_file_date == null) ? 0 : shipper_file_date.hashCode());
		result = prime * result + ((shipper_file_name == null) ? 0 : shipper_file_name.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((term_code == null) ? 0 : term_code.hashCode());
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
		QueryShipperForecastingFileBean other = (QueryShipperForecastingFileBean) obj;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
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
		if (operator_file_date == null) {
			if (other.operator_file_date != null)
				return false;
		} else if (!operator_file_date.equals(other.operator_file_date))
			return false;
		if (operator_file_name == null) {
			if (other.operator_file_name != null)
				return false;
		} else if (!operator_file_name.equals(other.operator_file_name))
			return false;
		if (shipper_file_date == null) {
			if (other.shipper_file_date != null)
				return false;
		} else if (!shipper_file_date.equals(other.shipper_file_date))
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
		if (term_code == null) {
			if (other.term_code != null)
				return false;
		} else if (!term_code.equals(other.term_code))
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
		return "QueryShipperForecastingFileBean [idn_operation=" + idn_operation + ", idn_user_group=" + idn_user_group
				+ ", user_group_id=" + user_group_id + ", term_code=" + term_code + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", idn_shipper_file=" + idn_shipper_file + ", shipper_file_name="
				+ shipper_file_name + ", idn_operator_file=" + idn_operator_file + ", operator_file_name="
				+ operator_file_name + ", shipper_file_date=" + shipper_file_date + ", operator_file_date="
				+ operator_file_date + "]";
	}

}
