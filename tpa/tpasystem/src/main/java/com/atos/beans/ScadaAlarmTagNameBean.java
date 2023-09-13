package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class ScadaAlarmTagNameBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8793279250474974655L;

	private BigDecimal idn_scada_tag_name;
    private String scada_tag_name;
    private BigDecimal min_alarm_threshold;
    private BigDecimal max_alarm_threshold;
    private BigDecimal on_off_alarm_threshold;
    private String is_binary;

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
	
	
	
	public String getIs_binary() {
		return is_binary;
	}
	public void setIs_binary(String is_binary) {
		this.is_binary = is_binary;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idn_scada_tag_name == null) ? 0 : idn_scada_tag_name.hashCode());
		result = prime * result + ((is_binary == null) ? 0 : is_binary.hashCode());
		result = prime * result + ((max_alarm_threshold == null) ? 0 : max_alarm_threshold.hashCode());
		result = prime * result + ((min_alarm_threshold == null) ? 0 : min_alarm_threshold.hashCode());
		result = prime * result + ((on_off_alarm_threshold == null) ? 0 : on_off_alarm_threshold.hashCode());
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
		ScadaAlarmTagNameBean other = (ScadaAlarmTagNameBean) obj;
		if (idn_scada_tag_name == null) {
			if (other.idn_scada_tag_name != null)
				return false;
		} else if (!idn_scada_tag_name.equals(other.idn_scada_tag_name))
			return false;
		if (is_binary == null) {
			if (other.is_binary != null)
				return false;
		} else if (!is_binary.equals(other.is_binary))
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
		if (scada_tag_name == null) {
			if (other.scada_tag_name != null)
				return false;
		} else if (!scada_tag_name.equals(other.scada_tag_name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ScadaAlarmTagNameBean [idn_scada_tag_name=" + idn_scada_tag_name + ", scada_tag_name=" + scada_tag_name
				+ ", min_alarm_threshold=" + min_alarm_threshold + ", max_alarm_threshold=" + max_alarm_threshold
				+ ", on_off_alarm_threshold=" + on_off_alarm_threshold + ", is_binary=" + is_binary + "]";
	}
	
	
	
	
}
