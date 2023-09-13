package com.atos.beans.forecasting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ForecastingBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2713605564914923004L;
	/**
	 * 
	 */
	private BigDecimal idn_forecasting;
	private String forecasting_code;
	private BigDecimal idn_operation;
	private BigDecimal idn_user_group;
	private Date start_date;
	private Date end_date;
	private Date version_date;
	private BigDecimal idn_shipper_file;
	private BigDecimal idn_operator_file;
	private BigDecimal idnSystem;

		
	public ForecastingBean() {
		super();
		this.idn_forecasting = null;
		this.forecasting_code = null;
		this.idn_operation = null;
		this.idn_user_group = null;
		this.start_date = null;
		this.end_date = null;
		this.version_date = null;
		this.idn_shipper_file = null;
		this.idn_operator_file = null;
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
	public Date getVersion_date() {
		return version_date;
	}
	public void setVersion_date(Date version_date) {
		this.version_date = version_date;
	}
	public BigDecimal getIdn_shipper_file() {
		return idn_shipper_file;
	}
	public void setIdn_shipper_file(BigDecimal idn_shipper_file) {
		this.idn_shipper_file = idn_shipper_file;
	}
	public BigDecimal getIdn_operator_file() {
		return idn_operator_file;
	}
	public void setIdn_operator_file(BigDecimal idn_operator_file) {
		this.idn_operator_file = idn_operator_file;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((forecasting_code == null) ? 0 : forecasting_code.hashCode());
		result = prime * result + ((idn_forecasting == null) ? 0 : idn_forecasting.hashCode());
		result = prime * result + ((idn_operation == null) ? 0 : idn_operation.hashCode());
		result = prime * result + ((idn_operator_file == null) ? 0 : idn_operator_file.hashCode());
		result = prime * result + ((idn_shipper_file == null) ? 0 : idn_shipper_file.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((version_date == null) ? 0 : version_date.hashCode());
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
		ForecastingBean other = (ForecastingBean) obj;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (forecasting_code == null) {
			if (other.forecasting_code != null)
				return false;
		} else if (!forecasting_code.equals(other.forecasting_code))
			return false;
		if (idn_forecasting == null) {
			if (other.idn_forecasting != null)
				return false;
		} else if (!idn_forecasting.equals(other.idn_forecasting))
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
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (version_date == null) {
			if (other.version_date != null)
				return false;
		} else if (!version_date.equals(other.version_date))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ForecastingBean [idn_forecasting=" + idn_forecasting + ", forecasting_code=" + forecasting_code
				+ ", idn_operation=" + idn_operation + ", idn_user_group=" + idn_user_group + ", start_date="
				+ start_date + ", end_date=" + end_date + ", version_date=" + version_date + ", idn_shipper_file="
				+ idn_shipper_file + ", idn_operator_file=" + idn_operator_file + "]";
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

}
