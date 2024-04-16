package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntradayAccImbalanceInventoryFilter implements Serializable{
	
	private static final long serialVersionUID = -1404494700248600473L;

	private Date gasDay;
	private boolean lastVersion;
	private String strLastVersion; 	// "Y"/"N" Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
	private BigDecimal systemId;
	private String timestampVar;
	private List<String> timestampVarList = new ArrayList<>();
	
	public IntradayAccImbalanceInventoryFilter() {
		this.gasDay = null;	
		this.lastVersion = true;
		this.strLastVersion = null;
		this.systemId = null;
		this.timestampVar = null;
		this.timestampVarList = null;
	}

	public IntradayAccImbalanceInventoryFilter(Date gasDay, boolean lastVersion, String strLastVersion,
			BigDecimal systemId, String timestampVar, List<String> timestampVarList) {
		super();
		this.gasDay = gasDay;
		this.lastVersion = lastVersion;
		this.strLastVersion = strLastVersion;
		this.systemId = systemId;
		this.timestampVar = timestampVar;
		this.timestampVarList = timestampVarList;
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

	public List<String> getTimestampVarList() {
		return timestampVarList;
	}

	public void setTimestampVarList(List<String> timestampVarList) {
		this.timestampVarList = timestampVarList;
	}

}
