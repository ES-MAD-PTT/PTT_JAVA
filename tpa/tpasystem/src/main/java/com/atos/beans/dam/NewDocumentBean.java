package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class NewDocumentBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = 2326616187010567845L;
	
	private BigDecimal idn_document_type;
	private String code;
	private String desc;
	private BigDecimal idn_user_group_type;
	private BigDecimal sort_value;
	private String username;
	private String is_user_guide = "N";
	
	public BigDecimal getIdn_document_type() {
		return idn_document_type;
	}
	public void setIdn_document_type(BigDecimal idn_document_type) {
		this.idn_document_type = idn_document_type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public BigDecimal getIdn_user_group_type() {
		return idn_user_group_type;
	}
	public void setIdn_user_group_type(BigDecimal idn_user_group_type) {
		this.idn_user_group_type = idn_user_group_type;
	}
	public BigDecimal getSort_value() {
		return sort_value;
	}
	public void setSort_value(BigDecimal sort_value) {
		this.sort_value = sort_value;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIs_user_guide() {
		return is_user_guide;
	}
	public void setIs_user_guide(String is_user_guide) {
		this.is_user_guide = is_user_guide;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NewDocumentBean [idn_document_type=");
		builder.append(idn_document_type);
		builder.append(", code=");
		builder.append(code);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", idn_user_group_type=");
		builder.append(idn_user_group_type);
		builder.append(", sort_value=");
		builder.append(sort_value);
		builder.append(", username=");
		builder.append(username);
		builder.append(", is_user_guide=");
		builder.append(is_user_guide);
		builder.append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((idn_document_type == null) ? 0 : idn_document_type.hashCode());
		result = prime * result + ((idn_user_group_type == null) ? 0 : idn_user_group_type.hashCode());
		result = prime * result + ((is_user_guide == null) ? 0 : is_user_guide.hashCode());
		result = prime * result + ((sort_value == null) ? 0 : sort_value.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		NewDocumentBean other = (NewDocumentBean) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (idn_document_type == null) {
			if (other.idn_document_type != null)
				return false;
		} else if (!idn_document_type.equals(other.idn_document_type))
			return false;
		if (idn_user_group_type == null) {
			if (other.idn_user_group_type != null)
				return false;
		} else if (!idn_user_group_type.equals(other.idn_user_group_type))
			return false;
		if (is_user_guide == null) {
			if (other.is_user_guide != null)
				return false;
		} else if (!is_user_guide.equals(other.is_user_guide))
			return false;
		if (sort_value == null) {
			if (other.sort_value != null)
				return false;
		} else if (!sort_value.equals(other.sort_value))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
