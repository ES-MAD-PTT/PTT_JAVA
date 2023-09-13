package com.atos.filters.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class ScadaAlarmsFilter implements Serializable{

	
	private static final long serialVersionUID = -1216440936544071784L;
	
	private String[] status;
	private Date startDate;
	private Date endDate;
	private String[] origin;
	
	private BigDecimal idn_system;//offshore
	
	
	public ScadaAlarmsFilter() {
		this.status=null;
		this.startDate=null;
		this.endDate=null;
		this.origin=null;
		this.idn_system=null;
		
	}
	
	
	public String[] getStatus() {
		return status;
	}
	public void setStatus(String[] status) {
		this.status = status;
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
	
	
	public String[] getOrigin() {
		return origin;
	}
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}
	
	
	
	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((idn_system == null) ? 0 : idn_system.hashCode());
		result = prime * result + Arrays.hashCode(origin);
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + Arrays.hashCode(status);
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
		ScadaAlarmsFilter other = (ScadaAlarmsFilter) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (idn_system == null) {
			if (other.idn_system != null)
				return false;
		} else if (!idn_system.equals(other.idn_system))
			return false;
		if (!Arrays.equals(origin, other.origin))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (!Arrays.equals(status, other.status))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ScadaAlarmsFilter [status=" + Arrays.toString(status) + ", startDate=" + startDate + ", endDate="
				+ endDate + ", origin=" + Arrays.toString(origin) + ", idn_system=" + idn_system + "]";
	}
		
	
	
}
