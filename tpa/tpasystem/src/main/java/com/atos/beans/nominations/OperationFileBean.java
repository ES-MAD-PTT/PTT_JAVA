package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class OperationFileBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7326231831836009110L;

	private BigDecimal idn_operation_file;
	private BigDecimal idn_operation_category;
	private BigDecimal idn_operation_term;
	private String file_name;
	private Date version_date;
	private byte[] binary_data;
	private String xml_data;
	public BigDecimal getIdn_operation_file() {
		return idn_operation_file;
	}
	public void setIdn_operation_file(BigDecimal idn_operation_file) {
		this.idn_operation_file = idn_operation_file;
	}
	public BigDecimal getIdn_operation_category() {
		return idn_operation_category;
	}
	public void setIdn_operation_category(BigDecimal idn_operation_category) {
		this.idn_operation_category = idn_operation_category;
	}
	public BigDecimal getIdn_operation_term() {
		return idn_operation_term;
	}
	public void setIdn_operation_term(BigDecimal idn_operation_term) {
		this.idn_operation_term = idn_operation_term;
	}
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
		result = prime * result + ((file_name == null) ? 0 : file_name.hashCode());
		result = prime * result + ((idn_operation_category == null) ? 0 : idn_operation_category.hashCode());
		result = prime * result + ((idn_operation_file == null) ? 0 : idn_operation_file.hashCode());
		result = prime * result + ((idn_operation_term == null) ? 0 : idn_operation_term.hashCode());
		result = prime * result + ((version_date == null) ? 0 : version_date.hashCode());
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
		OperationFileBean other = (OperationFileBean) obj;
		if (file_name == null) {
			if (other.file_name != null)
				return false;
		} else if (!file_name.equals(other.file_name))
			return false;
		if (idn_operation_category == null) {
			if (other.idn_operation_category != null)
				return false;
		} else if (!idn_operation_category.equals(other.idn_operation_category))
			return false;
		if (idn_operation_file == null) {
			if (other.idn_operation_file != null)
				return false;
		} else if (!idn_operation_file.equals(other.idn_operation_file))
			return false;
		if (idn_operation_term == null) {
			if (other.idn_operation_term != null)
				return false;
		} else if (!idn_operation_term.equals(other.idn_operation_term))
			return false;
		if (version_date == null) {
			if (other.version_date != null)
				return false;
		} else if (!version_date.equals(other.version_date))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OperationFileBean [idn_operation_file=" + idn_operation_file + ", idn_operation_category="
				+ idn_operation_category + ", idn_operation_term=" + idn_operation_term + ", file_name=" + file_name
				+ ", version_date=" + version_date + "]";
	}
	
	
}
