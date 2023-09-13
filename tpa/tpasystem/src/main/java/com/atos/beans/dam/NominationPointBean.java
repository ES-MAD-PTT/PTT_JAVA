package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.atos.beans.UserAudBean;

public class NominationPointBean extends UserAudBean implements Serializable {

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
	private BigDecimal latitude;
	private BigDecimal longitude;
	private BigDecimal minPressure;
	private BigDecimal maxPressure;
	private Date startDate;
	private Date endDate;
	private BigDecimal nominalCapacity;
	private String systemCode;
	private String zone;

	public NominationPointBean(BigDecimal idn_system_point, BigDecimal idn_area, BigDecimal idn_zone,
			BigDecimal idn_system_point_group, BigDecimal idn_system_point_param, BigDecimal idn_system_point_type,
			BigDecimal idn_pipeline_system, String id, String name, String pointType, String area, BigDecimal latitude,
			BigDecimal longitude, BigDecimal minPressure, BigDecimal maxPressure, Date startDate, Date endDate,
			BigDecimal nominalCapacity, String systemCode, String zone) {
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
		this.latitude = latitude;
		this.longitude = longitude;
		this.minPressure = minPressure;
		this.maxPressure = maxPressure;
		this.startDate = startDate;
		this.endDate = endDate;
		this.nominalCapacity = nominalCapacity;
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

	public BigDecimal getNominalCapacity() {
		return nominalCapacity;
	}

	public void setNominalCapacity(BigDecimal nominalCapacity) {
		this.nominalCapacity = nominalCapacity;
	}

	public NominationPointBean() {
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

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getMinPressure() {
		return minPressure;
	}

	public void setMinPressure(BigDecimal minPressure) {
		this.minPressure = minPressure;
	}

	public BigDecimal getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(BigDecimal maxPressure) {
		this.maxPressure = maxPressure;
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
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", minPressure=" + minPressure
				+ ", maxPressure=" + maxPressure + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", nominalCapacity=" + nominalCapacity + ", systemCode=" + systemCode + ", zone=" + zone + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((maxPressure == null) ? 0 : maxPressure.hashCode());
		result = prime * result + ((minPressure == null) ? 0 : minPressure.hashCode());
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
		NominationPointBean other = (NominationPointBean) obj;
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
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (maxPressure == null) {
			if (other.maxPressure != null)
				return false;
		} else if (!maxPressure.equals(other.maxPressure))
			return false;
		if (minPressure == null) {
			if (other.minPressure != null)
				return false;
		} else if (!minPressure.equals(other.minPressure))
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
