package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class OperationTemplateBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2760535188367807552L;
	private BigDecimal idn_operation_template;
	private BigDecimal idn_operation_category;
	private BigDecimal idn_operation_term;
	private String file_type;
	private Date start_date;
	private Date end_date;
	private byte[] binary_data;
	private String file_name;
	private BigDecimal idn_xml_map;
	private BigDecimal systemId;
	
	public OperationTemplateBean(){
		this.idn_operation_template=null;
		this.idn_operation_category=null;
		this.idn_operation_term=null;
		this.file_type=null;
		this.start_date=null;
		this.end_date=null;
		this.binary_data=null;
		this.file_name=null;
		this.idn_xml_map=null;
		this.systemId=null;
	}
	public BigDecimal getIdn_operation_template() {
		return idn_operation_template;
	}
	public void setIdn_operation_template(BigDecimal idn_operation_template) {
		this.idn_operation_template = idn_operation_template;
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
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public byte[] getBinary_data() {
		return binary_data;
	}
	public void setBinary_data(byte[] binary_data) {
		this.binary_data = binary_data;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public BigDecimal getIdn_xml_map() {
		return idn_xml_map;
	}
	public void setIdn_xml_map(BigDecimal idn_xml_map) {
		this.idn_xml_map = idn_xml_map;
	}
	public BigDecimal getSystemId() {
		return systemId;
	}
	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binary_data);
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((file_name == null) ? 0 : file_name.hashCode());
		result = prime * result + ((file_type == null) ? 0 : file_type.hashCode());
		result = prime * result + ((idn_operation_category == null) ? 0 : idn_operation_category.hashCode());
		result = prime * result + ((idn_operation_template == null) ? 0 : idn_operation_template.hashCode());
		result = prime * result + ((idn_operation_term == null) ? 0 : idn_operation_term.hashCode());
		result = prime * result + ((idn_xml_map == null) ? 0 : idn_xml_map.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
		result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
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
		OperationTemplateBean other = (OperationTemplateBean) obj;
		if (!Arrays.equals(binary_data, other.binary_data))
			return false;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (file_name == null) {
			if (other.file_name != null)
				return false;
		} else if (!file_name.equals(other.file_name))
			return false;
		if (file_type == null) {
			if (other.file_type != null)
				return false;
		} else if (!file_type.equals(other.file_type))
			return false;
		if (idn_operation_category == null) {
			if (other.idn_operation_category != null)
				return false;
		} else if (!idn_operation_category.equals(other.idn_operation_category))
			return false;
		if (idn_operation_template == null) {
			if (other.idn_operation_template != null)
				return false;
		} else if (!idn_operation_template.equals(other.idn_operation_template))
			return false;
		if (idn_operation_term == null) {
			if (other.idn_operation_term != null)
				return false;
		} else if (!idn_operation_term.equals(other.idn_operation_term))
			return false;
		if (idn_xml_map == null) {
			if (other.idn_xml_map != null)
				return false;
		} else if (!idn_xml_map.equals(other.idn_xml_map))
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
		return true;
	}
	@Override
	public String toString() {
		return "OperationTemplateBean [idn_operation_template=" + idn_operation_template + ", idn_operation_category="
				+ idn_operation_category + ", idn_operation_term=" + idn_operation_term + ", file_type=" + file_type
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", file_name=" + file_name
				+ ", idn_xml_map=" + idn_xml_map + ", systemId=" + systemId + "]";
	}

}
