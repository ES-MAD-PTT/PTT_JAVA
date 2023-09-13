package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class AreaNominalCapacityBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_area_tech_capacity;
	private BigDecimal idn_area;
	private String area_code;
	private String area_desc;
	private BigDecimal technicalCapacity;
	private Date startDate;
	private Date endDate;
	private BigDecimal startYear;

	public AreaNominalCapacityBean() {
		super();
	}

	public AreaNominalCapacityBean(BigDecimal idn_area_tech_capacity, BigDecimal idn_area, String area_code,
			String area_desc, BigDecimal technicalCapacity, Date startDate, Date endDate) {
		super();
		this.idn_area_tech_capacity = idn_area_tech_capacity;
		this.idn_area = idn_area;
		this.area_code = area_code;
		this.area_desc = area_desc;
		this.technicalCapacity = technicalCapacity;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((area_code==null ? "" : area_code)+";");
		builder.append((technicalCapacity==null ? "" : technicalCapacity.doubleValue())+";");
		builder.append((startDate==null ? "" : sdf.format(startDate))+";");
		builder.append((endDate==null ? "" : sdf.format(endDate)));
		return builder.toString();
	}

	
	@Override
	public String toString() {
		return "AreaNominalCapacityBean [area_code=" + area_code + ", technicalCapacity=" + technicalCapacity
				+ ", startDate=" + startDate + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area_code == null) ? 0 : area_code.hashCode());
		result = prime * result + ((area_desc == null) ? 0 : area_desc.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((idn_area == null) ? 0 : idn_area.hashCode());
		result = prime * result + ((idn_area_tech_capacity == null) ? 0 : idn_area_tech_capacity.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((technicalCapacity == null) ? 0 : technicalCapacity.hashCode());
		return result;
	}

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"area_code;technicalCapacity;startDate;endDate");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AreaNominalCapacityBean other = (AreaNominalCapacityBean) obj;
		if (area_code == null) {
			if (other.area_code != null)
				return false;
		} else if (!area_code.equals(other.area_code))
			return false;
		if (area_desc == null) {
			if (other.area_desc != null)
				return false;
		} else if (!area_desc.equals(other.area_desc))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (idn_area == null) {
			if (other.idn_area != null)
				return false;
		} else if (!idn_area.equals(other.idn_area))
			return false;
		if (idn_area_tech_capacity == null) {
			if (other.idn_area_tech_capacity != null)
				return false;
		} else if (!idn_area_tech_capacity.equals(other.idn_area_tech_capacity))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (technicalCapacity == null) {
			if (other.technicalCapacity != null)
				return false;
		} else if (!technicalCapacity.equals(other.technicalCapacity))
			return false;
		return true;
	}

	public BigDecimal getIdn_area_tech_capacity() {
		return idn_area_tech_capacity;
	}

	public void setIdn_area_tech_capacity(BigDecimal idn_area_tech_capacity) {
		this.idn_area_tech_capacity = idn_area_tech_capacity;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getArea_desc() {
		return area_desc;
	}

	public void setArea_desc(String area_desc) {
		this.area_desc = area_desc;
	}

	public BigDecimal getTechnicalCapacity() {
		return technicalCapacity;
	}

	public void setTechnicalCapacity(BigDecimal technicalCapacity) {
		this.technicalCapacity = technicalCapacity;
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

	public BigDecimal getStartYear() {
		return startYear;
	}

	public void setStartYear(BigDecimal startYear) {

		this.startYear = startYear;

	}

}
