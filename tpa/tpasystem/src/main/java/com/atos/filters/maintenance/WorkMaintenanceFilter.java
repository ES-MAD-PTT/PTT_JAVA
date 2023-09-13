package com.atos.filters.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WorkMaintenanceFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050310288149084268L;

	private String idn_maintenance;
	private BigDecimal idn_area;
	private BigDecimal idn_subarea;
	private Date start_date;
	private Date end_date;
	private BigDecimal idnActive;
	
	private String maintenance_subject;

	public String getIdn_maintenance() {
		return idn_maintenance;
	}

	public void setIdn_maintenance(String idn_maintenance) {
		this.idn_maintenance = idn_maintenance;
	}
	public BigDecimal getIdn_area() {
		return idn_area;
	}
	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}
	public BigDecimal getIdn_subarea() {
		return idn_subarea;
	}
	public void setIdn_subarea(BigDecimal idn_subarea) {
		this.idn_subarea = idn_subarea;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end_date == null) ? 0 : end_date.hashCode());
		result = prime * result + ((idn_area == null) ? 0 : idn_area.hashCode());
		result = prime * result + ((idn_maintenance == null) ? 0 : idn_maintenance.hashCode());
		result = prime * result + ((idn_subarea == null) ? 0 : idn_subarea.hashCode());
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
		WorkMaintenanceFilter other = (WorkMaintenanceFilter) obj;
		if (end_date == null) {
			if (other.end_date != null)
				return false;
		} else if (!end_date.equals(other.end_date))
			return false;
		if (idn_area == null) {
			if (other.idn_area != null)
				return false;
		} else if (!idn_area.equals(other.idn_area))
			return false;
		if (idn_maintenance == null) {
			if (other.idn_maintenance != null)
				return false;
		} else if (!idn_maintenance.equals(other.idn_maintenance))
			return false;
		if (idn_subarea == null) {
			if (other.idn_subarea != null)
				return false;
		} else if (!idn_subarea.equals(other.idn_subarea))
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
		return "WorkSubmissionFilter [idn_maintenance=" + idn_maintenance + ", idn_area=" + idn_area + ", idn_subarea="
				+ idn_subarea + ", start_date=" + start_date + ", end_date=" + end_date + "]";
	}

	public BigDecimal getIdnActive() {
		return idnActive;
	}

	public void setIdnActive(BigDecimal idnActive) {
		this.idnActive = idnActive;
	}

	public String getMaintenance_subject() {
		return maintenance_subject;
	}

	public void setMaintenance_subject(String maintenance_subject) {
		this.maintenance_subject = maintenance_subject;
	}
	
}
