package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParkingAllocationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4623311938841499418L;

	private BigDecimal idn_zone;
	private String zone_code;
	private BigDecimal idn_shipper;
	private String shipper_code;
	private String short_name;
	private String contract_code;
	private String nomination_code;
	private BigDecimal nomination_version;
	private BigDecimal unpark_nominated;
	private BigDecimal park_nominated;
	private BigDecimal park_allocated;
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
	public BigDecimal getIdn_shipper() {
		return idn_shipper;
	}
	public void setIdn_shipper(BigDecimal idn_shipper) {
		this.idn_shipper = idn_shipper;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getNomination_code() {
		return nomination_code;
	}
	public void setNomination_code(String nomination_code) {
		this.nomination_code = nomination_code;
	}
	public BigDecimal getNomination_version() {
		return nomination_version;
	}
	public void setNomination_version(BigDecimal nomination_version) {
		this.nomination_version = nomination_version;
	}
	public BigDecimal getUnpark_nominated() {
		return unpark_nominated;
	}
	public void setUnpark_nominated(BigDecimal unpark_nominated) {
		this.unpark_nominated = unpark_nominated;
	}
	public BigDecimal getPark_nominated() {
		return park_nominated;
	}
	public void setPark_nominated(BigDecimal park_nominated) {
		this.park_nominated = park_nominated;
	}
	public BigDecimal getPark_allocated() {
		return park_allocated;
	}
	public void setPark_allocated(BigDecimal park_allocated) {
		this.park_allocated = park_allocated;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contract_code == null) ? 0 : contract_code.hashCode());
		result = prime * result + ((idn_shipper == null) ? 0 : idn_shipper.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((nomination_code == null) ? 0 : nomination_code.hashCode());
		result = prime * result + ((nomination_version == null) ? 0 : nomination_version.hashCode());
		result = prime * result + ((park_allocated == null) ? 0 : park_allocated.hashCode());
		result = prime * result + ((park_nominated == null) ? 0 : park_nominated.hashCode());
		result = prime * result + ((shipper_code == null) ? 0 : shipper_code.hashCode());
		result = prime * result + ((unpark_nominated == null) ? 0 : unpark_nominated.hashCode());
		result = prime * result + ((zone_code == null) ? 0 : zone_code.hashCode());
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
		ParkingAllocationBean other = (ParkingAllocationBean) obj;
		if (contract_code == null) {
			if (other.contract_code != null)
				return false;
		} else if (!contract_code.equals(other.contract_code))
			return false;
		if (idn_shipper == null) {
			if (other.idn_shipper != null)
				return false;
		} else if (!idn_shipper.equals(other.idn_shipper))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		if (nomination_code == null) {
			if (other.nomination_code != null)
				return false;
		} else if (!nomination_code.equals(other.nomination_code))
			return false;
		if (nomination_version == null) {
			if (other.nomination_version != null)
				return false;
		} else if (!nomination_version.equals(other.nomination_version))
			return false;
		if (park_allocated == null) {
			if (other.park_allocated != null)
				return false;
		} else if (!park_allocated.equals(other.park_allocated))
			return false;
		if (park_nominated == null) {
			if (other.park_nominated != null)
				return false;
		} else if (!park_nominated.equals(other.park_nominated))
			return false;
		if (shipper_code == null) {
			if (other.shipper_code != null)
				return false;
		} else if (!shipper_code.equals(other.shipper_code))
			return false;
		if (unpark_nominated == null) {
			if (other.unpark_nominated != null)
				return false;
		} else if (!unpark_nominated.equals(other.unpark_nominated))
			return false;
		if (zone_code == null) {
			if (other.zone_code != null)
				return false;
		} else if (!zone_code.equals(other.zone_code))
			return false;
		return true;
	}
	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"idn_zone;zone_code;shipper_code;contract_code;nomination_code;nomination_version;unpark_nominated;park_nominated;park_allocated");
		return builder.toString();
	}
	
	public String toCSV() {
		StringBuilder builder = new StringBuilder();
		builder.append((idn_zone==null ? "" : idn_zone.intValue())+";");
		builder.append((zone_code==null ? "" : zone_code)+";");
		builder.append((shipper_code==null ? "" : shipper_code)+";");
		builder.append((contract_code==null ? "" : contract_code)+";");
		builder.append((nomination_code==null ? "" : nomination_code)+";");
		builder.append((nomination_version==null ? "" : nomination_version.intValue())+";");
		builder.append((unpark_nominated==null ? "" : unpark_nominated.doubleValue())+";");
		builder.append((park_nominated==null ? "" : park_nominated.doubleValue())+";");
		builder.append((park_allocated==null ? "" : park_allocated.doubleValue()));
		return builder.toString();
	}

	
	@Override
	public String toString() {
		return "ParkingAllocationBean [idn_zone=" + idn_zone + ", zone_code=" + zone_code + ", idn_shipper="
				+ idn_shipper + ", shipper_code=" + shipper_code + ", short_name=" + short_name + ", contract_code="
				+ contract_code + ", nomination_code=" + nomination_code + ", nomination_version=" + nomination_version
				+ ", unpark_nominated=" + unpark_nominated + ", park_nominated=" + park_nominated + ", park_allocated="
				+ park_allocated + "]";
	}

}
