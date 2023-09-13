package com.atos.filters.dam;

import java.io.Serializable;
import java.util.Date;

public class PimsGraphicMenuFilter implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7401309811129185191L;
	
	private Date startDate;
	private Date endDate;
	
	

	public PimsGraphicMenuFilter() {
		
		this.startDate = null;
		this.endDate = null;
	}

	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PmisGraphicMenuFilter [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}


	
}
