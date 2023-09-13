package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ScadaAlarmBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8210572551497660753L;

	 
	private BigDecimal idn_scada_alarm;
	private String scada_tag_name;
	private Date scada_timestamp;
	private String scada_label;
	private String metering_point;
	private BigDecimal capacity_restriction;
	private BigDecimal idn_area;
	private String area_code;
	private Date temp_resolution_time;
	private String severity;
	private String status;
	private String comments_remarks;
	private String comments_shipper;
	private String alarm_id;
	
	private BigDecimal idn_scada_tag_name;
	private BigDecimal idn_scada_label;
	private BigDecimal idn_scada_point;
	private String origin;
	
	private String alarm_type;
	private boolean is_binary;
	private String binary;
	
	private boolean edit = false;
	
	

	public ScadaAlarmBean() {
		super();
	}

	public BigDecimal getIdn_scada_alarm() {
		return idn_scada_alarm;
	}

	public void setIdn_scada_alarm(BigDecimal idn_scada_alarm) {
		this.idn_scada_alarm = idn_scada_alarm;
	}

	public String getScada_tag_name() {
		return scada_tag_name;
	}

	public void setScada_tag_name(String scada_tag_name) {
		this.scada_tag_name = scada_tag_name;
	}

	public Date getScada_timestamp() {
		return scada_timestamp;
	}

	public void setScada_timestamp(Date scada_timestamp) {
		this.scada_timestamp = scada_timestamp;
	}

	public String getScada_label() {
		return scada_label;
	}

	public void setScada_label(String scada_label) {
		this.scada_label = scada_label;
	}

	public String getMetering_point() {
		return metering_point;
	}

	public void setMetering_point(String metering_point) {
		this.metering_point = metering_point;
	}

	public BigDecimal getCapacity_restriction() {
		return capacity_restriction;
	}

	public void setCapacity_restriction(BigDecimal capacity_restriction) {
		this.capacity_restriction = capacity_restriction;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public Date getTemp_resolution_time() {
		return temp_resolution_time;
	}

	public void setTemp_resolution_time(Date temp_resolution_time) {
		this.temp_resolution_time = temp_resolution_time;
	}

	public String getSeverity() {
		return (severity==null ? "NO SEVERITY" : severity);
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	
	public String getComments_remarks() {
		return comments_remarks;
	}

	public void setComments_remarks(String comments_remarks) {
		this.comments_remarks = comments_remarks;
	}
	
	

	public String getAlarm_id() {
		return alarm_id;
	}

	public void setAlarm_id(String alarm_id) {
		this.alarm_id = alarm_id;
	}
	

	public BigDecimal getIdn_scada_tag_name() {
		return idn_scada_tag_name;
	}

	public void setIdn_scada_tag_name(BigDecimal idn_scada_tag_name) {
		this.idn_scada_tag_name = idn_scada_tag_name;
	}

	public BigDecimal getIdn_scada_label() {
		return idn_scada_label;
	}

	public void setIdn_scada_label(BigDecimal idn_scada_label) {
		this.idn_scada_label = idn_scada_label;
	}

	public BigDecimal getIdn_scada_point() {
		return idn_scada_point;
	}

	public void setIdn_scada_point(BigDecimal idn_scada_point) {
		this.idn_scada_point = idn_scada_point;
	}

	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	



	public String getComments_shipper() {
		return comments_shipper;
	}

	public void setComments_shipper(String comments_shipper) {
		this.comments_shipper = comments_shipper;
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
		result = prime * result + ((area_code == null) ? 0 : area_code.hashCode());
		result = prime * result + ((capacity_restriction == null) ? 0 : capacity_restriction.hashCode());
		result = prime * result + ((comments_remarks == null) ? 0 : comments_remarks.hashCode());
		result = prime * result + (edit ? 1231 : 1237);
		result = prime * result + ((idn_area == null) ? 0 : idn_area.hashCode());
		result = prime * result + ((idn_scada_alarm == null) ? 0 : idn_scada_alarm.hashCode());
		result = prime * result + ((idn_scada_label == null) ? 0 : idn_scada_label.hashCode());
		result = prime * result + ((idn_scada_point == null) ? 0 : idn_scada_point.hashCode());
		result = prime * result + ((idn_scada_tag_name == null) ? 0 : idn_scada_tag_name.hashCode());
		result = prime * result + ((metering_point == null) ? 0 : metering_point.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((scada_label == null) ? 0 : scada_label.hashCode());
		result = prime * result + ((scada_tag_name == null) ? 0 : scada_tag_name.hashCode());
		result = prime * result + ((scada_timestamp == null) ? 0 : scada_timestamp.hashCode());
		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((temp_resolution_time == null) ? 0 : temp_resolution_time.hashCode());
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
		ScadaAlarmBean other = (ScadaAlarmBean) obj;
		if (alarm_id == null) {
			if (other.alarm_id != null)
				return false;
		} else if (!alarm_id.equals(other.alarm_id))
			return false;
		if (area_code == null) {
			if (other.area_code != null)
				return false;
		} else if (!area_code.equals(other.area_code))
			return false;
		if (capacity_restriction == null) {
			if (other.capacity_restriction != null)
				return false;
		} else if (!capacity_restriction.equals(other.capacity_restriction))
			return false;
		if (comments_remarks == null) {
			if (other.comments_remarks != null)
				return false;
		} else if (!comments_remarks.equals(other.comments_remarks))
			return false;
		if (edit != other.edit)
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
		if (idn_scada_label == null) {
			if (other.idn_scada_label != null)
				return false;
		} else if (!idn_scada_label.equals(other.idn_scada_label))
			return false;
		if (idn_scada_point == null) {
			if (other.idn_scada_point != null)
				return false;
		} else if (!idn_scada_point.equals(other.idn_scada_point))
			return false;
		if (idn_scada_tag_name == null) {
			if (other.idn_scada_tag_name != null)
				return false;
		} else if (!idn_scada_tag_name.equals(other.idn_scada_tag_name))
			return false;
		if (metering_point == null) {
			if (other.metering_point != null)
				return false;
		} else if (!metering_point.equals(other.metering_point))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (scada_label == null) {
			if (other.scada_label != null)
				return false;
		} else if (!scada_label.equals(other.scada_label))
			return false;
		if (scada_tag_name == null) {
			if (other.scada_tag_name != null)
				return false;
		} else if (!scada_tag_name.equals(other.scada_tag_name))
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
		if (temp_resolution_time == null) {
			if (other.temp_resolution_time != null)
				return false;
		} else if (!temp_resolution_time.equals(other.temp_resolution_time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ScadaAlarmBean [idn_scada_alarm=" + idn_scada_alarm + ", scada_tag_name=" + scada_tag_name
				+ ", scada_timestamp=" + scada_timestamp + ", scada_label=" + scada_label + ", metering_point="
				+ metering_point + ", capacity_restriction=" + capacity_restriction + ", idn_area=" + idn_area
				+ ", area_code=" + area_code + ", temp_resolution_time=" + temp_resolution_time + ", severity="
				+ severity + ", status=" + status + ", comments_remarks=" + comments_remarks + ", alarm_id=" + alarm_id
				+ ", idn_scada_tag_name=" + idn_scada_tag_name + ", idn_scada_label=" + idn_scada_label
				+ ", idn_scada_point=" + idn_scada_point + ", origin=" + origin + ", edit=" + edit + "]";
	}
	
	
	public boolean isIs_binary() {
		return is_binary;
	}



	public void setIs_binary(boolean is_binary) {
		this.is_binary = is_binary;
		if(is_binary){
			this.binary = "Y";
		} else {
			this.binary = "N";
		}
	}

	

	public String getBinary() {
		return binary;
	}



	public void setBinary(String binary) {
		this.binary = binary;
		if(binary!=null){
			if(binary.equals("Y")){
				this.is_binary = true;
			} else {
				this.is_binary = false;
			}
		} else {
			this.is_binary = false;
		}
	}
	
}
