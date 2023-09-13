package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NotificationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3115765982182076603L;

	private Integer integer_exit;
	private BigDecimal idn_notification;
	private BigDecimal systemId;
    private String origin;
    private String information;
    private Date start_date;
    private BigDecimal idn_notification_user;
    private String type_code;
    private BigDecimal idn_user_group;
    private String user_id;
    private String language;
    private String error_desc;
	public Integer getInteger_exit() {
		return integer_exit;
	}
	public void setInteger_exit(Integer integer_exit) {
		this.integer_exit = integer_exit;
	}
	public BigDecimal getIdn_notification() {
		return idn_notification;
	}
	public void setIdn_notification(BigDecimal idn_notification) {
		this.idn_notification = idn_notification;
	}
	public BigDecimal getSystemId() {
		return systemId;
	}
	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public BigDecimal getIdn_notification_user() {
		return idn_notification_user;
	}
	public void setIdn_notification_user(BigDecimal idn_notification_user) {
		this.idn_notification_user = idn_notification_user;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}
	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
		result = prime * result + ((error_desc == null) ? 0 : error_desc.hashCode());
		result = prime * result + ((idn_notification == null) ? 0 : idn_notification.hashCode());
		result = prime * result + ((idn_notification_user == null) ? 0 : idn_notification_user.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((information == null) ? 0 : information.hashCode());
		result = prime * result + ((integer_exit == null) ? 0 : integer_exit.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
		result = prime * result + ((type_code == null) ? 0 : type_code.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
		NotificationBean other = (NotificationBean) obj;
		if (error_desc == null) {
			if (other.error_desc != null)
				return false;
		} else if (!error_desc.equals(other.error_desc))
			return false;
		if (idn_notification == null) {
			if (other.idn_notification != null)
				return false;
		} else if (!idn_notification.equals(other.idn_notification))
			return false;
		if (idn_notification_user == null) {
			if (other.idn_notification_user != null)
				return false;
		} else if (!idn_notification_user.equals(other.idn_notification_user))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (integer_exit == null) {
			if (other.integer_exit != null)
				return false;
		} else if (!integer_exit.equals(other.integer_exit))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		if (type_code == null) {
			if (other.type_code != null)
				return false;
		} else if (!type_code.equals(other.type_code))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NotificationBean [integer_exit=" + integer_exit + ", idn_notification=" + idn_notification
				+ ", systemId=" + systemId + ", origin=" + origin + ", information=" + information + ", start_date="
				+ start_date + ", idn_notification_user=" + idn_notification_user + ", type_code=" + type_code
				+ ", idn_user_group=" + idn_user_group + ", user_id=" + user_id + ", language=" + language
				+ ", error_desc=" + error_desc + "]";
	}
    
}
