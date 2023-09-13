package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.atos.beans.UserAudBean;

public class OperatorBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_user_group;
	private BigDecimal idn_operator;

	private String id;
	private String name;
	private String division;
	private String telephone;
	private String fax;
	private String email;
	private Date startDate;
	private Date endDate;

	public OperatorBean() {
		super();
	}

	public OperatorBean(BigDecimal idn_user_group, BigDecimal idn_operator, String id, String name, String division,
			String telephone, String fax, String email, Date startDate, Date endDate) {
		super();
		this.idn_user_group = idn_user_group;
		this.idn_operator = idn_operator;
		this.id = id;
		this.name = name;
		this.division = division;
		this.telephone = telephone;
		this.fax = fax;
		this.email = email;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public BigDecimal getIdn_operator() {
		return idn_operator;
	}

	public void setIdn_operator(BigDecimal idn_operator) {
		this.idn_operator = idn_operator;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((division == null) ? 0 : division.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
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
		OperatorBean other = (OperatorBean) obj;
		if (division == null) {
			if (other.division != null)
				return false;
		} else if (!division.equals(other.division))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OperatorBean [idn_user_group=" + idn_user_group + ", idn_operator=" + idn_operator + ", id=" + id
				+ ", name=" + name + ", division=" + division + ", telephone=" + telephone + ", fax=" + fax + ", email="
				+ email + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
