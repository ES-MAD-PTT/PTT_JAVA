package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
//import java.util.Calendar;
import java.util.Date;

public class BalanceInProgressReportFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1404494700248600473L;

	private BigDecimal shipperId;
	private Date gasDay;
	private Date generatedFrom;
	private Date generatedTo;
	private boolean lastVersion;
	private String strLastVersion; 	// "Y"/"N" Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
	private BigDecimal systemId;
	private String timestampVar;
	private boolean totalRows;
	private String strTotalRows; 
	private boolean totalAllShippers;
	private String strTotalAllShippers; 
	
	public BalanceInProgressReportFilter() {
		this.shipperId = null;
		this.gasDay = null;
		this.generatedFrom = null;
		this.generatedTo = null;		
		this.lastVersion = true;
		this.strLastVersion = null;
		this.systemId = null;
		this.timestampVar = null;
		this.totalRows = false;
		this.strTotalRows = null;
		this.totalAllShippers = false;
		this.strTotalAllShippers = null;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public Date getGeneratedFrom() {
		return generatedFrom;
	}

	public void setGeneratedFrom(Date generatedFrom) {
		this.generatedFrom = generatedFrom;
	}

	public Date getGeneratedTo() {
		return generatedTo;
	}

	public void setGeneratedTo(Date generatedTo) {
		this.generatedTo = generatedTo;
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
	
	public boolean isTotalRows() {
		return totalRows;
	}

	public void setTotalRows(boolean totalRows) {
		this.totalRows = totalRows;
	}

	public String getStrTotalRows() {
		return (totalRows? "Y" : "N");
	}
	
	public boolean isTotalAllShippers() {
		return totalAllShippers;
	}

	public void setTotalAllShippers(boolean totalAllShippers) {
		this.totalAllShippers = totalAllShippers;
	}

	public String getStrTotalAllShippers() {
		return (totalAllShippers? "Y" : "N");
	}

	// Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
//	public void setStrLastVersion(String strLastVersion) {
//		this.strLastVersion = strLastVersion;
//	}

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

	@Override
	public String toString() {
		return "BalanceInProgressReportFilter [shipperId=" + shipperId + ", gasDay=" + gasDay + ", generatedFrom="
				+ generatedFrom + ", generatedTo=" + generatedTo + ", lastVersion=" + lastVersion + ", strLastVersion="
				+ strLastVersion + ", totalRows=" + totalRows + ", strTotalRows="
						+ strTotalRows + ", totalAllShippers=" + totalAllShippers + ", strTotalAllShippers="
								+ strTotalAllShippers + ", systemId=" + systemId + ", timestampVar=" + timestampVar + "]";
	}
	
}
