package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class LinePackRequirementBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_linepack_requirement;
	private BigDecimal idn_zone;
	private String zone_code;
	private String zone_desc;
	private BigDecimal linepack;
	private Date startDate;
	private Date endDate;

	public LinePackRequirementBean() {
		super();
	}

	public LinePackRequirementBean(BigDecimal idn_linepack_requirement, BigDecimal idn_zone, String zone_code,
			String zone_desc, BigDecimal linepack, Date startDate, Date endDate) {
		super();
		this.idn_linepack_requirement = idn_linepack_requirement;
		this.idn_zone = idn_zone;
		this.zone_code = zone_code;
		this.zone_desc = zone_desc;
		this.linepack = linepack;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public BigDecimal getIdn_linepack_requirement() {
		return idn_linepack_requirement;
	}

	public void setIdn_linepack_requirement(BigDecimal idn_linepack_requirement) {
		this.idn_linepack_requirement = idn_linepack_requirement;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	public String getZone_code() {
		return zone_code;
	}

	public void setZone_code(String zone_code) {
		this.zone_code = zone_code;
	}

	public String getZone_desc() {
		return zone_desc;
	}

	public void setZone_desc(String zone_desc) {
		this.zone_desc = zone_desc;
	}

	public BigDecimal getLinepack() {
		return linepack;
	}

	public void setLinepack(BigDecimal linepack) {
		this.linepack = linepack;
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
		return "LinePackRequirement [idn_zone=" + idn_zone + ", linepack=" + linepack + ", startDate=" + startDate
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((idn_linepack_requirement == null) ? 0 : idn_linepack_requirement.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((linepack == null) ? 0 : linepack.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((zone_code == null) ? 0 : zone_code.hashCode());
		result = prime * result + ((zone_desc == null) ? 0 : zone_desc.hashCode());
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
		LinePackRequirementBean other = (LinePackRequirementBean) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (idn_linepack_requirement == null) {
			if (other.idn_linepack_requirement != null)
				return false;
		} else if (!idn_linepack_requirement.equals(other.idn_linepack_requirement))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		if (linepack == null) {
			if (other.linepack != null)
				return false;
		} else if (!linepack.equals(other.linepack))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (zone_code == null) {
			if (other.zone_code != null)
				return false;
		} else if (!zone_code.equals(other.zone_code))
			return false;
		if (zone_desc == null) {
			if (other.zone_desc != null)
				return false;
		} else if (!zone_desc.equals(other.zone_desc))
			return false;
		return true;
	}

}
