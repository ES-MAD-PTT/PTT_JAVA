package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
//import java.util.Calendar;
import java.util.Date;

public class BalanceIntradayReportFilter implements Serializable {

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
	private String is_operator;
	
	public BalanceIntradayReportFilter() {
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
		this.is_operator= "N";
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

	public String getIs_operator() {
		return is_operator;
	}

	public void setIs_operator(String is_operator) {
		this.is_operator = is_operator;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BalanceIntradayReportFilter [shipperId=");
		builder.append(shipperId);
		builder.append(", gasDay=");
		builder.append(gasDay);
		builder.append(", generatedFrom=");
		builder.append(generatedFrom);
		builder.append(", generatedTo=");
		builder.append(generatedTo);
		builder.append(", lastVersion=");
		builder.append(lastVersion);
		builder.append(", strLastVersion=");
		builder.append(strLastVersion);
		builder.append(", systemId=");
		builder.append(systemId);
		builder.append(", timestampVar=");
		builder.append(timestampVar);
		builder.append(", totalRows=");
		builder.append(totalRows);
		builder.append(", strTotalRows=");
		builder.append(strTotalRows);
		builder.append(", totalAllShippers=");
		builder.append(totalAllShippers);
		builder.append(", strTotalAllShippers=");
		builder.append(strTotalAllShippers);
		builder.append(", is_operator=");
		builder.append(is_operator);
		builder.append("]");
		return builder.toString();
	}
	
}
