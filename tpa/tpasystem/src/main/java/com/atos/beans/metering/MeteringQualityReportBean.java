package com.atos.beans.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class MeteringQualityReportBean implements Serializable {
	

	private static final long serialVersionUID = 6371860709834194903L;
	
	
	private Date gasDay;
	
	private String gasQualityParam;
	private String unit;
	
	private BigDecimal mixEastMin;
	private BigDecimal mixEastMax;
	
	private BigDecimal mixWestMin;
	private BigDecimal mixWestMax;
	
	private BigDecimal delEastMin;
	private BigDecimal delEastMax;
	
	private BigDecimal delWestMin;
	private BigDecimal delWestMax;
	
	private BigDecimal delEastWestMin;
	private BigDecimal delEastWestMax;
	
	
	public MeteringQualityReportBean() {
		this.gasDay= null;
		this.gasQualityParam = null;
		this.unit= null;
		
		this.mixEastMin= null;
		this.mixEastMax= null;
		
		this.mixWestMin= null;
		this.mixWestMax= null;
		
		this.delEastMin= null;
		this.delEastMax= null;
		
		this.delWestMin= null;
		this.delWestMax= null;
		
		this.delEastWestMin= null;
		this.delEastWestMax= null;
	}

	public Date getGasDay() {
		return gasDay;
	}
	
	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public String getGasQualityParam() {
		return gasQualityParam;
	}

	public void setGasQualityParam(String gasQualityParam) {
		this.gasQualityParam = gasQualityParam;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getMixEastMin() {
		return mixEastMin;
	}

	public void setMixEastMin(BigDecimal mixEastMin) {
		this.mixEastMin = mixEastMin;
	}

	public BigDecimal getMixEastMax() {
		return mixEastMax;
	}

	public void setMixEastMax(BigDecimal mixEastMax) {
		this.mixEastMax = mixEastMax;
	}

	public BigDecimal getMixWestMin() {
		return mixWestMin;
	}

	public void setMixWestMin(BigDecimal mixWestMin) {
		this.mixWestMin = mixWestMin;
	}

	public BigDecimal getMixWestMax() {
		return mixWestMax;
	}

	public void setMixWestMax(BigDecimal mixWestMax) {
		this.mixWestMax = mixWestMax;
	}

	public BigDecimal getDelEastMin() {
		return delEastMin;
	}

	public void setDelEastMin(BigDecimal delEastMin) {
		this.delEastMin = delEastMin;
	}

	public BigDecimal getDelEastMax() {
		return delEastMax;
	}

	public void setDelEastMax(BigDecimal delEastMax) {
		this.delEastMax = delEastMax;
	}

	public BigDecimal getDelWestMin() {
		return delWestMin;
	}

	public void setDelWestMin(BigDecimal delWestMin) {
		this.delWestMin = delWestMin;
	}

	public BigDecimal getDelWestMax() {
		return delWestMax;
	}

	public void setDelWestMax(BigDecimal delWestMax) {
		this.delWestMax = delWestMax;
	}

	public BigDecimal getDelEastWestMin() {
		return delEastWestMin;
	}

	public void setDelEastWestMin(BigDecimal delEastWestMin) {
		this.delEastWestMin = delEastWestMin;
	}

	public BigDecimal getDelEastWestMax() {
		return delEastWestMax;
	}

	public void setDelEastWestMax(BigDecimal delEastWestMax) {
		this.delEastWestMax = delEastWestMax;
	}

	@Override
	public String toString() {
		return "MeteringQualityReportBean [gasDay=" + gasDay + ", gasQualityParam=" + gasQualityParam + ", unit=" + unit
				+ ", mixEastMin=" + mixEastMin + ", mixEastMax=" + mixEastMax + ", mixWestMin=" + mixWestMin
				+ ", mixWestMax=" + mixWestMax + ", delEastMin=" + delEastMin + ", delEastMax=" + delEastMax
				+ ", delWestMin=" + delWestMin + ", delWestMax=" + delWestMax + ", delEastWestMin=" + delEastWestMin
				+ ", delEastWestMax=" + delEastWestMax + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delEastMax == null) ? 0 : delEastMax.hashCode());
		result = prime * result + ((delEastMin == null) ? 0 : delEastMin.hashCode());
		result = prime * result + ((delEastWestMax == null) ? 0 : delEastWestMax.hashCode());
		result = prime * result + ((delEastWestMin == null) ? 0 : delEastWestMin.hashCode());
		result = prime * result + ((delWestMax == null) ? 0 : delWestMax.hashCode());
		result = prime * result + ((delWestMin == null) ? 0 : delWestMin.hashCode());
		result = prime * result + ((gasDay == null) ? 0 : gasDay.hashCode());
		result = prime * result + ((gasQualityParam == null) ? 0 : gasQualityParam.hashCode());
		result = prime * result + ((mixEastMax == null) ? 0 : mixEastMax.hashCode());
		result = prime * result + ((mixEastMin == null) ? 0 : mixEastMin.hashCode());
		result = prime * result + ((mixWestMax == null) ? 0 : mixWestMax.hashCode());
		result = prime * result + ((mixWestMin == null) ? 0 : mixWestMin.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		MeteringQualityReportBean other = (MeteringQualityReportBean) obj;
		if (delEastMax == null) {
			if (other.delEastMax != null)
				return false;
		} else if (!delEastMax.equals(other.delEastMax))
			return false;
		if (delEastMin == null) {
			if (other.delEastMin != null)
				return false;
		} else if (!delEastMin.equals(other.delEastMin))
			return false;
		if (delEastWestMax == null) {
			if (other.delEastWestMax != null)
				return false;
		} else if (!delEastWestMax.equals(other.delEastWestMax))
			return false;
		if (delEastWestMin == null) {
			if (other.delEastWestMin != null)
				return false;
		} else if (!delEastWestMin.equals(other.delEastWestMin))
			return false;
		if (delWestMax == null) {
			if (other.delWestMax != null)
				return false;
		} else if (!delWestMax.equals(other.delWestMax))
			return false;
		if (delWestMin == null) {
			if (other.delWestMin != null)
				return false;
		} else if (!delWestMin.equals(other.delWestMin))
			return false;
		if (gasDay == null) {
			if (other.gasDay != null)
				return false;
		} else if (!gasDay.equals(other.gasDay))
			return false;
		if (gasQualityParam == null) {
			if (other.gasQualityParam != null)
				return false;
		} else if (!gasQualityParam.equals(other.gasQualityParam))
			return false;
		if (mixEastMax == null) {
			if (other.mixEastMax != null)
				return false;
		} else if (!mixEastMax.equals(other.mixEastMax))
			return false;
		if (mixEastMin == null) {
			if (other.mixEastMin != null)
				return false;
		} else if (!mixEastMin.equals(other.mixEastMin))
			return false;
		if (mixWestMax == null) {
			if (other.mixWestMax != null)
				return false;
		} else if (!mixWestMax.equals(other.mixWestMax))
			return false;
		if (mixWestMin == null) {
			if (other.mixWestMin != null)
				return false;
		} else if (!mixWestMin.equals(other.mixWestMin))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	
		
}
