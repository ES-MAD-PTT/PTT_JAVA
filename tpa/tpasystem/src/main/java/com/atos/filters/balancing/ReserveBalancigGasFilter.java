package com.atos.filters.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class ReserveBalancigGasFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8741334525216688454L;
	
	private BigDecimal shipperId;
	private Date fromDate;
	private Date toDate;
	private BigDecimal idnUser;
	private BigDecimal reserveBalId;
	private boolean shipper;
	private BigDecimal idnSystem;
	

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
		else{
			this.fromDate = null;
		}
	}

	public Date getToDate() {
		return toDate;
	}


	@Override
	public String toString() {
		return "ReserveBalancigGasFilter [shipperId=" + shipperId + ", fromDate=" + fromDate + "]";
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getIdnUser() {
		return idnUser;
	}

	public void setIdnUser(BigDecimal idnUser) {
		this.idnUser = idnUser;
	}

	public BigDecimal getReserveBalId() {
		return reserveBalId;
	}

	public void setReserveBalId(BigDecimal reserveBalId) {
		this.reserveBalId = reserveBalId;
	}

	public boolean isShipper() {
		return shipper;
	}

	public void setShipper(boolean shipper) {
		this.shipper = shipper;
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}
	

}
