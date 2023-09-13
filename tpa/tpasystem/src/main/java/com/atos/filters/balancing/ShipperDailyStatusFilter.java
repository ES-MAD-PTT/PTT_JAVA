package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShipperDailyStatusFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 495390586861607800L;

	private BigDecimal shipperId;
	private Date fromDate;
	private BigDecimal systemId;	
	
	public ShipperDailyStatusFilter() {
		this.shipperId = null;
		this.fromDate = null;
		this.systemId = null;
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
		this.fromDate = fromDate;
	}

	/*	public void setFromDate(Date fromDate) {
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
*/
	
	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "ShipperDailyStatusFilter [shipperId=" + shipperId + ", fromDate=" + fromDate + ", systemId=" + systemId
				+ "]";
	}
}
