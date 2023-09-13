package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ChangePictureBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7326231831836009110L;

	private String file_name;
	private Date version_date;
	private byte[] binary_data;
	private String xml_data;
	
	
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Date getVersion_date() {
		return version_date;
	}
	public void setVersion_date(Date version_date) {
		this.version_date = version_date;
	}
	public byte[] getBinary_data() {
		return binary_data;
	}
	public void setBinary_data(byte[] binary_data) {
		this.binary_data = binary_data;
	}
	public String getXml_data() {
		return xml_data;
	}
	public void setXml_data(String xml_data) {
		this.xml_data = xml_data;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binary_data);
		result = prime * result + ((file_name == null) ? 0 : file_name.hashCode());
		result = prime * result + ((version_date == null) ? 0 : version_date.hashCode());
		result = prime * result + ((xml_data == null) ? 0 : xml_data.hashCode());
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
		ChangePictureBean other = (ChangePictureBean) obj;
		if (!Arrays.equals(binary_data, other.binary_data))
			return false;
		if (file_name == null) {
			if (other.file_name != null)
				return false;
		} else if (!file_name.equals(other.file_name))
			return false;
		if (version_date == null) {
			if (other.version_date != null)
				return false;
		} else if (!version_date.equals(other.version_date))
			return false;
		if (xml_data == null) {
			if (other.xml_data != null)
				return false;
		} else if (!xml_data.equals(other.xml_data))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ChangePictureBean [file_name=" + file_name + ", version_date=" + version_date + ", binary_data="
				+ Arrays.toString(binary_data) + ", xml_data=" + xml_data + "]";
	}
}
