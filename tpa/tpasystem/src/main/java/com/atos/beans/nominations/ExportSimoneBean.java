package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExportSimoneBean implements Serializable{

	
	/**
	 * ELECT f.zone_code,  nvl(f.point_code, f.concept_code) AS entity, f.area, f.subarea,  f.unit,  f.entry_exit,
		                decode(SUM(f.day_confirmed_quantity), 0, NULL, SUM(f.day_confirmed_quantity * f.wi) / SUM(f.day_confirmed_quantity)) AS wi,
		                decode(SUM(f.day_confirmed_quantity), 0, NULL, SUM(f.day_confirmed_quantity * f.hv) / SUM(f.day_confirmed_quantity)) AS hv,
		                f.hour_,       AVG(f.hour_confirmed_quantity * f.conversion_factor) AS hour_quantity,
		                AVG(f.day_confirmed_quantity * f.conversion_factor) AS day_quantity,
		                f.sort_value
		          FROM full_nomination f
		         WHERE f.unit_type NOT IN (SELECT type_code FROM aggregatable_units)
	 */
	private static final long serialVersionUID = -7010833792478488200L;
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
	private BigDecimal day_quantity;
	private BigDecimal sort_value;
	
	
	private BigDecimal hour_01;
	private BigDecimal hour_02;
	private BigDecimal hour_03;
	private BigDecimal hour_04;
	private BigDecimal hour_05;
	private BigDecimal hour_06;
	private BigDecimal hour_07;
	private BigDecimal hour_08;
	private BigDecimal hour_09;
	private BigDecimal hour_10;
	private BigDecimal hour_11;
	private BigDecimal hour_12;
	private BigDecimal hour_13;
	private BigDecimal hour_14;
	private BigDecimal hour_15;
	private BigDecimal hour_16;
	private BigDecimal hour_17;
	private BigDecimal hour_18;
	private BigDecimal hour_19;
	private BigDecimal hour_20;
	private BigDecimal hour_21;
	private BigDecimal hour_22;
	private BigDecimal hour_23;
	private BigDecimal hour_24;
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


	public BigDecimal getSg() {
		return sg;
	}


	public void setSg(BigDecimal sg) {
		this.sg = sg;
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


	public BigDecimal getDay_quantity() {
		return day_quantity;
	}


	public void setDay_quantity(BigDecimal day_quantity) {
		this.day_quantity = day_quantity;
	}


	public BigDecimal getSort_value() {
		return sort_value;
	}


	public void setSort_value(BigDecimal sort_value) {
		this.sort_value = sort_value;
	}


	public BigDecimal getHour_01() {
		return hour_01;
	}


	public void setHour_01(BigDecimal hour_01) {
		this.hour_01 = hour_01;
	}


	public BigDecimal getHour_02() {
		return hour_02;
	}


	public void setHour_02(BigDecimal hour_02) {
		this.hour_02 = hour_02;
	}


	public BigDecimal getHour_03() {
		return hour_03;
	}


	public void setHour_03(BigDecimal hour_03) {
		this.hour_03 = hour_03;
	}


	public BigDecimal getHour_04() {
		return hour_04;
	}


	public void setHour_04(BigDecimal hour_04) {
		this.hour_04 = hour_04;
	}


	public BigDecimal getHour_05() {
		return hour_05;
	}


	public void setHour_05(BigDecimal hour_05) {
		this.hour_05 = hour_05;
	}


	public BigDecimal getHour_06() {
		return hour_06;
	}


	public void setHour_06(BigDecimal hour_06) {
		this.hour_06 = hour_06;
	}


	public BigDecimal getHour_07() {
		return hour_07;
	}


	public void setHour_07(BigDecimal hour_07) {
		this.hour_07 = hour_07;
	}


	public BigDecimal getHour_08() {
		return hour_08;
	}


	public void setHour_08(BigDecimal hour_08) {
		this.hour_08 = hour_08;
	}


	public BigDecimal getHour_09() {
		return hour_09;
	}


	public void setHour_09(BigDecimal hour_09) {
		this.hour_09 = hour_09;
	}


	public BigDecimal getHour_10() {
		return hour_10;
	}


	public void setHour_10(BigDecimal hour_10) {
		this.hour_10 = hour_10;
	}


	public BigDecimal getHour_11() {
		return hour_11;
	}


	public void setHour_11(BigDecimal hour_11) {
		this.hour_11 = hour_11;
	}


	public BigDecimal getHour_12() {
		return hour_12;
	}


	public void setHour_12(BigDecimal hour_12) {
		this.hour_12 = hour_12;
	}


	public BigDecimal getHour_13() {
		return hour_13;
	}


	public void setHour_13(BigDecimal hour_13) {
		this.hour_13 = hour_13;
	}


	public BigDecimal getHour_14() {
		return hour_14;
	}


	public void setHour_14(BigDecimal hour_14) {
		this.hour_14 = hour_14;
	}


	public BigDecimal getHour_15() {
		return hour_15;
	}


	public void setHour_15(BigDecimal hour_15) {
		this.hour_15 = hour_15;
	}


	public BigDecimal getHour_16() {
		return hour_16;
	}


	public void setHour_16(BigDecimal hour_16) {
		this.hour_16 = hour_16;
	}


	public BigDecimal getHour_17() {
		return hour_17;
	}


	public void setHour_17(BigDecimal hour_17) {
		this.hour_17 = hour_17;
	}


	public BigDecimal getHour_18() {
		return hour_18;
	}


	public void setHour_18(BigDecimal hour_18) {
		this.hour_18 = hour_18;
	}


	public BigDecimal getHour_19() {
		return hour_19;
	}


	public void setHour_19(BigDecimal hour_19) {
		this.hour_19 = hour_19;
	}


	public BigDecimal getHour_20() {
		return hour_20;
	}


	public void setHour_20(BigDecimal hour_20) {
		this.hour_20 = hour_20;
	}


	public BigDecimal getHour_21() {
		return hour_21;
	}


	public void setHour_21(BigDecimal hour_21) {
		this.hour_21 = hour_21;
	}


	public BigDecimal getHour_22() {
		return hour_22;
	}


	public void setHour_22(BigDecimal hour_22) {
		this.hour_22 = hour_22;
	}


	public BigDecimal getHour_23() {
		return hour_23;
	}


	public void setHour_23(BigDecimal hour_23) {
		this.hour_23 = hour_23;
	}


	public BigDecimal getHour_24() {
		return hour_24;
	}


	public void setHour_24(BigDecimal hour_24) {
		this.hour_24 = hour_24;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((day_quantity == null) ? 0 : day_quantity.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((entry_exit == null) ? 0 : entry_exit.hashCode());
		result = prime * result + ((hour_ == null) ? 0 : hour_.hashCode());
		result = prime * result + ((hour_01 == null) ? 0 : hour_01.hashCode());
		result = prime * result + ((hour_02 == null) ? 0 : hour_02.hashCode());
		result = prime * result + ((hour_03 == null) ? 0 : hour_03.hashCode());
		result = prime * result + ((hour_04 == null) ? 0 : hour_04.hashCode());
		result = prime * result + ((hour_05 == null) ? 0 : hour_05.hashCode());
		result = prime * result + ((hour_06 == null) ? 0 : hour_06.hashCode());
		result = prime * result + ((hour_07 == null) ? 0 : hour_07.hashCode());
		result = prime * result + ((hour_08 == null) ? 0 : hour_08.hashCode());
		result = prime * result + ((hour_09 == null) ? 0 : hour_09.hashCode());
		result = prime * result + ((hour_10 == null) ? 0 : hour_10.hashCode());
		result = prime * result + ((hour_11 == null) ? 0 : hour_11.hashCode());
		result = prime * result + ((hour_12 == null) ? 0 : hour_12.hashCode());
		result = prime * result + ((hour_13 == null) ? 0 : hour_13.hashCode());
		result = prime * result + ((hour_14 == null) ? 0 : hour_14.hashCode());
		result = prime * result + ((hour_15 == null) ? 0 : hour_15.hashCode());
		result = prime * result + ((hour_16 == null) ? 0 : hour_16.hashCode());
		result = prime * result + ((hour_17 == null) ? 0 : hour_17.hashCode());
		result = prime * result + ((hour_18 == null) ? 0 : hour_18.hashCode());
		result = prime * result + ((hour_19 == null) ? 0 : hour_19.hashCode());
		result = prime * result + ((hour_20 == null) ? 0 : hour_20.hashCode());
		result = prime * result + ((hour_21 == null) ? 0 : hour_21.hashCode());
		result = prime * result + ((hour_22 == null) ? 0 : hour_22.hashCode());
		result = prime * result + ((hour_23 == null) ? 0 : hour_23.hashCode());
		result = prime * result + ((hour_24 == null) ? 0 : hour_24.hashCode());
		result = prime * result + ((hour_quantity == null) ? 0 : hour_quantity.hashCode());
		result = prime * result + ((hv == null) ? 0 : hv.hashCode());
		result = prime * result + ((sg == null) ? 0 : sg.hashCode());
		result = prime * result + ((is_warned == null) ? 0 : is_warned.hashCode());
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
		ExportSimoneBean other = (ExportSimoneBean) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (day_quantity == null) {
			if (other.day_quantity != null)
				return false;
		} else if (!day_quantity.equals(other.day_quantity))
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
		if (hour_01 == null) {
			if (other.hour_01 != null)
				return false;
		} else if (!hour_01.equals(other.hour_01))
			return false;
		if (hour_02 == null) {
			if (other.hour_02 != null)
				return false;
		} else if (!hour_02.equals(other.hour_02))
			return false;
		if (hour_03 == null) {
			if (other.hour_03 != null)
				return false;
		} else if (!hour_03.equals(other.hour_03))
			return false;
		if (hour_04 == null) {
			if (other.hour_04 != null)
				return false;
		} else if (!hour_04.equals(other.hour_04))
			return false;
		if (hour_05 == null) {
			if (other.hour_05 != null)
				return false;
		} else if (!hour_05.equals(other.hour_05))
			return false;
		if (hour_06 == null) {
			if (other.hour_06 != null)
				return false;
		} else if (!hour_06.equals(other.hour_06))
			return false;
		if (hour_07 == null) {
			if (other.hour_07 != null)
				return false;
		} else if (!hour_07.equals(other.hour_07))
			return false;
		if (hour_08 == null) {
			if (other.hour_08 != null)
				return false;
		} else if (!hour_08.equals(other.hour_08))
			return false;
		if (hour_09 == null) {
			if (other.hour_09 != null)
				return false;
		} else if (!hour_09.equals(other.hour_09))
			return false;
		if (hour_10 == null) {
			if (other.hour_10 != null)
				return false;
		} else if (!hour_10.equals(other.hour_10))
			return false;
		if (hour_11 == null) {
			if (other.hour_11 != null)
				return false;
		} else if (!hour_11.equals(other.hour_11))
			return false;
		if (hour_12 == null) {
			if (other.hour_12 != null)
				return false;
		} else if (!hour_12.equals(other.hour_12))
			return false;
		if (hour_13 == null) {
			if (other.hour_13 != null)
				return false;
		} else if (!hour_13.equals(other.hour_13))
			return false;
		if (hour_14 == null) {
			if (other.hour_14 != null)
				return false;
		} else if (!hour_14.equals(other.hour_14))
			return false;
		if (hour_15 == null) {
			if (other.hour_15 != null)
				return false;
		} else if (!hour_15.equals(other.hour_15))
			return false;
		if (hour_16 == null) {
			if (other.hour_16 != null)
				return false;
		} else if (!hour_16.equals(other.hour_16))
			return false;
		if (hour_17 == null) {
			if (other.hour_17 != null)
				return false;
		} else if (!hour_17.equals(other.hour_17))
			return false;
		if (hour_18 == null) {
			if (other.hour_18 != null)
				return false;
		} else if (!hour_18.equals(other.hour_18))
			return false;
		if (hour_19 == null) {
			if (other.hour_19 != null)
				return false;
		} else if (!hour_19.equals(other.hour_19))
			return false;
		if (hour_20 == null) {
			if (other.hour_20 != null)
				return false;
		} else if (!hour_20.equals(other.hour_20))
			return false;
		if (hour_21 == null) {
			if (other.hour_21 != null)
				return false;
		} else if (!hour_21.equals(other.hour_21))
			return false;
		if (hour_22 == null) {
			if (other.hour_22 != null)
				return false;
		} else if (!hour_22.equals(other.hour_22))
			return false;
		if (hour_23 == null) {
			if (other.hour_23 != null)
				return false;
		} else if (!hour_23.equals(other.hour_23))
			return false;
		if (hour_24 == null) {
			if (other.hour_24 != null)
				return false;
		} else if (!hour_24.equals(other.hour_24))
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
		if (sg == null) {
			if (other.sg != null)
				return false;
		} else if (!sg.equals(other.sg))
			return false;
		if (is_warned == null) {
			if (other.is_warned != null)
				return false;
		} else if (!is_warned.equals(other.is_warned))
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
				"zone_code;entity;area;subarea;unit;entry_exit;wi;hv;sg;hour_01;hour_02;hour_03;hour_04;hour_05;hour_06;hour_07;hour_08;hour_09;hour_10;hour_11;hour_12;hour_13;hour_14;hour_15;hour_16;hour_17;hour_18;hour_19;hour_20;hour_21;hour_22;hour_23;hour_24;day_quantity");
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
		builder.append((hour_01==null ? "" : hour_01.doubleValue())+";");
		builder.append((hour_02==null ? "" : hour_02.doubleValue())+";");
		builder.append((hour_03==null ? "" : hour_03.doubleValue())+";");
		builder.append((hour_04==null ? "" : hour_04.doubleValue())+";");
		builder.append((hour_05==null ? "" : hour_05.doubleValue())+";");
		builder.append((hour_06==null ? "" : hour_06.doubleValue())+";");
		builder.append((hour_07==null ? "" : hour_07.doubleValue())+";");
		builder.append((hour_08==null ? "" : hour_08.doubleValue())+";");
		builder.append((hour_09==null ? "" : hour_09.doubleValue())+";");
		builder.append((hour_10==null ? "" : hour_10.doubleValue())+";");
		builder.append((hour_11==null ? "" : hour_11.doubleValue())+";");
		builder.append((hour_12==null ? "" : hour_12.doubleValue())+";");
		builder.append((hour_13==null ? "" : hour_13.doubleValue())+";");
		builder.append((hour_14==null ? "" : hour_14.doubleValue())+";");
		builder.append((hour_15==null ? "" : hour_15.doubleValue())+";");
		builder.append((hour_16==null ? "" : hour_16.doubleValue())+";");
		builder.append((hour_17==null ? "" : hour_17.doubleValue())+";");
		builder.append((hour_18==null ? "" : hour_18.doubleValue())+";");
		builder.append((hour_19==null ? "" : hour_19.doubleValue())+";");
		builder.append((hour_20==null ? "" : hour_20.doubleValue())+";");
		builder.append((hour_21==null ? "" : hour_21.doubleValue())+";");
		builder.append((hour_22==null ? "" : hour_22.doubleValue())+";");
		builder.append((hour_23==null ? "" : hour_23.doubleValue())+";");
		builder.append((hour_24==null ? "" : hour_24.doubleValue())+";");
		builder.append((day_quantity==null ? "" : day_quantity.doubleValue()));
		return builder.toString();
	}


	@Override
	public String toString() {
		return "ExportSimoneBean [zone_code=" + zone_code + ", entity=" + entity + ", area=" + area + ", subarea="
				+ subarea + ", unit=" + unit + ", entry_exit=" + entry_exit + ", wi=" + wi + ", hv=" + hv + ",sg=" + sg + ", hour_="
				+ hour_ + ", hour_quantity=" + hour_quantity + ", day_quantity=" + day_quantity + ", sort_value="
				+ sort_value + ", hour_01=" + hour_01 + ", hour_02=" + hour_02 + ", hour_03=" + hour_03 + ", hour_04="
				+ hour_04 + ", hour_05=" + hour_05 + ", hour_06=" + hour_06 + ", hour_07=" + hour_07 + ", hour_08="
				+ hour_08 + ", hour_09=" + hour_09 + ", hour_10=" + hour_10 + ", hour_11=" + hour_11 + ", hour_12="
				+ hour_12 + ", hour_13=" + hour_13 + ", hour_14=" + hour_14 + ", hour_15=" + hour_15 + ", hour_16="
				+ hour_16 + ", hour_17=" + hour_17 + ", hour_18=" + hour_18 + ", hour_19=" + hour_19 + ", hour_20="
				+ hour_20 + ", hour_21=" + hour_21 + ", hour_22=" + hour_22 + ", hour_23=" + hour_23 + ", hour_24="
				+ hour_24 + ", total=" + total + ", is_warned=" + is_warned + "]";
	}
	
}
