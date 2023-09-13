package com.atos.filters.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class MeteringQualityReportFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 689265227361385489L;

	
	
	private Date gasDay;
	
	
	
	public MeteringQualityReportFilter() {
		this.gasDay = null;
		
	}


	public Date getGasDay() {
		return gasDay;
	}


	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}


	@Override
	public String toString() {
		return "MeteringQualityReportFilter [gasDay=" + gasDay + "]";
	}


	
}
