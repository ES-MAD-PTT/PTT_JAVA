package com.atos.filters.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class TagnameManagementFilter implements Serializable{

	
	private static final long serialVersionUID = -1216440936544071784L;
	
//	private String scadaTagName;
	private BigDecimal idnScadaTagName;
	private BigDecimal idPonit;
	private BigDecimal idLabel;
	private BigDecimal idn_system;//offshore
	private String binary;
	
	private BigDecimal idn_user;
	private String permission_code;
	
	public BigDecimal getIdnScadaTagName() {
		return idnScadaTagName;
	}


	public void setIdnScadaTagName(BigDecimal idnScadaTagName) {
		this.idnScadaTagName = idnScadaTagName;
	}


	public BigDecimal getIdPonit() {
		return idPonit;
	}


	public void setIdPonit(BigDecimal idPonit) {
		this.idPonit = idPonit;
	}


	public BigDecimal getIdLabel() {
		return idLabel;
	}


	public void setIdLabel(BigDecimal idLabel) {
		this.idLabel = idLabel;
	}
	
	public TagnameManagementFilter() {
		this.idnScadaTagName=null;
		this.idPonit=null;
		this.idLabel=null;
		this.idn_system=null;
		
	}
	
	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}


	public BigDecimal getIdn_user() {
		return idn_user;
	}


	public void setIdn_user(BigDecimal idn_user) {
		this.idn_user = idn_user;
	}


	

	public String getPermission_code() {
		return permission_code;
	}


	public void setPermission_code(String permission_code) {
		this.permission_code = permission_code;
	}


	public String getBinary() {
		return binary;
	}


	public void setBinary(String binary) {
		this.binary = binary;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((binary == null) ? 0 : binary.hashCode());
		result = prime * result + ((idLabel == null) ? 0 : idLabel.hashCode());
		result = prime * result + ((idPonit == null) ? 0 : idPonit.hashCode());
		result = prime * result + ((idn_system == null) ? 0 : idn_system.hashCode());
		result = prime * result + ((idn_user == null) ? 0 : idn_user.hashCode());
		result = prime * result + ((permission_code == null) ? 0 : permission_code.hashCode());
		result = prime * result + ((idnScadaTagName == null) ? 0 : idnScadaTagName.hashCode());
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
		TagnameManagementFilter other = (TagnameManagementFilter) obj;
		if (binary == null) {
			if (other.binary != null)
				return false;
		} else if (!binary.equals(other.binary))
			return false;
		if (idLabel == null) {
			if (other.idLabel != null)
				return false;
		} else if (!idLabel.equals(other.idLabel))
			return false;
		if (idPonit == null) {
			if (other.idPonit != null)
				return false;
		} else if (!idPonit.equals(other.idPonit))
			return false;
		if (idn_system == null) {
			if (other.idn_system != null)
				return false;
		} else if (!idn_system.equals(other.idn_system))
			return false;
		if (idn_user == null) {
			if (other.idn_user != null)
				return false;
		} else if (!idn_user.equals(other.idn_user))
			return false;
		if (permission_code == null) {
			if (other.permission_code != null)
				return false;
		} else if (!permission_code.equals(other.permission_code))
			return false;
		if (idnScadaTagName == null) {
			if (other.idnScadaTagName != null)
				return false;
		} else if (!idnScadaTagName.equals(other.idnScadaTagName))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TagnameManagementFilter [idnScadaTagName=" + idnScadaTagName + ", idPonit=" + idPonit + ", idLabel=" + idLabel
				+ ", idn_system=" + idn_system + ", binary=" + binary + ", idn_user=" + idn_user + ", permission_code="
				+ permission_code + "]";
	}


	

	
	
	
}
