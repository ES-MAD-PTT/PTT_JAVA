package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class ShippersNominationReportsWeeklyBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7010833792478488200L;
	private Date gas_day;
	private BigDecimal idn_contract;
	private String contract_code;
	private BigDecimal idn_nomination;
	private String zone_code;
	private String supply_demand;
	private String area;
	private String point_id;
	private String wi_hv;
	private String area_concept;
	private String cust_type;
	private String area_code;
	private String subarea;
	private String unit;
	private String entry_exit;
	private BigDecimal wi;
	private String is_warned_wi;
	private BigDecimal hv;
	private String is_warned_hv;
	private BigDecimal sg;
	private String is_warned_sg;
	private BigDecimal day_1;
	private BigDecimal day_2;
	private BigDecimal day_3;
	private BigDecimal day_4;
	private BigDecimal day_5;
	private BigDecimal day_6;
	private BigDecimal day_7;
	private BigDecimal total;
	private String is_warned;
	private String is_warned_day_1;
	private String is_warned_day_2;
	private String is_warned_day_3;
	private String is_warned_day_4;
	private String is_warned_day_5;
	private String is_warned_day_6;
	private String is_warned_day_7;	
	private String is_warned_quantity;
	
	public ShippersNominationReportsWeeklyBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShippersNominationReportsWeeklyBean(Date gas_day, BigDecimal idn_contract, String contract_code,
			BigDecimal idn_nomination, String zone_code, String supply_demand, String area, String point_id,
			String wi_hv, String area_concept, String cust_type, String area_code, String subarea, String unit,
			String entry_exit, BigDecimal wi, String is_warned_wi, BigDecimal hv, String is_warned_hv, BigDecimal sg,
			String is_warned_sg, BigDecimal day_1, BigDecimal day_2, BigDecimal day_3, BigDecimal day_4,
			BigDecimal day_5, BigDecimal day_6, BigDecimal day_7, BigDecimal total, String is_warned,
			String is_warned_day_1, String is_warned_day_2, String is_warned_day_3, String is_warned_day_4,
			String is_warned_day_5, String is_warned_day_6, String is_warned_day_7, String is_warned_quantity) {
		super();
		this.gas_day = gas_day;
		this.idn_contract = idn_contract;
		this.contract_code = contract_code;
		this.idn_nomination = idn_nomination;
		this.zone_code = zone_code;
		this.supply_demand = supply_demand;
		this.area = area;
		this.point_id = point_id;
		this.wi_hv = wi_hv;
		this.area_concept = area_concept;
		this.cust_type = cust_type;
		this.area_code = area_code;
		this.subarea = subarea;
		this.unit = unit;
		this.entry_exit = entry_exit;
		this.wi = wi;
		this.is_warned_wi = is_warned_wi;
		this.hv = hv;
		this.is_warned_hv = is_warned_hv;
		this.sg = sg;
		this.is_warned_sg = is_warned_sg;
		this.day_1 = day_1;
		this.day_2 = day_2;
		this.day_3 = day_3;
		this.day_4 = day_4;
		this.day_5 = day_5;
		this.day_6 = day_6;
		this.day_7 = day_7;
		this.total = total;
		this.is_warned = is_warned;
		this.is_warned_day_1 = is_warned_day_1;
		this.is_warned_day_2 = is_warned_day_2;
		this.is_warned_day_3 = is_warned_day_3;
		this.is_warned_day_4 = is_warned_day_4;
		this.is_warned_day_5 = is_warned_day_5;
		this.is_warned_day_6 = is_warned_day_6;
		this.is_warned_day_7 = is_warned_day_7;
		this.is_warned_quantity = is_warned_quantity;
	}

	public Date getGas_day() {
		return gas_day;
	}

	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}

	public BigDecimal getIdn_contract() {
		return idn_contract;
	}

	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}

	public String getContract_code() {
		return contract_code;
	}

	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}

	public BigDecimal getIdn_nomination() {
		return idn_nomination;
	}

	public void setIdn_nomination(BigDecimal idn_nomination) {
		this.idn_nomination = idn_nomination;
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

	public String getWi_hv() {
		return wi_hv;
	}

	public void setWi_hv(String wi_hv) {
		this.wi_hv = wi_hv;
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

	public String getIs_warned_wi() {
		return is_warned_wi;
	}

	public void setIs_warned_wi(String is_warned_wi) {
		this.is_warned_wi = is_warned_wi;
	}

	public BigDecimal getHv() {
		return hv;
	}

	public void setHv(BigDecimal hv) {
		this.hv = hv;
	}

	public String getIs_warned_hv() {
		return is_warned_hv;
	}

	public void setIs_warned_hv(String is_warned_hv) {
		this.is_warned_hv = is_warned_hv;
	}

	public BigDecimal getSg() {
		return sg;
	}

	public void setSg(BigDecimal sg) {
		this.sg = sg;
	}

	public String getIs_warned_sg() {
		return is_warned_sg;
	}

	public void setIs_warned_sg(String is_warned_sg) {
		this.is_warned_sg = is_warned_sg;
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

	public String getIs_warned_quantity() {
		return is_warned_quantity;
	}

	public void setIs_warned_quantity(String is_warned_quantity) {
		this.is_warned_quantity = is_warned_quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, area_code, area_concept, contract_code, cust_type, day_1, day_2, day_3, day_4, day_5,
				day_6, day_7, entry_exit, gas_day, hv, idn_contract, idn_nomination, is_warned, is_warned_day_1,
				is_warned_day_2, is_warned_day_3, is_warned_day_4, is_warned_day_5, is_warned_day_6, is_warned_day_7,
				is_warned_hv, is_warned_quantity, is_warned_sg, is_warned_wi, point_id, sg, subarea, supply_demand,
				total, unit, wi, wi_hv, zone_code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShippersNominationReportsWeeklyBean other = (ShippersNominationReportsWeeklyBean) obj;
		return Objects.equals(area, other.area) && Objects.equals(area_code, other.area_code)
				&& Objects.equals(area_concept, other.area_concept)
				&& Objects.equals(contract_code, other.contract_code) && Objects.equals(cust_type, other.cust_type)
				&& Objects.equals(day_1, other.day_1) && Objects.equals(day_2, other.day_2)
				&& Objects.equals(day_3, other.day_3) && Objects.equals(day_4, other.day_4)
				&& Objects.equals(day_5, other.day_5) && Objects.equals(day_6, other.day_6)
				&& Objects.equals(day_7, other.day_7) && Objects.equals(entry_exit, other.entry_exit)
				&& Objects.equals(gas_day, other.gas_day) && Objects.equals(hv, other.hv)
				&& Objects.equals(idn_contract, other.idn_contract)
				&& Objects.equals(idn_nomination, other.idn_nomination) && Objects.equals(is_warned, other.is_warned)
				&& Objects.equals(is_warned_day_1, other.is_warned_day_1)
				&& Objects.equals(is_warned_day_2, other.is_warned_day_2)
				&& Objects.equals(is_warned_day_3, other.is_warned_day_3)
				&& Objects.equals(is_warned_day_4, other.is_warned_day_4)
				&& Objects.equals(is_warned_day_5, other.is_warned_day_5)
				&& Objects.equals(is_warned_day_6, other.is_warned_day_6)
				&& Objects.equals(is_warned_day_7, other.is_warned_day_7)
				&& Objects.equals(is_warned_hv, other.is_warned_hv)
				&& Objects.equals(is_warned_quantity, other.is_warned_quantity)
				&& Objects.equals(is_warned_sg, other.is_warned_sg) && Objects.equals(is_warned_wi, other.is_warned_wi)
				&& Objects.equals(point_id, other.point_id) && Objects.equals(sg, other.sg)
				&& Objects.equals(subarea, other.subarea) && Objects.equals(supply_demand, other.supply_demand)
				&& Objects.equals(total, other.total) && Objects.equals(unit, other.unit)
				&& Objects.equals(wi, other.wi) && Objects.equals(wi_hv, other.wi_hv)
				&& Objects.equals(zone_code, other.zone_code);
	}

	@Override
	public String toString() {
		return "ShippersNominationReportsWeeklyBean [gas_day=" + gas_day + ", idn_contract=" + idn_contract
				+ ", contract_code=" + contract_code + ", idn_nomination=" + idn_nomination + ", zone_code=" + zone_code
				+ ", supply_demand=" + supply_demand + ", area=" + area + ", point_id=" + point_id + ", wi_hv=" + wi_hv
				+ ", area_concept=" + area_concept + ", cust_type=" + cust_type + ", area_code=" + area_code
				+ ", subarea=" + subarea + ", unit=" + unit + ", entry_exit=" + entry_exit + ", wi=" + wi
				+ ", is_warned_wi=" + is_warned_wi + ", hv=" + hv + ", is_warned_hv=" + is_warned_hv + ", sg=" + sg
				+ ", is_warned_sg=" + is_warned_sg + ", day_1=" + day_1 + ", day_2=" + day_2 + ", day_3=" + day_3
				+ ", day_4=" + day_4 + ", day_5=" + day_5 + ", day_6=" + day_6 + ", day_7=" + day_7 + ", total=" + total
				+ ", is_warned=" + is_warned + ", is_warned_day_1=" + is_warned_day_1 + ", is_warned_day_2="
				+ is_warned_day_2 + ", is_warned_day_3=" + is_warned_day_3 + ", is_warned_day_4=" + is_warned_day_4
				+ ", is_warned_day_5=" + is_warned_day_5 + ", is_warned_day_6=" + is_warned_day_6 + ", is_warned_day_7="
				+ is_warned_day_7 + ", is_warned_quantity=" + is_warned_quantity + "]";
	}
	
		

}
