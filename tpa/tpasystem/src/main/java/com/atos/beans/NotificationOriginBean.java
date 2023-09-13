package com.atos.beans;

import java.io.Serializable;

public class NotificationOriginBean implements Serializable{

	private static final long serialVersionUID = 3115765982182076603L;

    private String origin;
    private String user_id;
    
	public String getOrigin() {
		return origin;
	}
	
	public NotificationOriginBean(String origin, String user_id) {
		super();
		this.origin = origin;
		this.user_id = user_id;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
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
		NotificationOriginBean other = (NotificationOriginBean) obj;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
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
		return "NotificationOriginBean [origin=" + origin + ", user_id=" + user_id + "]";
	}

    
}
