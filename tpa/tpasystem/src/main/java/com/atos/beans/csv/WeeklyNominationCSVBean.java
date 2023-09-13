package com.atos.beans.csv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeeklyNominationCSVBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3747918233623516202L;
	
	private BigDecimal idn_nomination;
	private String nomination_code;
	private BigDecimal nomination_version;
	private String user_group_id;
   	private String contract_code;
   	private Date start_date;
   	private Date end_date;
   	private String zone_code;
   	private String supply_demand;
   	private String area;
   	private String point_id;
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
   	private BigDecimal co2;
   	private BigDecimal c1;
   	private BigDecimal c2;
   	private BigDecimal c3;
   	private BigDecimal ic4;
   	private BigDecimal nc4;
   	private BigDecimal ic5;
   	private BigDecimal nc5;
   	private BigDecimal c6;
   	private BigDecimal c7;
   	private BigDecimal c8;
   	private BigDecimal n2;
   	private BigDecimal o2;
   	private BigDecimal h2s;
   	private BigDecimal s;
   	private BigDecimal hg;

   	
	public BigDecimal getIdn_nomination() {
		return idn_nomination;
	}
	public void setIdn_nomination(BigDecimal idn_nomination) {
		this.idn_nomination = idn_nomination;
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
	public String getUser_group_id() {
		return user_group_id;
	}
	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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
	public BigDecimal getCo2() {
		return co2;
	}
	public void setCo2(BigDecimal co2) {
		this.co2 = co2;
	}
	public BigDecimal getC1() {
		return c1;
	}
	public void setC1(BigDecimal c1) {
		this.c1 = c1;
	}
	public BigDecimal getC2() {
		return c2;
	}
	public void setC2(BigDecimal c2) {
		this.c2 = c2;
	}
	public BigDecimal getC3() {
		return c3;
	}
	public void setC3(BigDecimal c3) {
		this.c3 = c3;
	}
	public BigDecimal getIc4() {
		return ic4;
	}
	public void setIc4(BigDecimal ic4) {
		this.ic4 = ic4;
	}
	public BigDecimal getNc4() {
		return nc4;
	}
	public void setNc4(BigDecimal nc4) {
		this.nc4 = nc4;
	}
	public BigDecimal getIc5() {
		return ic5;
	}
	public void setIc5(BigDecimal ic5) {
		this.ic5 = ic5;
	}
	public BigDecimal getNc5() {
		return nc5;
	}
	public void setNc5(BigDecimal nc5) {
		this.nc5 = nc5;
	}
	public BigDecimal getC6() {
		return c6;
	}
	public void setC6(BigDecimal c6) {
		this.c6 = c6;
	}
	public BigDecimal getC7() {
		return c7;
	}
	public void setC7(BigDecimal c7) {
		this.c7 = c7;
	}
	public BigDecimal getC8() {
		return c8;
	}
	public void setC8(BigDecimal c8) {
		this.c8 = c8;
	}
	public BigDecimal getN2() {
		return n2;
	}
	public void setN2(BigDecimal n2) {
		this.n2 = n2;
	}
	public BigDecimal getO2() {
		return o2;
	}
	public void setO2(BigDecimal o2) {
		this.o2 = o2;
	}
	public BigDecimal getH2s() {
		return h2s;
	}
	public void setH2s(BigDecimal h2s) {
		this.h2s = h2s;
	}
	public BigDecimal getS() {
		return s;
	}
	public void setS(BigDecimal s) {
		this.s = s;
	}
	public BigDecimal getHg() {
		return hg;
	}
	public void setHg(BigDecimal hg) {
		this.hg = hg;
	}

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"nomination_code;nomination_version;user_group_id;contract_code;start_date;end_date;zone_code;supply_demand;area;point_id;cust_type;area_code;subarea;unit;entry_exit;wi;hv;sg;day_1;day_2;day_3;day_4;day_5;day_6;day_7;co2;c1;c2;c3;ic4;nc4;ic5;nc5;c6;c7;c8;n2;o2;h2s;s;hg");
		return builder.toString();
	}
	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((nomination_code==null ? "" : nomination_code)+";");
		builder.append((nomination_version==null ? "" : nomination_version.intValue())+";");
		builder.append((user_group_id ==null ? "" : user_group_id)+";");
		builder.append((contract_code==null ? "" : contract_code)+";");
		builder.append((start_date==null ? "" : sdf.format(start_date))+";");
		builder.append((end_date==null ? "" : sdf.format(end_date))+";");
		builder.append((zone_code==null ? "" : zone_code)+";");
		builder.append((supply_demand==null ? "" :supply_demand)+";");
		builder.append((area==null ? "": area)+";");
		builder.append((point_id==null ? "" : point_id)+";");
		builder.append((cust_type==null ? "" : cust_type)+";");
		builder.append((area_code==null ? "" : area_code)+";");
		builder.append((subarea==null ? "" : subarea)+";");
		builder.append((unit==null ? "" : unit)+";");
		builder.append((entry_exit==null ? "" :entry_exit)+";");
		builder.append((wi == null ? "" : wi.doubleValue())+";");
		builder.append((hv == null ? "" : hv.doubleValue())+";");
		builder.append((sg == null ? "" : sg.doubleValue())+";");
		builder.append((day_1 == null ? "" : day_1.doubleValue())+";");
		builder.append((day_2 == null ? "" : day_2.doubleValue())+";");
		builder.append((day_3 == null ? "" : day_3.doubleValue())+";");
		builder.append((day_4 == null ? "" : day_4.doubleValue())+";");
		builder.append((day_5 == null ? "" : day_5.doubleValue())+";");
		builder.append((day_6 == null ? "" : day_6.doubleValue())+";");
		builder.append((day_7 == null ? "" : day_7.doubleValue())+";");
		builder.append((co2== null ? "" : co2.doubleValue())+";");
		builder.append((c1 == null ? "" : c1.doubleValue())+";");
		builder.append((c2 == null ? "" : c2.doubleValue())+";");
		builder.append((c3 == null ? "" : c3.doubleValue())+";");
		builder.append((ic4 == null ? "" : ic4.doubleValue())+";");
		builder.append((nc4 == null ? "" : nc4.doubleValue())+";");
		builder.append((ic5 == null ? "" : ic5.doubleValue())+";");
		builder.append((nc5 == null ? "" : nc5.doubleValue())+";");
		builder.append((c6 == null ? "" : c6.doubleValue())+";");
		builder.append((c7 == null ? "" : c7.doubleValue())+";");
		builder.append((c8 == null ? "" : c8.doubleValue())+";");
		builder.append((n2 == null ? "" : n2.doubleValue())+";");
		builder.append((o2 == null ? "" : o2.doubleValue())+";");
		builder.append((h2s == null ? "" : h2s.doubleValue())+";");
		builder.append((s == null ? "" : s.doubleValue())+";");
		builder.append((hg == null ? "" : hg.doubleValue()));
		return builder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WeeklyNominationCSVBean [idn_nomination=");
		builder.append(idn_nomination);
		builder.append(", nomination_code=");
		builder.append(nomination_code);
		builder.append(", nomination_version=");
		builder.append(nomination_version);
		builder.append(", user_group_id=");
		builder.append(user_group_id);
		builder.append(", contract_code=");
		builder.append(contract_code);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", zone_code=");
		builder.append(zone_code);
		builder.append(", supply_demand=");
		builder.append(supply_demand);
		builder.append(", area=");
		builder.append(area);
		builder.append(", point_id=");
		builder.append(point_id);
		builder.append(", cust_type=");
		builder.append(cust_type);
		builder.append(", area_code=");
		builder.append(area_code);
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
		builder.append(", day_1=");
		builder.append(day_1);
		builder.append(", day_2=");
		builder.append(day_2);
		builder.append(", day_3=");
		builder.append(day_3);
		builder.append(", day_4=");
		builder.append(day_4);
		builder.append(", day_5=");
		builder.append(day_5);
		builder.append(", day_6=");
		builder.append(day_6);
		builder.append(", day_7=");
		builder.append(day_7);
		builder.append(", co2=");
		builder.append(co2);
		builder.append(", c1=");
		builder.append(c1);
		builder.append(", c2=");
		builder.append(c2);
		builder.append(", c3=");
		builder.append(c3);
		builder.append(", ic4=");
		builder.append(ic4);
		builder.append(", nc4=");
		builder.append(nc4);
		builder.append(", ic5=");
		builder.append(ic5);
		builder.append(", nc5=");
		builder.append(nc5);
		builder.append(", c6=");
		builder.append(c6);
		builder.append(", c7=");
		builder.append(c7);
		builder.append(", c8=");
		builder.append(c8);
		builder.append(", n2=");
		builder.append(n2);
		builder.append(", o2=");
		builder.append(o2);
		builder.append(", h2s=");
		builder.append(h2s);
		builder.append(", s=");
		builder.append(s);
		builder.append(", hg=");
		builder.append(hg);
		builder.append("]");
		return builder.toString();
	}
	
   	
   	
}
