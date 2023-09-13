package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ShrinkageFactorBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_shrinkage_factor;
	private BigDecimal idn_zone;
	private String zone_code;
	private String zone_desc;
	private BigDecimal sF;
	private Date startDate;
	private Date endDate;

	public ShrinkageFactorBean() {

		super();
		this.idn_shrinkage_factor = null;
		this.idn_zone = null;
		this.zone_code = null;
		this.zone_desc = null;
		this.sF = null;
		this.startDate = null;
		this.endDate = null;
	}

	public ShrinkageFactorBean(BigDecimal idn_shrinkage_factor, BigDecimal idn_zone, String zone_code, String zone_desc,
			BigDecimal sF, Date startDate, Date endDate) {
		super();
		this.idn_shrinkage_factor = idn_shrinkage_factor;
		this.idn_zone = idn_zone;
		this.zone_code = zone_code;
		this.zone_desc = zone_desc;
		this.sF = sF;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public BigDecimal getIdn_shrinkage_factor() {
		return idn_shrinkage_factor;
	}

	public void setIdn_shrinkage_factor(BigDecimal idn_shrinkage_factor) {
		this.idn_shrinkage_factor = idn_shrinkage_factor;
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

	public BigDecimal getsF() {
		return sF;
	}

	public void setsF(BigDecimal sF) {
		this.sF = sF;
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
		return "ShrinkageFactor [idn_zone=" + idn_zone + ", sF=" + sF + ", startDate=" + startDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((idn_shrinkage_factor == null) ? 0 : idn_shrinkage_factor.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((sF == null) ? 0 : sF.hashCode());
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
		ShrinkageFactorBean other = (ShrinkageFactorBean) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (idn_shrinkage_factor == null) {
			if (other.idn_shrinkage_factor != null)
				return false;
		} else if (!idn_shrinkage_factor.equals(other.idn_shrinkage_factor))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		if (sF == null) {
			if (other.sF != null)
				return false;
		} else if (!sF.equals(other.sF))
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
