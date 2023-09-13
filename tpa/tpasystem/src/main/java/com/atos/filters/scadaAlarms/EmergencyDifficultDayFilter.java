package com.atos.filters.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EmergencyDifficultDayFilter implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal idn_tso_event_type;
	private BigDecimal zone;
	private BigDecimal idn_user_group;
	private String status;
	private Date start_date;
	private Date end_date;
	private Date aud_last_date;
	private BigDecimal idn_system;//offshore
	private boolean isShipper;
	
	
	
	public EmergencyDifficultDayFilter() {
		super();
		this.idn_tso_event_type = null;
		this.zone = null;
		this.idn_user_group = null;
		this.status = null;
		this.start_date = null;
		this.end_date = null;
		this.aud_last_date = null;
		this.idn_system = null;
		this.isShipper = false;
	}

	public EmergencyDifficultDayFilter(BigDecimal type, BigDecimal zone, BigDecimal idn_user_group, String status, Date startDate,
			Date endDate, Date audLastDate, BigDecimal idn_system, boolean isShipper) {
		super();
		this.idn_tso_event_type = type;
		this.zone = zone;
		this.idn_user_group = idn_user_group;
		this.status = status;
		this.start_date = startDate;
		this.end_date = endDate;
		this.aud_last_date = audLastDate;
		this.idn_system = idn_system;
		this.isShipper = isShipper;
	}
	
	public BigDecimal getType() {
		return idn_tso_event_type;
	}

	public void setType(BigDecimal type) {
		this.idn_tso_event_type = type;
	}
	
	public BigDecimal getZone() {
		return zone;
	}
	
	public void setZone(BigDecimal zone) {
		this.zone = zone;
	}
	
	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}
	
	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getStart_date() {
		return start_date;
	}
	
	public void setStart_date(Date startDate) {
		this.start_date = startDate;
	}
	
	public Date getEnd_date() {
		return end_date;
	}
	
	public void setEnd_date(Date endDate) {
		this.end_date = endDate;
	}
	
	public void setAud_last_date(Date lastDate) {
		this.aud_last_date = lastDate;
	}
	
	public Date getAud_last_date() {
		return aud_last_date;
	}
	
	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}
	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public boolean getIsShipper() {
		return isShipper;
	}
	
	public void setIsShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}

	@Override
	public String toString() {
		return "EmergencyDifficultDayFilter [idn_tso_event_type=" + idn_tso_event_type + ", zone=" + zone
				+ ", idn_user_group=" + idn_user_group + ", status=" + status + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", aud_last_date=" + aud_last_date + ", idn_system=" + idn_system
				+ ", isShipper=" + isShipper + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aud_last_date == null) ? 0 : aud_last_date.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((idn_system == null) ? 0 : idn_system.hashCode());
		result = prime * result + ((idn_tso_event_type == null) ? 0 : idn_tso_event_type.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + (isShipper ? 1231 : 1237);
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
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
		EmergencyDifficultDayFilter other = (EmergencyDifficultDayFilter) obj;
		if (aud_last_date == null) {
			if (other.aud_last_date != null)
				return false;
		} else if (!aud_last_date.equals(other.aud_last_date))
			return false;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (idn_system == null) {
			if (other.idn_system != null)
				return false;
		} else if (!idn_system.equals(other.idn_system))
			return false;
		if (idn_tso_event_type == null) {
			if (other.idn_tso_event_type != null)
				return false;
		} else if (!idn_tso_event_type.equals(other.idn_tso_event_type))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (isShipper != other.isShipper)
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		return true;
	}

	
}
