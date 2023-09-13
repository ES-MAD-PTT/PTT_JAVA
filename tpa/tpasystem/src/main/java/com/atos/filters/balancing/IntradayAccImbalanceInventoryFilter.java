package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntradayAccImbalanceInventoryFilter implements Serializable{
	
	private static final long serialVersionUID = -1404494700248600473L;

	private Date gasDay;
	private boolean lastVersion;
	private String strLastVersion; 	// "Y"/"N" Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
	private BigDecimal systemId;
	private String timestampVar;
	
	public IntradayAccImbalanceInventoryFilter() {
		this.gasDay = null;	
		this.lastVersion = true;
		this.strLastVersion = null;
		this.systemId = null;
		this.timestampVar = null;
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public boolean isLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(boolean lastVersion) {
		this.lastVersion = lastVersion;
	}

	public String getStrLastVersion() {
		return (lastVersion? "Y" : "N");
	}
	
	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	public String getTimestampVar() {
		return timestampVar;
	}

	public void setTimestampVar(String timestampVar) {
		this.timestampVar = timestampVar;
	}

}
