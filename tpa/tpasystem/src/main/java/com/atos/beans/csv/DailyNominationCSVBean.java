package com.atos.beans.csv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.atos.utils.Constants;

public class DailyNominationCSVBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7007949864785023448L;
	private BigDecimal idn_nomination;
	private String nomination_code;
	private BigDecimal nomination_version;
	private String user_group_id;
	private String user_group_name;
	private String shipper_comments;
   	private String contract_code;
   	private String is_valid;
   	private String is_responsed;
   	private String operator_comments;
   	private String shipper_file_name;
   	private String is_renomination;
   	private Date aud_ins_date;
   	private Date start_date;
   	private String zone_code;
   	private String supply_demand;
   	private String area;
   	private String point_id;
   	private String area_concept;
   	private String cust_type;
   	private String area_code;
   	private String subarea;
   	private String unit;
   	private String entry_exit;
   	private BigDecimal wi;
   	private BigDecimal hv;
   	private BigDecimal sg;
   	
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
	public String getUser_group_name() {
		return user_group_name;
	}
	public void setUser_group_name(String user_group_name) {
		this.user_group_name = user_group_name;
	}
	public String getShipper_comments() {
		return shipper_comments;
	}
	public void setShipper_comments(String shipper_comments) {
		this.shipper_comments = shipper_comments;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getIs_responsed() {
		return is_responsed;
	}
	public void setIs_responsed(String is_responsed) {
		this.is_responsed = is_responsed;
	}
	public String getOperator_comments() {
		return operator_comments;
	}
	public void setOperator_comments(String operator_comments) {
		this.operator_comments = operator_comments;
	}
	public String getShipper_file_name() {
		return shipper_file_name;
	}
	public void setShipper_file_name(String shipper_file_name) {
		this.shipper_file_name = shipper_file_name;
	}
	public String getIs_renomination() {
		if(this.is_renomination.equals("Y")){
			return "YES";
		} else {
			return "NO";
		}
	}
	public void setIs_renomination(String is_renomination) {
		this.is_renomination = is_renomination;
	}
	public Date getAud_ins_date() {
		return aud_ins_date;
	}
	public void setAud_ins_date(Date aud_ins_date) {
		this.aud_ins_date = aud_ins_date;
	}
	
//////////////////////
    public String getStatus() {

    	String status = null;
    	// Segun restricciones de base de datos:
    	// - is_valid solo puede tener valores "Y" y "N".
    	// - is_responsed solo puede tener valores "Y", "N" y "P". "P" se utiliza en caso de una respuesta en un
    	// 	sistema (ON_SHORE, OFF_SHORE) pendiente de responder en el otro.
    	if("Y".equalsIgnoreCase(is_valid)) {
    		
    		if("N".equalsIgnoreCase(is_responsed))
        		status = Constants.WaitingForResponse;
    		// is_responsed == "P" se toma igual que si fuera "Y".
    		else if("Y".equalsIgnoreCase(is_responsed) || "P".equalsIgnoreCase(is_responsed))
        		status = Constants.Accepted;
    		
    	} else if("N".equalsIgnoreCase(is_valid)) {
    		
    		if("N".equalsIgnoreCase(is_responsed))
        		status = Constants.WaitingForResponse;
    		// is_responsed == "P" se toma igual que si fuera "Y".
    		else if("Y".equalsIgnoreCase(is_responsed) || "P".equalsIgnoreCase(is_responsed))
        		status = Constants.Rejected;    		
    	}
    	
    	return status;    	
	}
	
	
/////////////////////	
	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append("nomination_code;version;user_group_id;shipper_name;shipper_comments;contract_code;status;submission_comments;shipper_file_name;is_renomination;timestamp;start_date;zone_code;supply_demand;area;"
				+ "point_id;area_concept;cust_type;area_code;subarea;unit;entry_exit;wi;hv;sg;"
				+ "hour_01;hour_02;hour_03;hour_04;hour_05;hour_06;hour_07;hour_08;hour_09;hour_10;hour_11;hour_12;hour_13;hour_14;hour_15;hour_16;hour_17;hour_18;hour_19;hour_20;hour_21;hour_22;hour_23;hour_24;"
				+ "co2;c1;c2;c3;ic4;nc4;ic5;nc5;c6;c7;c8;n2;o2;h2s;s;hg");
		return builder.toString();
	}
	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((nomination_code==null ? "" : nomination_code)+";");
		builder.append((nomination_version == null ? "" : nomination_version.intValue())+";");
		builder.append((user_group_id ==null ? "" : user_group_id)+";");
		builder.append((user_group_name ==null ? "" : user_group_name)+";");
		builder.append((shipper_comments ==null ? "" : shipper_comments)+";");
		builder.append((contract_code==null ? "" : contract_code)+";");
		builder.append(getStatus()+";");
		builder.append((operator_comments==null ? "" : operator_comments)+";"); 
		builder.append((shipper_file_name==null ? "" : shipper_file_name)+";"); 
		builder.append(getIs_renomination()+";");
		builder.append((aud_ins_date==null ? "" : sdf.format(aud_ins_date))+";"); 
		builder.append((start_date==null ? "" : sdf.format(start_date))+";");
		builder.append((zone_code==null ? "" : zone_code)+";");
		builder.append((supply_demand==null ? "" :supply_demand)+";");
		builder.append((area==null ? "": area)+";");
		builder.append((point_id==null ? "" : point_id)+";");
		builder.append((area_concept==null ? "" :area_concept)+";");
		builder.append((cust_type==null ? "" : cust_type)+";");
		builder.append((area_code==null ? "" : area_code)+";");
		builder.append((subarea==null ? "" : subarea)+";");
		builder.append((unit==null ? "" : unit)+";");
		builder.append((entry_exit==null ? "" :entry_exit)+";");
		builder.append((wi == null ? "" : wi.doubleValue())+";");
		builder.append((hv == null ? "" : hv.doubleValue())+";");
		builder.append((sg == null ? "" : sg.doubleValue())+";");
		
		builder.append((hour_01 == null ? "" : hour_01.doubleValue())+";");
		builder.append((hour_02 == null ? "" : hour_02.doubleValue())+";");
		builder.append((hour_03 == null ? "" : hour_03.doubleValue())+";");
		builder.append((hour_04 == null ? "" : hour_04.doubleValue())+";");
		builder.append((hour_05 == null ? "" : hour_05.doubleValue())+";");
		builder.append((hour_06 == null ? "" : hour_06.doubleValue())+";");
		builder.append((hour_07 == null ? "" : hour_07.doubleValue())+";");
		builder.append((hour_08 == null ? "" : hour_08.doubleValue())+";");
		builder.append((hour_09 == null ? "" : hour_09.doubleValue())+";");
		builder.append((hour_10 == null ? "" : hour_10.doubleValue())+";");
		builder.append((hour_11 == null ? "" : hour_11.doubleValue())+";");
		builder.append((hour_12 == null ? "" : hour_12.doubleValue())+";");
		builder.append((hour_13 == null ? "" : hour_13.doubleValue())+";");
		builder.append((hour_14 == null ? "" : hour_14.doubleValue())+";");
		builder.append((hour_15 == null ? "" : hour_15.doubleValue())+";");
		builder.append((hour_16 == null ? "" : hour_16.doubleValue())+";");
		builder.append((hour_17 == null ? "" : hour_17.doubleValue())+";");
		builder.append((hour_18 == null ? "" : hour_18.doubleValue())+";");
		builder.append((hour_19 == null ? "" : hour_19.doubleValue())+";");
		builder.append((hour_20 == null ? "" : hour_20.doubleValue())+";");
		builder.append((hour_21 == null ? "" : hour_21.doubleValue())+";");
		builder.append((hour_22 == null ? "" : hour_22.doubleValue())+";");
		builder.append((hour_23 == null ? "" : hour_23.doubleValue())+";");
		builder.append((hour_24 == null ? "" : hour_24.doubleValue())+";");
		
		
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
	
   	
   	
}
