package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AlarmBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8210572551497660753L;

	private BigDecimal idn_scada_alarm;
	private BigDecimal idn_scada_tag_name;
	private Date scada_timestamp;
	private BigDecimal scada_tag_value;
	private BigDecimal capacity_restriction;
	private String severity;
	private BigDecimal idn_area;
	private Date estimated_end_date;
	private Date publication_date;
	private Date end_date;
	private String status;
	private String alarm_id;
	private String alarm_type; //CH717 (1196114)
	
	public BigDecimal getIdn_scada_alarm() {
		return idn_scada_alarm;
	}
	public void setIdn_scada_alarm(BigDecimal idn_scada_alarm) {
		this.idn_scada_alarm = idn_scada_alarm;
	}
	public BigDecimal getIdn_scada_tag_name() {
		return idn_scada_tag_name;
	}
	public void setIdn_scada_tag_name(BigDecimal idn_scada_tag_name) {
		this.idn_scada_tag_name = idn_scada_tag_name;
	}
	public Date getScada_timestamp() {
		return scada_timestamp;
	}
	public void setScada_timestamp(Date scada_timestamp) {
		this.scada_timestamp = scada_timestamp;
	}
	public BigDecimal getScada_tag_value() {
		return scada_tag_value;
	}
	public void setScada_tag_value(BigDecimal scada_tag_value) {
		this.scada_tag_value = scada_tag_value;
	}
	public BigDecimal getCapacity_restriction() {
		return capacity_restriction;
	}
	public void setCapacity_restriction(BigDecimal capacity_restriction) {
		this.capacity_restriction = capacity_restriction;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public BigDecimal getIdn_area() {
		return idn_area;
	}
	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}
	public Date getEstimated_end_date() {
		return estimated_end_date;
	}
	public void setEstimated_end_date(Date estimated_end_date) {
		this.estimated_end_date = estimated_end_date;
	}
	public Date getPublication_date() {
		return publication_date;
	}
	public void setPublication_date(Date publication_date) {
		this.publication_date = publication_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAlarm_id() {
		return alarm_id;
	}
	public void setAlarm_id(String alarm_id) {
		this.alarm_id = alarm_id;
	}
	
	
	public String getAlarm_type() {
		return alarm_type;
	}
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alarm_id == null) ? 0 : alarm_id.hashCode());
		result = prime * result + ((capacity_restriction == null) ? 0 : capacity_restriction.hashCode());
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((estimated_end_date == null) ? 0 : estimated_end_date.hashCode());
		result = prime * result + ((idn_area == null) ? 0 : idn_area.hashCode());
		result = prime * result + ((idn_scada_alarm == null) ? 0 : idn_scada_alarm.hashCode());
		result = prime * result + ((idn_scada_tag_name == null) ? 0 : idn_scada_tag_name.hashCode());
		result = prime * result + ((publication_date == null) ? 0 : publication_date.hashCode());
		result = prime * result + ((scada_tag_value == null) ? 0 : scada_tag_value.hashCode());
		result = prime * result + ((scada_timestamp == null) ? 0 : scada_timestamp.hashCode());
		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		AlarmBean other = (AlarmBean) obj;
		if (alarm_id == null) {
			if (other.alarm_id != null)
				return false;
		} else if (!alarm_id.equals(other.alarm_id))
			return false;
		if (capacity_restriction == null) {
			if (other.capacity_restriction != null)
				return false;
		} else if (!capacity_restriction.equals(other.capacity_restriction))
			return false;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (estimated_end_date == null) {
			if (other.estimated_end_date != null)
				return false;
		} else if (!estimated_end_date.equals(other.estimated_end_date))
			return false;
		if (idn_area == null) {
			if (other.idn_area != null)
				return false;
		} else if (!idn_area.equals(other.idn_area))
			return false;
		if (idn_scada_alarm == null) {
			if (other.idn_scada_alarm != null)
				return false;
		} else if (!idn_scada_alarm.equals(other.idn_scada_alarm))
			return false;
		if (idn_scada_tag_name == null) {
			if (other.idn_scada_tag_name != null)
				return false;
		} else if (!idn_scada_tag_name.equals(other.idn_scada_tag_name))
			return false;
		if (publication_date == null) {
			if (other.publication_date != null)
				return false;
		} else if (!publication_date.equals(other.publication_date))
			return false;
		if (scada_tag_value == null) {
			if (other.scada_tag_value != null)
				return false;
		} else if (!scada_tag_value.equals(other.scada_tag_value))
			return false;
		if (scada_timestamp == null) {
			if (other.scada_timestamp != null)
				return false;
		} else if (!scada_timestamp.equals(other.scada_timestamp))
			return false;
		if (severity == null) {
			if (other.severity != null)
				return false;
		} else if (!severity.equals(other.severity))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AlarmBean [idn_scada_alarm=" + idn_scada_alarm + ", idn_scada_tag_name=" + idn_scada_tag_name
				+ ", scada_timestamp=" + scada_timestamp + ", scada_tag_value=" + scada_tag_value
				+ ", capacity_restriction=" + capacity_restriction + ", severity=" + severity + ", idn_area=" + idn_area
				+ ", estimated_end_date=" + estimated_end_date + ", publication_date=" + publication_date
				+ ", end_date=" + end_date + ", status=" + status + ", alarm_id=" + alarm_id + ", alarm_type="
				+ alarm_type + "]";
	}
	
	
	
}
