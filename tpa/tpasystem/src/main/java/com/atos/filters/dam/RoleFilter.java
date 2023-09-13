package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoleFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	
	//private String system;
	private String role;
	private BigDecimal idn_system;//offshore
	


	public RoleFilter() {
		//this.system = null;
		this.role= null;
		this.idn_system=null;
	}

	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}

	

	public BigDecimal getIdn_system() {
		return idn_system;
	}



	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}



	@Override
	public String toString() {
		return "RoleFilter [role=" + role + ", idn_system=" + idn_system + "]";
	}
	
	
	
}