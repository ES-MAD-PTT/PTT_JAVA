package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParkingAllocationFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5261722812223687450L;

	private Date gas_day;
	//private BigDecimal idn_zone;
	private BigDecimal[] idn_zone;
	
	public Date getGas_day() {
		return gas_day;
	}
	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}
	
	public BigDecimal[] getIdn_zone() {
		return idn_zone;
	}
	public void setIdn_zone(BigDecimal[] idn_zone) {
		this.idn_zone = idn_zone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gas_day == null) ? 0 : gas_day.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingAllocationFilter other = (ParkingAllocationFilter) obj;
		if (gas_day == null) {
			if (other.gas_day != null)
				return false;
		} else if (!gas_day.equals(other.gas_day))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ParkingAllocationFilter [gas_day=" + gas_day + ", idn_zone=" + idn_zone + "]";
	}
	
}
