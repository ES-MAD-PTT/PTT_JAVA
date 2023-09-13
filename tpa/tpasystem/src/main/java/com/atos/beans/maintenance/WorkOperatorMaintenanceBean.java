package com.atos.beans.maintenance;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class WorkOperatorMaintenanceBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1989754402458295525L;
	
	private BigDecimal idn_maintenance_subarea;
	private BigDecimal idn_maintenance;
	private String area;
	private BigDecimal idn_area;
	private String subarea;
	private BigDecimal idn_subarea;
	private BigDecimal capacity_affected;
	private String insert_group;
	
	private String maintenance_subject; //CH736
	
	public BigDecimal getIdn_maintenance_subarea() {
		return idn_maintenance_subarea;
	}
	public void setIdn_maintenance_subarea(BigDecimal idn_maintenance_subarea) {
		this.idn_maintenance_subarea = idn_maintenance_subarea;
	}
	public BigDecimal getIdn_maintenance() {
		return idn_maintenance;
	}
	public void setIdn_maintenance(BigDecimal idn_maintenance) {
		this.idn_maintenance = idn_maintenance;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public BigDecimal getIdn_area() {
		return idn_area;
	}
	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}
	public String getSubarea() {
		return subarea;
	}
	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}
	public BigDecimal getIdn_subarea() {
		return idn_subarea;
	}
	public void setIdn_subarea(BigDecimal idn_subarea) {
		this.idn_subarea = idn_subarea;
	}
	public BigDecimal getCapacity_affected() {
		return capacity_affected;
	}
	public void setCapacity_affected(BigDecimal capacity_affected) {
		this.capacity_affected = capacity_affected;
	}
	public String getInsert_group() {
		return insert_group;
	}
	public void setInsert_group(String insert_group) {
		this.insert_group = insert_group;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((capacity_affected == null) ? 0 : capacity_affected.hashCode());
		result = prime * result + ((idn_area == null) ? 0 : idn_area.hashCode());
		result = prime * result + ((idn_maintenance == null) ? 0 : idn_maintenance.hashCode());
		result = prime * result + ((idn_maintenance_subarea == null) ? 0 : idn_maintenance_subarea.hashCode());
		result = prime * result + ((idn_subarea == null) ? 0 : idn_subarea.hashCode());
		result = prime * result + ((insert_group == null) ? 0 : insert_group.hashCode());
		result = prime * result + ((subarea == null) ? 0 : subarea.hashCode());
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
		WorkOperatorMaintenanceBean other = (WorkOperatorMaintenanceBean) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (capacity_affected == null) {
			if (other.capacity_affected != null)
				return false;
		} else if (!capacity_affected.equals(other.capacity_affected))
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
		if (idn_maintenance_subarea == null) {
			if (other.idn_maintenance_subarea != null)
				return false;
		} else if (!idn_maintenance_subarea.equals(other.idn_maintenance_subarea))
			return false;
		if (idn_subarea == null) {
			if (other.idn_subarea != null)
				return false;
		} else if (!idn_subarea.equals(other.idn_subarea))
			return false;
		if (insert_group == null) {
			if (other.insert_group != null)
				return false;
		} else if (!insert_group.equals(other.insert_group))
			return false;
		if (subarea == null) {
			if (other.subarea != null)
				return false;
		} else if (!subarea.equals(other.subarea))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "WorkOperatorMaintenanceBean [idn_maintenance_subarea=" + idn_maintenance_subarea + ", idn_maintenance="
				+ idn_maintenance + ", area=" + area + ", idn_area=" + idn_area + ", subarea=" + subarea
				+ ", idn_subarea=" + idn_subarea + ", capacity_affected=" + capacity_affected + ", insert_group="
				+ insert_group + "]";
	}
	public String getMaintenance_subject() {
		return maintenance_subject;
	}
	public void setMaintenance_subject(String maintenance_subject) {
		this.maintenance_subject = maintenance_subject;
	}
	
	
}
