package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class NominationQualityGasWeekBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -858557028218211184L;
	private BigDecimal idn_zone;
	private BigDecimal idn_nomination;
	private BigDecimal idn_nomination_entity;
	private BigDecimal idn_nomination_concept;
	private String zone_code;
	private String supply_demand;
	private String area;
	private String point_id;
	private String concept;
	private String area_concept;
	private String cust_type;
	private String area_code;
	private String subarea;
	private String unit;
	private String entry_exit;
	private BigDecimal wi;
	private BigDecimal hv;
	private BigDecimal sg;
	private BigDecimal day_1;
	private BigDecimal day_2;
	private BigDecimal day_3;
	private BigDecimal day_4;
	private BigDecimal day_5;
	private BigDecimal day_6;
	private BigDecimal day_7;
	private String is_warned_day_1;
	private String is_warned_day_2;
	private String is_warned_day_3;
	private String is_warned_day_4;
	private String is_warned_day_5;
	private String is_warned_day_6;
	private String is_warned_day_7;
	private ArrayList<NominationFormulaBean> points = new ArrayList<NominationFormulaBean>();
	public BigDecimal getIdn_zone() {
		return idn_zone;
	}
	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}
	public BigDecimal getIdn_nomination() {
		return idn_nomination;
	}
	public void setIdn_nomination(BigDecimal idn_nomination) {
		this.idn_nomination = idn_nomination;
	}
	public BigDecimal getIdn_nomination_entity() {
		return idn_nomination_entity;
	}
	public void setIdn_nomination_entity(BigDecimal idn_nomination_entity) {
		this.idn_nomination_entity = idn_nomination_entity;
	}
	public BigDecimal getIdn_nomination_concept() {
		return idn_nomination_concept;
	}
	public void setIdn_nomination_concept(BigDecimal idn_nomination_concept) {
		this.idn_nomination_concept = idn_nomination_concept;
	}
	public String getZone_code() {
		return zone_code;
	}
	public void setZone_code(String zone_code) {
		this.zone_code = zone_code;
	}
	public String getSupply_demand() {
		return supply_demand;
	}
	public void setSupply_demand(String supply_demand) {
		this.supply_demand = supply_demand;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPoint_id() {
		return point_id;
	}
	public void setPoint_id(String point_id) {
		this.point_id = point_id;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getArea_concept() {
		return area_concept;
	}
	public void setArea_concept(String area_concept) {
		this.area_concept = area_concept;
	}
	public String getCust_type() {
		return cust_type;
	}
	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getSubarea() {
		return subarea;
	}
	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getEntry_exit() {
		return entry_exit;
	}
	public void setEntry_exit(String entry_exit) {
		this.entry_exit = entry_exit;
	}
	public BigDecimal getWi() {
		return wi;
	}
	public void setWi(BigDecimal wi) {
		this.wi = wi;
	}
	public BigDecimal getHv() {
		return hv;
	}
	public void setHv(BigDecimal hv) {
		this.hv = hv;
	}
	public BigDecimal getSg() {
		return sg;
	}
	public void setSg(BigDecimal sg) {
		this.sg = sg;
	}
	public BigDecimal getDay_1() {
		return day_1;
	}
	public void setDay_1(BigDecimal day_1) {
		this.day_1 = day_1;
	}
	public BigDecimal getDay_2() {
		return day_2;
	}
	public void setDay_2(BigDecimal day_2) {
		this.day_2 = day_2;
	}
	public BigDecimal getDay_3() {
		return day_3;
	}
	public void setDay_3(BigDecimal day_3) {
		this.day_3 = day_3;
	}
	public BigDecimal getDay_4() {
		return day_4;
	}
	public void setDay_4(BigDecimal day_4) {
		this.day_4 = day_4;
	}
	public BigDecimal getDay_5() {
		return day_5;
	}
	public void setDay_5(BigDecimal day_5) {
		this.day_5 = day_5;
	}
	public BigDecimal getDay_6() {
		return day_6;
	}
	public void setDay_6(BigDecimal day_6) {
		this.day_6 = day_6;
	}
	public BigDecimal getDay_7() {
		return day_7;
	}
	public void setDay_7(BigDecimal day_7) {
		this.day_7 = day_7;
	}
	public String getIs_warned_day_1() {
		return is_warned_day_1;
	}
	public void setIs_warned_day_1(String is_warned_day_1) {
		this.is_warned_day_1 = is_warned_day_1;
	}
	public String getIs_warned_day_2() {
		return is_warned_day_2;
	}
	public void setIs_warned_day_2(String is_warned_day_2) {
		this.is_warned_day_2 = is_warned_day_2;
	}
	public String getIs_warned_day_3() {
		return is_warned_day_3;
	}
	public void setIs_warned_day_3(String is_warned_day_3) {
		this.is_warned_day_3 = is_warned_day_3;
	}
	public String getIs_warned_day_4() {
		return is_warned_day_4;
	}
	public void setIs_warned_day_4(String is_warned_day_4) {
		this.is_warned_day_4 = is_warned_day_4;
	}
	public String getIs_warned_day_5() {
		return is_warned_day_5;
	}
	public void setIs_warned_day_5(String is_warned_day_5) {
		this.is_warned_day_5 = is_warned_day_5;
	}
	public String getIs_warned_day_6() {
		return is_warned_day_6;
	}
	public void setIs_warned_day_6(String is_warned_day_6) {
		this.is_warned_day_6 = is_warned_day_6;
	}
	public String getIs_warned_day_7() {
		return is_warned_day_7;
	}
	public void setIs_warned_day_7(String is_warned_day_7) {
		this.is_warned_day_7 = is_warned_day_7;
	}
	public ArrayList<NominationFormulaBean> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<NominationFormulaBean> points) {
		this.points = points;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((area_code == null) ? 0 : area_code.hashCode());
		result = prime * result + ((area_concept == null) ? 0 : area_concept.hashCode());
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result + ((cust_type == null) ? 0 : cust_type.hashCode());
		result = prime * result + ((day_1 == null) ? 0 : day_1.hashCode());
		result = prime * result + ((day_2 == null) ? 0 : day_2.hashCode());
		result = prime * result + ((day_3 == null) ? 0 : day_3.hashCode());
		result = prime * result + ((day_4 == null) ? 0 : day_4.hashCode());
		result = prime * result + ((day_5 == null) ? 0 : day_5.hashCode());
		result = prime * result + ((day_6 == null) ? 0 : day_6.hashCode());
		result = prime * result + ((day_7 == null) ? 0 : day_7.hashCode());
		result = prime * result + ((entry_exit == null) ? 0 : entry_exit.hashCode());
		result = prime * result + ((hv == null) ? 0 : hv.hashCode());
		result = prime * result + ((idn_nomination == null) ? 0 : idn_nomination.hashCode());
		result = prime * result + ((idn_nomination_concept == null) ? 0 : idn_nomination_concept.hashCode());
		result = prime * result + ((idn_nomination_entity == null) ? 0 : idn_nomination_entity.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((point_id == null) ? 0 : point_id.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((subarea == null) ? 0 : subarea.hashCode());
		result = prime * result + ((supply_demand == null) ? 0 : supply_demand.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((wi == null) ? 0 : wi.hashCode());
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
		NominationQualityGasWeekBean other = (NominationQualityGasWeekBean) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (area_code == null) {
			if (other.area_code != null)
				return false;
		} else if (!area_code.equals(other.area_code))
			return false;
		if (area_concept == null) {
			if (other.area_concept != null)
				return false;
		} else if (!area_concept.equals(other.area_concept))
			return false;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		if (cust_type == null) {
			if (other.cust_type != null)
				return false;
		} else if (!cust_type.equals(other.cust_type))
			return false;
		if (day_1 == null) {
			if (other.day_1 != null)
				return false;
		} else if (!day_1.equals(other.day_1))
			return false;
		if (day_2 == null) {
			if (other.day_2 != null)
				return false;
		} else if (!day_2.equals(other.day_2))
			return false;
		if (day_3 == null) {
			if (other.day_3 != null)
				return false;
		} else if (!day_3.equals(other.day_3))
			return false;
		if (day_4 == null) {
			if (other.day_4 != null)
				return false;
		} else if (!day_4.equals(other.day_4))
			return false;
		if (day_5 == null) {
			if (other.day_5 != null)
				return false;
		} else if (!day_5.equals(other.day_5))
			return false;
		if (day_6 == null) {
			if (other.day_6 != null)
				return false;
		} else if (!day_6.equals(other.day_6))
			return false;
		if (day_7 == null) {
			if (other.day_7 != null)
				return false;
		} else if (!day_7.equals(other.day_7))
			return false;
		if (entry_exit == null) {
			if (other.entry_exit != null)
				return false;
		} else if (!entry_exit.equals(other.entry_exit))
			return false;
		if (hv == null) {
			if (other.hv != null)
				return false;
		} else if (!hv.equals(other.hv))
			return false;
		if (idn_nomination == null) {
			if (other.idn_nomination != null)
				return false;
		} else if (!idn_nomination.equals(other.idn_nomination))
			return false;
		if (idn_nomination_concept == null) {
			if (other.idn_nomination_concept != null)
				return false;
		} else if (!idn_nomination_concept.equals(other.idn_nomination_concept))
			return false;
		if (idn_nomination_entity == null) {
			if (other.idn_nomination_entity != null)
				return false;
		} else if (!idn_nomination_entity.equals(other.idn_nomination_entity))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		if (point_id == null) {
			if (other.point_id != null)
				return false;
		} else if (!point_id.equals(other.point_id))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (subarea == null) {
			if (other.subarea != null)
				return false;
		} else if (!subarea.equals(other.subarea))
			return false;
		if (supply_demand == null) {
			if (other.supply_demand != null)
				return false;
		} else if (!supply_demand.equals(other.supply_demand))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (wi == null) {
			if (other.wi != null)
				return false;
		} else if (!wi.equals(other.wi))
			return false;
		if (zone_code == null) {
			if (other.zone_code != null)
				return false;
		} else if (!zone_code.equals(other.zone_code))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NominationQualityGasWeekBean [idn_zone=" + idn_zone + ", idn_nomination=" + idn_nomination
				+ ", idn_nomination_entity=" + idn_nomination_entity + ", idn_nomination_concept="
				+ idn_nomination_concept + ", zone_code=" + zone_code + ", supply_demand=" + supply_demand + ", area="
				+ area + ", point_id=" + point_id + ", concept=" + concept + ", area_concept=" + area_concept
				+ ", cust_type=" + cust_type + ", area_code=" + area_code + ", subarea=" + subarea + ", unit=" + unit
				+ ", entry_exit=" + entry_exit + ", wi=" + wi + ", hv=" + hv + ", day_1=" + day_1 + ", day_2=" + day_2
				+ ", day_3=" + day_3 + ", day_4=" + day_4 + ", day_5=" + day_5 + ", day_6=" + day_6 + ", day_7=" + day_7
				+ ", points=" + points + "]";
	}

}
