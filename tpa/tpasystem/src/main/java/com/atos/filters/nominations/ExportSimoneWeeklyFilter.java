package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExportSimoneWeeklyFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2161375842133535516L;
	private Date start_date;
	private BigDecimal idn_pipeline_system;
	
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}
	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idn_pipeline_system == null) ? 0 : idn_pipeline_system.hashCode());
		result = prime * result + ((start_date == null) ? 0 : start_date.hashCode());
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
		ExportSimoneWeeklyFilter other = (ExportSimoneWeeklyFilter) obj;
		if (idn_pipeline_system == null) {
			if (other.idn_pipeline_system != null)
				return false;
		} else if (!idn_pipeline_system.equals(other.idn_pipeline_system))
			return false;
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExportSimoneWeeklyFilter [start_date=");
		builder.append(start_date);
		builder.append(", idn_pipeline_system=");
		builder.append(idn_pipeline_system);
		builder.append("]");
		return builder.toString();
	}

	
}
