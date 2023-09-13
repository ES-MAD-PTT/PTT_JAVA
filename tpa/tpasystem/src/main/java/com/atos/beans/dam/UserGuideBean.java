package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class UserGuideBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private String type_desc;
	private String document_name;
	private byte[] binary_data;
	private String version_comment;
	private Date version_date;
	private BigDecimal idn_document_type;
	private BigDecimal idn_document;
	private BigDecimal idn_user_group;

	public UserGuideBean() {
		super();
	}

	public UserGuideBean(String type_desc, String document_name, byte[] binary_data, String version_comment,
			Date version_date, BigDecimal idn_document_type, BigDecimal idn_document, BigDecimal idn_user_group) {
		super();
		this.type_desc = type_desc;
		this.document_name = document_name;
		this.binary_data = binary_data;
		this.version_comment = version_comment;
		this.version_date = version_date;
		this.idn_document_type = idn_document_type;
		this.idn_document = idn_document;
		this.idn_user_group = idn_user_group;
	}

	public String getType_desc() {
		return type_desc;
	}

	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}

	public String getDocument_name() {
		return document_name;
	}

	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}

	public byte[] getBinary_data() {
		return binary_data;
	}

	public void setBinary_data(byte[] binary_data) {
		this.binary_data = binary_data;
	}

	public String getVersion_comment() {
		return version_comment;
	}

	public void setVersion_comment(String version_comment) {
		this.version_comment = version_comment;
	}

	public Date getVersion_date() {
		return version_date;
	}

	public void setVersion_date(Date version_date) {
		this.version_date = version_date;
	}

	public BigDecimal getIdn_document_type() {
		return idn_document_type;
	}

	public void setIdn_document_type(BigDecimal idn_document_type) {
		this.idn_document_type = idn_document_type;
	}

	public BigDecimal getIdn_document() {
		return idn_document;
	}

	public void setIdn_document(BigDecimal idn_document) {
		this.idn_document = idn_document;
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	@Override
	public String toString() {
		return "UserGuideBean [type_desc=" + type_desc + ", document_name=" + document_name + ", binary_data="
				+ Arrays.toString(binary_data) + ", version_comment=" + version_comment + ", version_date="
				+ version_date + ", idn_document_type=" + idn_document_type + ", idn_document=" + idn_document
				+ ", idn_user_group=" + idn_user_group + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binary_data);
		result = prime * result + ((document_name == null) ? 0 : document_name.hashCode());
		result = prime * result + ((idn_document == null) ? 0 : idn_document.hashCode());
		result = prime * result + ((idn_document_type == null) ? 0 : idn_document_type.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((type_desc == null) ? 0 : type_desc.hashCode());
		result = prime * result + ((version_comment == null) ? 0 : version_comment.hashCode());
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
		UserGuideBean other = (UserGuideBean) obj;
		if (!Arrays.equals(binary_data, other.binary_data))
			return false;
		if (document_name == null) {
			if (other.document_name != null)
				return false;
		} else if (!document_name.equals(other.document_name))
			return false;
		if (idn_document == null) {
			if (other.idn_document != null)
				return false;
		} else if (!idn_document.equals(other.idn_document))
			return false;
		if (idn_document_type == null) {
			if (other.idn_document_type != null)
				return false;
		} else if (!idn_document_type.equals(other.idn_document_type))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (type_desc == null) {
			if (other.type_desc != null)
				return false;
		} else if (!type_desc.equals(other.type_desc))
			return false;
		if (version_comment == null) {
			if (other.version_comment != null)
				return false;
		} else if (!version_comment.equals(other.version_comment))
			return false;
		if (version_date == null) {
			if (other.version_date != null)
				return false;
		} else if (!version_date.equals(other.version_date))
			return false;
		return true;
	}

}
