package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.primefaces.model.DualListModel;

import com.atos.beans.UserAudBean;

public class GuestUserBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_user;
	private BigDecimal idn_user_group;
	private BigDecimal idn_profile;

	protected String id;
	protected String name;
	protected String shipperOperator;
	protected String telephone;
	protected String address;
	protected String fax;
	protected String email;
	protected String password;
	protected String salt;
	protected Date startDate;
	protected Date endDate;
	protected String comments;

	// roles del usuario es para <p:pickList> de la pantalla
	protected DualListModel<String> rolesUsu;

	public GuestUserBean() {
		super();
	}

	public GuestUserBean(BigDecimal idn_user, BigDecimal idn_user_group, BigDecimal idn_profile, String id, String name,
			String shipperOperator, String telephone, String address, String fax, String email, String password,
			String salt, Date startDate, Date endDate, DualListModel<String> rolesUsu, String comments) {
		super();
		this.idn_user = idn_user;
		this.idn_user_group = idn_user_group;
		this.idn_profile = idn_profile;
		this.id = id;
		this.name = name;
		this.shipperOperator = shipperOperator;
		this.telephone = telephone;
		this.address = address;
		this.fax = fax;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rolesUsu = rolesUsu;
		this.comments = comments;
	}

	public DualListModel<String> getRolesUsu() {
		return rolesUsu;
	}

	public void setRolesUsu(DualListModel<String> rolesUsu) {
		this.rolesUsu = rolesUsu;
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

	public String getShipperOperator() {
		return shipperOperator;
	}

	public void setShipperOperator(String shipperOperator) {
		this.shipperOperator = shipperOperator;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public BigDecimal getIdn_user() {
		return idn_user;
	}

	public void setIdn_user(BigDecimal idn_user) {
		this.idn_user = idn_user;
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public BigDecimal getIdn_profile() {
		return idn_profile;
	}

	public void setIdn_profile(BigDecimal idn_profile) {
		this.idn_profile = idn_profile;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "GuestUserBean [idn_user=" + idn_user + ", idn_user_group=" + idn_user_group + ", idn_profile="
				+ idn_profile + ", id=" + id + ", name=" + name + ", shipperOperator=" + shipperOperator
				+ ", telephone=" + telephone + ", address=" + address + ", fax=" + fax + ", email=" + email
				+ ", password=" + password + ", salt=" + salt + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", comments=" + comments + ", rolesUsu=" + rolesUsu + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idn_profile == null) ? 0 : idn_profile.hashCode());
		result = prime * result + ((idn_user == null) ? 0 : idn_user.hashCode());
		result = prime * result + ((idn_user_group == null) ? 0 : idn_user_group.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((rolesUsu == null) ? 0 : rolesUsu.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		result = prime * result + ((shipperOperator == null) ? 0 : shipperOperator.hashCode());
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
		GuestUserBean other = (GuestUserBean) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
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
		if (idn_profile == null) {
			if (other.idn_profile != null)
				return false;
		} else if (!idn_profile.equals(other.idn_profile))
			return false;
		if (idn_user == null) {
			if (other.idn_user != null)
				return false;
		} else if (!idn_user.equals(other.idn_user))
			return false;
		if (idn_user_group == null) {
			if (other.idn_user_group != null)
				return false;
		} else if (!idn_user_group.equals(other.idn_user_group))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (rolesUsu == null) {
			if (other.rolesUsu != null)
				return false;
		} else if (!rolesUsu.equals(other.rolesUsu))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		if (shipperOperator == null) {
			if (other.shipperOperator != null)
				return false;
		} else if (!shipperOperator.equals(other.shipperOperator))
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

}
