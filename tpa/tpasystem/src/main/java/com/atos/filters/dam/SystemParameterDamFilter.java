package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SystemParameterDamFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;

	
	private BigDecimal idn_parameter;
	private BigDecimal idn_parameter_module;
	private Date startDate;
	private Date endDate;
	
	

	public SystemParameterDamFilter() {
		
		this.idn_parameter= null;
		this.idn_parameter_module=null;
		this.startDate = null;
		this.endDate = null;
	}

	public BigDecimal getIdn_parameter() {
		return idn_parameter;
	}


	public void setIdn_parameter(BigDecimal idn_parameter) {
		this.idn_parameter = idn_parameter;
	}


	public BigDecimal getIdn_parameter_module() {
		return idn_parameter_module;
	}

	public void setIdn_parameter_module(BigDecimal idn_parameter_module) {
		this.idn_parameter_module = idn_parameter_module;
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
	public String toString() {
		return "SystemParameterDamFilter [idn_parameter=" + idn_parameter + ", idn_parameter_module="
				+ idn_parameter_module + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

		
	
}
