package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class BalanceReportFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 495390586861607800L;

	private BigDecimal shipperId;
	private Date fromDate;
	private Date toDate;
	private BigDecimal systemId;
	private boolean totalRows;
	private String strTotalRows; 
	private boolean totalAllShippers;
	private String strTotalAllShippers; 
	
	public BalanceReportFilter() {
		this.shipperId = null;
		this.fromDate = null;
		this.toDate = null;
		this.systemId = null;
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		//Ante cualquier fecha especificada por el usuario, se filtra siempre por el dia 1 de mes.

		if(fromDate!=null){
			Calendar tmpCal1 = Calendar.getInstance();
			tmpCal1.setTime(fromDate);
			tmpCal1.set(Calendar.DATE, 1);	// Se coge el primer dia de mes.		
			this.fromDate = tmpCal1.getTime();
			
			// Al guardar fromDate se guarda tambien toDate.
			Calendar tmpCal2 = Calendar.getInstance();
			tmpCal2.setTime(fromDate);
			tmpCal2.add(Calendar.MONTH, 1);		// Se pasa al primer dia del siguiente mes y se resta 1 dia.
			tmpCal2.set(Calendar.DATE, 1);
			tmpCal2.add(Calendar.DATE, -1);
			this.toDate = tmpCal2.getTime();
		}
		else { 		// Para el caso en que el parametro de entrada sea nulo.
					// Cuando el usuario borra el componente en pantalla.
			this.fromDate = null;
			this.toDate = null;
		}
	}

	public Date getToDate() {
		return toDate;
	}

	// Se devolvera siempre el ultimo dia de mes a partir del primer dia de mes de la fecha fromDate
//	public void setToDate(Date toDate) {
//		this.toDate = toDate;
//	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
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

	@Override
	public String toString() {
		return "BalanceReportFilter [shipperId=" + shipperId + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", totalRows=" + totalRows + ", strTotalRows="
				+ strTotalRows + ", totalAllShippers=" + totalAllShippers + ", strTotalAllShippers="
						+ strTotalAllShippers + ", systemId=" + systemId + "]";
	}
}
