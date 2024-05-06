package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntradayAccImbalanceInventoryFormBean implements Serializable {

	private static final long serialVersionUID = -97280511251817359L;
	
	private Date gasday;
	private BigDecimal idn_allocation;
	private String user;
	
	public Date getGasday() {
		return gasday;
	}
	public void setGasday(Date gasday) {
		this.gasday = gasday;
	}
	public BigDecimal getIdn_allocation() {
		return idn_allocation;
	}
	public void setIdn_allocation(BigDecimal idn_allocation) {
		this.idn_allocation = idn_allocation;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IntradayAccImbalanceInventoryFormBean [gasday=");
		builder.append(gasday);
		builder.append(", idn_allocation=");
		builder.append(idn_allocation);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}
	
	
}
