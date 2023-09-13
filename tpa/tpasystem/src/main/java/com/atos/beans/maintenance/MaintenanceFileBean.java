package com.atos.beans.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

import com.atos.beans.UserAudBean;

public class MaintenanceFileBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6166775734423937637L;

	private BigDecimal idn_maintenance_file;
	private String file_name;
	private byte[] binary_data;
	private String content_type;
	
	public BigDecimal getIdn_maintenance_file() {
		return idn_maintenance_file;
	}
	public void setIdn_maintenance_file(BigDecimal idn_maintenance_file) {
		this.idn_maintenance_file = idn_maintenance_file;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public byte[] getBinary_data() {
		return binary_data;
	}
	public void setBinary_data(byte[] binary_data) {
		this.binary_data = binary_data;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binary_data);
		result = prime * result + ((content_type == null) ? 0 : content_type.hashCode());
		result = prime * result + ((file_name == null) ? 0 : file_name.hashCode());
		result = prime * result + ((idn_maintenance_file == null) ? 0 : idn_maintenance_file.hashCode());
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
		MaintenanceFileBean other = (MaintenanceFileBean) obj;
		if (!Arrays.equals(binary_data, other.binary_data))
			return false;
		if (content_type == null) {
			if (other.content_type != null)
				return false;
		} else if (!content_type.equals(other.content_type))
			return false;
		if (file_name == null) {
			if (other.file_name != null)
				return false;
		} else if (!file_name.equals(other.file_name))
			return false;
		if (idn_maintenance_file == null) {
			if (other.idn_maintenance_file != null)
				return false;
		} else if (!idn_maintenance_file.equals(other.idn_maintenance_file))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MaintenanceFileBean [idn_maintenance_file=" + idn_maintenance_file + ", file_name=" + file_name
				+ ", binary_data=" + Arrays.toString(binary_data) + ", content_type=" + content_type + "]";
	}
	
}
