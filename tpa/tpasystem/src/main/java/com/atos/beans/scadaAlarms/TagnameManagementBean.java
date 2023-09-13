package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class TagnameManagementBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -8210572551497660753L;

	 
	private BigDecimal idn_scada_tag_name;
	private String scada_tag_name;
	private String scada_point;
	private String scada_label;
	private BigDecimal min_alarm_threshold;
	private BigDecimal max_alarm_threshold;
	private BigDecimal on_off_alarm_threshold;
	private boolean is_binary;
	private String binary;
	
	
	private boolean is_enabled;
	private String enabled;
	private BigDecimal idn_scada_label;
	private BigDecimal idn_scada_point;
	private BigDecimal idn_pipeline_system;
	
		

	public TagnameManagementBean() {
		super();
	}



	public BigDecimal getIdn_scada_tag_name() {
		return idn_scada_tag_name;
	}



	public void setIdn_scada_tag_name(BigDecimal idn_scada_tag_name) {
		this.idn_scada_tag_name = idn_scada_tag_name;
	}



	public String getScada_tag_name() {
		return scada_tag_name;
	}



	public void setScada_tag_name(String scada_tag_name) {
		this.scada_tag_name = scada_tag_name;
	}



	public String getScada_point() {
		return scada_point;
	}



	public void setScada_point(String scada_point) {
		this.scada_point = scada_point;
	}



	public String getScada_label() {
		return scada_label;
	}



	public void setScada_label(String scada_label) {
		this.scada_label = scada_label;
	}



	public BigDecimal getMin_alarm_threshold() {
		return min_alarm_threshold;
	}



	public void setMin_alarm_threshold(BigDecimal min_alarm_threshold) {
		this.min_alarm_threshold = min_alarm_threshold;
	}



	public BigDecimal getMax_alarm_threshold() {
		return max_alarm_threshold;
	}



	public void setMax_alarm_threshold(BigDecimal max_alarm_threshold) {
		this.max_alarm_threshold = max_alarm_threshold;
	}



	public BigDecimal getOn_off_alarm_threshold() {
		return on_off_alarm_threshold;
	}



	public void setOn_off_alarm_threshold(BigDecimal on_off_alarm_threshold) {
		this.on_off_alarm_threshold = on_off_alarm_threshold;
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



	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}



	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
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



	public boolean isIs_enabled() {
		return is_enabled;
	}



	public void setIs_enabled(boolean is_enabled) {
		this.is_enabled = is_enabled;
		if(is_enabled){
			this.enabled = "Y";
		} else {
			this.enabled = "N";
		}
	}



	public String getEnabled() {
		return enabled;
	}



	public void setEnabled(String enabled) {
		this.enabled = enabled;
		if(enabled!=null){
			if(enabled.equals("Y")){
				this.is_enabled = true;
			} else {
				this.is_enabled = false;
			}
		} else {
			this.is_enabled = false;
		}
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((binary == null) ? 0 : binary.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((idn_pipeline_system == null) ? 0 : idn_pipeline_system.hashCode());
		result = prime * result + ((idn_scada_label == null) ? 0 : idn_scada_label.hashCode());
		result = prime * result + ((idn_scada_point == null) ? 0 : idn_scada_point.hashCode());
		result = prime * result + ((idn_scada_tag_name == null) ? 0 : idn_scada_tag_name.hashCode());
		result = prime * result + (is_binary ? 1231 : 1237);
		result = prime * result + (is_enabled ? 1231 : 1237);
		result = prime * result + ((max_alarm_threshold == null) ? 0 : max_alarm_threshold.hashCode());
		result = prime * result + ((min_alarm_threshold == null) ? 0 : min_alarm_threshold.hashCode());
		result = prime * result + ((on_off_alarm_threshold == null) ? 0 : on_off_alarm_threshold.hashCode());
		result = prime * result + ((scada_label == null) ? 0 : scada_label.hashCode());
		result = prime * result + ((scada_point == null) ? 0 : scada_point.hashCode());
		result = prime * result + ((scada_tag_name == null) ? 0 : scada_tag_name.hashCode());
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
		TagnameManagementBean other = (TagnameManagementBean) obj;
		if (binary == null) {
			if (other.binary != null)
				return false;
		} else if (!binary.equals(other.binary))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (idn_pipeline_system == null) {
			if (other.idn_pipeline_system != null)
				return false;
		} else if (!idn_pipeline_system.equals(other.idn_pipeline_system))
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
		if (is_binary != other.is_binary)
			return false;
		if (is_enabled != other.is_enabled)
			return false;
		if (max_alarm_threshold == null) {
			if (other.max_alarm_threshold != null)
				return false;
		} else if (!max_alarm_threshold.equals(other.max_alarm_threshold))
			return false;
		if (min_alarm_threshold == null) {
			if (other.min_alarm_threshold != null)
				return false;
		} else if (!min_alarm_threshold.equals(other.min_alarm_threshold))
			return false;
		if (on_off_alarm_threshold == null) {
			if (other.on_off_alarm_threshold != null)
				return false;
		} else if (!on_off_alarm_threshold.equals(other.on_off_alarm_threshold))
			return false;
		if (scada_label == null) {
			if (other.scada_label != null)
				return false;
		} else if (!scada_label.equals(other.scada_label))
			return false;
		if (scada_point == null) {
			if (other.scada_point != null)
				return false;
		} else if (!scada_point.equals(other.scada_point))
			return false;
		if (scada_tag_name == null) {
			if (other.scada_tag_name != null)
				return false;
		} else if (!scada_tag_name.equals(other.scada_tag_name))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "TagnameManagementBean [idn_scada_tag_name=" + idn_scada_tag_name + ", scada_tag_name=" + scada_tag_name
				+ ", scada_point=" + scada_point + ", scada_label=" + scada_label + ", min_alarm_threshold="
				+ min_alarm_threshold + ", max_alarm_threshold=" + max_alarm_threshold + ", on_off_alarm_threshold="
				+ on_off_alarm_threshold + ", is_binary=" + is_binary + ", binary=" + binary + ", is_enabled="
				+ is_enabled + ", enabled=" + enabled + ", idn_scada_label=" + idn_scada_label + ", idn_scada_point="
				+ idn_scada_point + ", idn_pipeline_system=" + idn_pipeline_system + "]";
	}



	

	
	
}
