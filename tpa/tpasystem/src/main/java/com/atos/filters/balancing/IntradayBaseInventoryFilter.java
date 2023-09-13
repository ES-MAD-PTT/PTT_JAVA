package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntradayBaseInventoryFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date date;
	private BigDecimal zoneId;
	private BigDecimal modeId;	
	private BigDecimal systemId;
	private boolean isShipper;
	private boolean lastVersion;
	private String strLastVersion; 	// "Y"/"N" Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
	private String timestampVar;
	
	public IntradayBaseInventoryFilter() {
		this.date = null;
		this.zoneId = null;
		this.modeId = null;
		this.systemId = null;
		this.isShipper = false;
		this.lastVersion = true;
		this.strLastVersion = null;
		this.timestampVar = null;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}
	
	public BigDecimal getModeId() {
		return modeId;
	}

	public void setModeId(BigDecimal modeId) {
		this.modeId = modeId;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
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
}
