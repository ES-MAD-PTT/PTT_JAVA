package com.atos.beans;

import java.io.Serializable;
import java.util.Date;

import com.atos.utils.Constants;

public class MessageBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3416676040679938937L;

	private int type; 
	private String type_string;
	private String summary;
	private String message;
	private Date creationDate;
	public MessageBean(int type, String summary, String message, Date creationDate) {
		super();
		this.setType(type);
		this.summary = summary;
		this.message = message;
		this.creationDate = creationDate;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
		if(type==Constants.ERROR){
			this.type_string="Error";
		}
		if(type==Constants.WARNING){
			this.type_string="Warning";
		}
		if(type==Constants.INFO){
			this.type_string="Info";
		}
	}
	public String getType_string() {
		return type_string;
	}
	public void setType_string(String type_string) {
		this.type_string = type_string;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	@Override
	public String toString() {
		return "MessageBean [type=" + type + ", type_string=" + type_string + ", summary=" + summary + ", message=" + message + ", creationDate="
				+ creationDate + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
		result = prime * result + type;
		result = prime * result + ((type_string == null) ? 0 : type_string.hashCode());
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
		MessageBean other = (MessageBean) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		if (type != other.type)
			return false;
		if (type_string == null) {
			if (other.type_string != null)
				return false;
		} else if (!type_string.equals(other.type_string))
			return false;
		return true;
	}

}
