package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class ComboFilterNS implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7211545012859133525L;
	private BigDecimal key;
	private String value = "";

	public ComboFilterNS(BigDecimal key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public BigDecimal getKey() {
		return key;
	}

	public void setKey(BigDecimal key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ComboFilterNS other = (ComboFilterNS) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComboFilterNS [key=" + key + ", value=" + value + "]";
	}

	
	
}
