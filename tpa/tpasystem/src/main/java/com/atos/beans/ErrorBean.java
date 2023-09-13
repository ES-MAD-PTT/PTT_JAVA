package com.atos.beans;

import java.io.Serializable;

public class ErrorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6269862033023559153L;

	private Integer error_code;
	private String error_msg;
	
	public ErrorBean(){
		
	}
	
	public ErrorBean(Integer error_code, String error_msg) {
		super();
		this.error_code = error_code;
		this.error_msg = error_msg;
	}

	public Integer getError_code() {
		return error_code;
	}
	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error_code == null) ? 0 : error_code.hashCode());
		result = prime * result + ((error_msg == null) ? 0 : error_msg.hashCode());
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
		ErrorBean other = (ErrorBean) obj;
		if (error_code == null) {
			if (other.error_code != null)
				return false;
		} else if (!error_code.equals(other.error_code))
			return false;
		if (error_msg == null) {
			if (other.error_msg != null)
				return false;
		} else if (!error_msg.equals(other.error_msg))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ErrorBean [error_code=" + error_code + ", error_msg=" + error_msg + "]";
	}
	
}
