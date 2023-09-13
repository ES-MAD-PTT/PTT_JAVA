package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParkingAllocationFormBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5639016820382100878L;

	private Date gas_day;
	private BigDecimal idn_zone;
	private String zone_code;
	private BigDecimal parking_value;
	private BigDecimal default_value;
	private BigDecimal last_value;
	
	
	private int valid;
	private String user;
	private String language;
	private String error_desc;
	
	public Date getGas_day() {
		return gas_day;
	}
	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}
	public BigDecimal getIdn_zone() {
		return idn_zone;
	}
	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}
	public String getZone_code() {
		return zone_code;
	}
	public void setZone_code(String zone_code) {
		this.zone_code = zone_code;
	}
	public BigDecimal getParking_value() {
		return parking_value;
	}
	public void setParking_value(BigDecimal parking_value) {
		this.parking_value = parking_value;
	}
	public BigDecimal getDefault_value() {
		return default_value;
	}
	public void setDefault_value(BigDecimal default_value) {
		this.default_value = default_value;
	}
	
	
	public BigDecimal getLast_value() {
		return last_value;
	}
	public void setLast_value(BigDecimal last_value) {
		this.last_value = last_value;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((default_value == null) ? 0 : default_value.hashCode());
		result = prime * result + ((gas_day == null) ? 0 : gas_day.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((last_value == null) ? 0 : last_value.hashCode());
		result = prime * result + ((parking_value == null) ? 0 : parking_value.hashCode());
		result = prime * result + ((zone_code == null) ? 0 : zone_code.hashCode());
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
		ParkingAllocationFormBean other = (ParkingAllocationFormBean) obj;
		if (default_value == null) {
			if (other.default_value != null)
				return false;
		} else if (!default_value.equals(other.default_value))
			return false;
		if (gas_day == null) {
			if (other.gas_day != null)
				return false;
		} else if (!gas_day.equals(other.gas_day))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		if (last_value == null) {
			if (other.last_value != null)
				return false;
		} else if (!last_value.equals(other.last_value))
			return false;
		if (parking_value == null) {
			if (other.parking_value != null)
				return false;
		} else if (!parking_value.equals(other.parking_value))
			return false;
		if (zone_code == null) {
			if (other.zone_code != null)
				return false;
		} else if (!zone_code.equals(other.zone_code))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ParkingAllocationFormBean [gas_day=" + gas_day + ", idn_zone=" + idn_zone + ", zone_code=" + zone_code
				+ ", parking_value=" + parking_value + ", default_value=" + default_value + ", last_value=" + last_value
				+ "]";
	}
	
	
}
