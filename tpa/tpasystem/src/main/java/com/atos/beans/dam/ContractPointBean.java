package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.atos.beans.UserAudBean;

public class ContractPointBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_system_point;
	private BigDecimal idn_area;
	private BigDecimal idn_zone;
	private BigDecimal idn_system_point_group;
	private BigDecimal idn_system_point_param;
	private BigDecimal idn_system_point_type;
	private BigDecimal idn_pipeline_system;

	private String id;
	private String name;
	private String pointType;
	private String area;
	private Date startDate;
	private Date endDate;
	private String systemCode;
	private String zone;

	public ContractPointBean(BigDecimal idn_system_point, BigDecimal idn_area, BigDecimal idn_zone,
			BigDecimal idn_system_point_group, BigDecimal idn_system_point_param, BigDecimal idn_system_point_type,
			BigDecimal idn_pipeline_system, String id, String name, String pointType, String area,
			Date startDate, Date endDate,
			String systemCode, String zone) {
		super();
		this.idn_system_point = idn_system_point;
		this.idn_area = idn_area;
		this.idn_zone = idn_zone;
		this.idn_system_point_group = idn_system_point_group;
		this.idn_system_point_param = idn_system_point_param;
		this.idn_system_point_type = idn_system_point_type;
		this.idn_pipeline_system = idn_pipeline_system;
		this.id = id;
		this.name = name;
		this.pointType = pointType;
		this.area = area;
		this.startDate = startDate;
		this.endDate = endDate;
		this.systemCode = systemCode;
		this.zone = zone;
	}

	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}

	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	public BigDecimal getIdn_system_point_group() {
		return idn_system_point_group;
	}

	public void setIdn_system_point_group(BigDecimal idn_system_point_group) {
		this.idn_system_point_group = idn_system_point_group;
	}

	public BigDecimal getIdn_system_point_param() {
		return idn_system_point_param;
	}

	public void setIdn_system_point_param(BigDecimal idn_system_point_param) {
		this.idn_system_point_param = idn_system_point_param;
	}

	public BigDecimal getIdn_system_point_type() {
		return idn_system_point_type;
	}

	public void setIdn_system_point_type(BigDecimal idn_system_point_type) {
		this.idn_system_point_type = idn_system_point_type;
	}

	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}

	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public ContractPointBean() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
		return "NominationPointBean [idn_system_point=" + idn_system_point + ", idn_area=" + idn_area + ", idn_zone="
				+ idn_zone + ", idn_system_point_group=" + idn_system_point_group + ", idn_system_point_param="
				+ idn_system_point_param + ", idn_system_point_type=" + idn_system_point_type + ", idn_pipeline_system="
				+ idn_pipeline_system + ", id=" + id + ", name=" + name + ", pointType=" + pointType + ", area=" + area
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", systemCode=" + systemCode + ", zone=" + zone + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pointType == null) ? 0 : pointType.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		ContractPointBean other = (ContractPointBean) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pointType == null) {
			if (other.pointType != null)
				return false;
		} else if (!pointType.equals(other.pointType))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

}
