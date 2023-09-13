package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExportSimoneFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3929974414344800858L;
	private Date start_date;
	private BigDecimal idn_pipeline;

	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public BigDecimal getIdn_pipeline() {
		return idn_pipeline;
	}
	public void setIdn_pipeline(BigDecimal idn_pipeline) {
		this.idn_pipeline = idn_pipeline;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idn_pipeline == null) ? 0 : idn_pipeline.hashCode());
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
		ExportSimoneFilter other = (ExportSimoneFilter) obj;
		if (idn_pipeline == null) {
			if (other.idn_pipeline != null)
				return false;
		} else if (!idn_pipeline.equals(other.idn_pipeline))
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
		builder.append("ExportSimoneFilter [start_date=");
		builder.append(start_date);
		builder.append(", idn_pipeline=");
		builder.append(idn_pipeline);
		builder.append("]");
		return builder.toString();
	}
	
}
