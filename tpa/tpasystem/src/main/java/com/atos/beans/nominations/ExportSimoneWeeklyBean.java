package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExportSimoneWeeklyBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6665778439013336418L;

	private String zone_code;
	private String entity;
	private String area;
	private String subarea;
	private String unit;
	private String entry_exit;
	private BigDecimal wi;
	private BigDecimal hv;
	private BigDecimal sg;
	private BigDecimal hour_;
	private BigDecimal hour_quantity;
	private BigDecimal day_0quantity;
	private BigDecimal sort_value;
	
	
	private BigDecimal day_01;
	private BigDecimal day_02;
	private BigDecimal day_03;
	private BigDecimal day_04;
	private BigDecimal day_05;
	private BigDecimal day_06;
	private BigDecimal day_07;
	private BigDecimal total;
	private String is_warned;

	
	public String getZone_code() {
		return zone_code;
	}


	public void setZone_code(String zone_code) {
		this.zone_code = zone_code;
	}


	public String getEntity() {
		return entity;
	}


	public void setEntity(String entity) {
		this.entity = entity;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
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


	public BigDecimal getHour_() {
		return hour_;
	}


	public void setHour_(BigDecimal hour_) {
		this.hour_ = hour_;
	}


	public BigDecimal getHour_quantity() {
		return hour_quantity;
	}


	public void setHour_quantity(BigDecimal hour_quantity) {
		this.hour_quantity = hour_quantity;
	}


	public BigDecimal getDay_0quantity() {
		return day_0quantity;
	}


	public void setDay_0quantity(BigDecimal day_0quantity) {
		this.day_0quantity = day_0quantity;
	}


	public BigDecimal getSort_value() {
		return sort_value;
	}


	public void setSort_value(BigDecimal sort_value) {
		this.sort_value = sort_value;
	}


	public BigDecimal getTotal() {
		return total;
	}


	public void setTotal(BigDecimal total) {
		this.total = total;
	}


	public String getIs_warned() {
		return is_warned;
	}


	public void setIs_warned(String is_warned) {
		this.is_warned = is_warned;
	}


	public BigDecimal getSg() {
		return sg;
	}


	public void setSg(BigDecimal sg) {
		this.sg = sg;
	}


	public BigDecimal getDay_01() {
		return day_01;
	}


	public void setDay_01(BigDecimal day_01) {
		this.day_01 = day_01;
	}


	public BigDecimal getDay_02() {
		return day_02;
	}


	public void setDay_02(BigDecimal day_02) {
		this.day_02 = day_02;
	}


	public BigDecimal getDay_03() {
		return day_03;
	}


	public void setDay_03(BigDecimal day_03) {
		this.day_03 = day_03;
	}


	public BigDecimal getDay_04() {
		return day_04;
	}


	public void setDay_04(BigDecimal day_04) {
		this.day_04 = day_04;
	}


	public BigDecimal getDay_05() {
		return day_05;
	}


	public void setDay_05(BigDecimal day_05) {
		this.day_05 = day_05;
	}


	public BigDecimal getDay_06() {
		return day_06;
	}


	public void setDay_06(BigDecimal day_06) {
		this.day_06 = day_06;
	}


	public BigDecimal getDay_07() {
		return day_07;
	}


	public void setDay_07(BigDecimal day_07) {
		this.day_07 = day_07;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((day_01 == null) ? 0 : day_01.hashCode());
		result = prime * result + ((day_02 == null) ? 0 : day_02.hashCode());
		result = prime * result + ((day_03 == null) ? 0 : day_03.hashCode());
		result = prime * result + ((day_04 == null) ? 0 : day_04.hashCode());
		result = prime * result + ((day_05 == null) ? 0 : day_05.hashCode());
		result = prime * result + ((day_06 == null) ? 0 : day_06.hashCode());
		result = prime * result + ((day_07 == null) ? 0 : day_07.hashCode());
		result = prime * result + ((day_0quantity == null) ? 0 : day_0quantity.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((entry_exit == null) ? 0 : entry_exit.hashCode());
		result = prime * result + ((hour_ == null) ? 0 : hour_.hashCode());
		result = prime * result + ((hour_quantity == null) ? 0 : hour_quantity.hashCode());
		result = prime * result + ((hv == null) ? 0 : hv.hashCode());
		result = prime * result + ((is_warned == null) ? 0 : is_warned.hashCode());
		result = prime * result + ((sg == null) ? 0 : sg.hashCode());
		result = prime * result + ((sort_value == null) ? 0 : sort_value.hashCode());
		result = prime * result + ((subarea == null) ? 0 : subarea.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		ExportSimoneWeeklyBean other = (ExportSimoneWeeklyBean) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (day_01 == null) {
			if (other.day_01 != null)
				return false;
		} else if (!day_01.equals(other.day_01))
			return false;
		if (day_02 == null) {
			if (other.day_02 != null)
				return false;
		} else if (!day_02.equals(other.day_02))
			return false;
		if (day_03 == null) {
			if (other.day_03 != null)
				return false;
		} else if (!day_03.equals(other.day_03))
			return false;
		if (day_04 == null) {
			if (other.day_04 != null)
				return false;
		} else if (!day_04.equals(other.day_04))
			return false;
		if (day_05 == null) {
			if (other.day_05 != null)
				return false;
		} else if (!day_05.equals(other.day_05))
			return false;
		if (day_06 == null) {
			if (other.day_06 != null)
				return false;
		} else if (!day_06.equals(other.day_06))
			return false;
		if (day_07 == null) {
			if (other.day_07 != null)
				return false;
		} else if (!day_07.equals(other.day_07))
			return false;
		if (day_0quantity == null) {
			if (other.day_0quantity != null)
				return false;
		} else if (!day_0quantity.equals(other.day_0quantity))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (entry_exit == null) {
			if (other.entry_exit != null)
				return false;
		} else if (!entry_exit.equals(other.entry_exit))
			return false;
		if (hour_ == null) {
			if (other.hour_ != null)
				return false;
		} else if (!hour_.equals(other.hour_))
			return false;
		if (hour_quantity == null) {
			if (other.hour_quantity != null)
				return false;
		} else if (!hour_quantity.equals(other.hour_quantity))
			return false;
		if (hv == null) {
			if (other.hv != null)
				return false;
		} else if (!hv.equals(other.hv))
			return false;
		if (is_warned == null) {
			if (other.is_warned != null)
				return false;
		} else if (!is_warned.equals(other.is_warned))
			return false;
		if (sg == null) {
			if (other.sg != null)
				return false;
		} else if (!sg.equals(other.sg))
			return false;
		if (sort_value == null) {
			if (other.sort_value != null)
				return false;
		} else if (!sort_value.equals(other.sort_value))
			return false;
		if (subarea == null) {
			if (other.subarea != null)
				return false;
		} else if (!subarea.equals(other.subarea))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
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

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"zone_code;entity;area;subarea;unit;entry_exit;wi;hv;sg;day_01;day_02;day_03;day_04;day_05;day_06;day_07");
		return builder.toString();
	}


	public String toCSV() {
		StringBuilder builder = new StringBuilder();
		builder.append((zone_code==null ? "" : zone_code)+";");
		builder.append((entity==null ? "" : entity)+";");
		builder.append((area==null ? "" : area)+";");
		builder.append((subarea==null ? "" : subarea)+";");
		builder.append((unit==null ? "" : unit)+";");
		builder.append((entry_exit==null ? "" : entry_exit)+";");
		builder.append((wi==null ? "" : wi.doubleValue())+";");
		builder.append((hv==null ? "" : hv.doubleValue())+";");
		builder.append((sg==null ? "" : sg.doubleValue())+";");
		builder.append((day_01==null ? "" : day_01.doubleValue())+";");
		builder.append((day_02==null ? "" : day_02.doubleValue())+";");
		builder.append((day_03==null ? "" : day_03.doubleValue())+";");
		builder.append((day_04==null ? "" : day_04.doubleValue())+";");
		builder.append((day_05==null ? "" : day_05.doubleValue())+";");
		builder.append((day_06==null ? "" : day_06.doubleValue())+";");
		builder.append((day_07==null ? "" : day_07.doubleValue()));
		return builder.toString();
	}

/*	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExportSimoneWeeklyBean [zone_code=");
		builder.append(zone_code);
		builder.append(", entity=");
		builder.append(entity);
		builder.append(", area=");
		builder.append(area);
		builder.append(", subarea=");
		builder.append(subarea);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", entry_exit=");
		builder.append(entry_exit);
		builder.append(", wi=");
		builder.append(wi);
		builder.append(", hv=");
		builder.append(hv);
		builder.append(", sg=");
		builder.append(sg);
		builder.append(", hour_=");
		builder.append(hour_);
		builder.append(", hour_quantity=");
		builder.append(hour_quantity);
		builder.append(", day_0quantity=");
		builder.append(day_0quantity);
		builder.append(", sort_value=");
		builder.append(sort_value);
		builder.append(", day_01=");
		builder.append(day_01);
		builder.append(", day_02=");
		builder.append(day_02);
		builder.append(", day_03=");
		builder.append(day_03);
		builder.append(", day_04=");
		builder.append(day_04);
		builder.append(", day_05=");
		builder.append(day_05);
		builder.append(", day_06=");
		builder.append(day_06);
		builder.append(", day_07=");
		builder.append(day_07);
		builder.append(", total=");
		builder.append(total);
		builder.append(", is_warned=");
		builder.append(is_warned);
		builder.append("]");
		return builder.toString();
	}


*/
}
