package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class InstructedFlowExPostBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5255211382691787452L;
	
	
	private Date gas_day;
	private String shipper_code;
	private String shortName;
	private String contrac_code;
	private String concept;
	private BigDecimal allocated_value;

	private BigDecimal idn_contract;
	private BigDecimal idn_user_group;
	private BigDecimal idn_nomination_concept;
		
	private String zone_code;
	private BigDecimal idn_zone;
	
	private BigDecimal idn_system;//offshore
	
	public InstructedFlowExPostBean(Date gas_day, String shipper_code, String shortName, String contrac_code, String concept,
			BigDecimal allocated_value, BigDecimal idn_contract, BigDecimal idn_user_group,
			BigDecimal idn_nomination_concept, String zone_code, BigDecimal idn_zone) {
		super();
		this.gas_day = gas_day;
		this.shipper_code = shipper_code;
		this.shortName = shortName;
		this.contrac_code = contrac_code;
		this.concept = concept;
		this.allocated_value = allocated_value;
		this.idn_contract = idn_contract;
		this.idn_user_group = idn_user_group;
		this.idn_nomination_concept = idn_nomination_concept;
		this.zone_code = zone_code;
		this.idn_zone = idn_zone;
		this.idn_system=null;
	}


	public InstructedFlowExPostBean(){
		super();
	}

	
	
	

	@Override
	public String toString() {
		return "InstructedFlowExPostBean [gas_day=" + gas_day + ", shipper_code=" + shipper_code + ", shortName="
				+ shortName + ", contrac_code=" + contrac_code + ", concept=" + concept + ", allocated_value="
				+ allocated_value + ", idn_contract=" + idn_contract + ", idn_user_group=" + idn_user_group
				+ ", idn_nomination_concept=" + idn_nomination_concept + ", zone_code=" + zone_code + ", idn_zone="
				+ idn_zone + ", idn_system=" + idn_system + "]";
	}


	public Date getGas_day() {
		return gas_day;
	}


	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}


	public String getShipper_code() {
		return shipper_code;
	}


	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}


	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getContrac_code() {
		return contrac_code;
	}


	public void setContrac_code(String contrac_code) {
		this.contrac_code = contrac_code;
	}


	public String getConcept() {
		return concept;
	}


	public void setConcept(String concept) {
		this.concept = concept;
	}


	public BigDecimal getAllocated_value() {
		return allocated_value;
	}


	public void setAllocated_value(BigDecimal allocated_value) {
		this.allocated_value = allocated_value;
	}


	public BigDecimal getIdn_contract() {
		return idn_contract;
	}


	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}


	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}


	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
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


	public BigDecimal getIdn_zone() {
		return idn_zone;
	}


	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}


	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}


	
}
