package com.atos.filters.balancing;

import java.math.BigDecimal;
import java.util.Date;

public class InstructedOperationFlowShippersFilter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date date;
	private BigDecimal shipperId;
	private BigDecimal zoneId;
	private boolean isShipper;
	private boolean lastVersion;
	private String strLastVersion; 	// "Y"/"N" Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
	private String timestampVar;
	private BigDecimal systemId;
	
	public InstructedOperationFlowShippersFilter() {
		this.date = null;
		this.shipperId = null;
		this.zoneId = null;
		this.systemId = null;
		this.lastVersion = true;
		this.strLastVersion = null;
		this.timestampVar = null;
		this.isShipper = false;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	public boolean isShipper() {
		return isShipper;
	}

	public void setIsShipper(boolean isShipper) {
		this.isShipper = isShipper;
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

	public String getTimestampVar() {
		return timestampVar;
	}

	public void setTimestampVar(String timestampVar) {
		this.timestampVar = timestampVar;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}
}
