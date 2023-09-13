package com.atos.beans.csv;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TariffOveruseCSVBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4877682137761566598L;
	
	private String month;
	private String shipper_code;
	private Date gas_day;
	private String variable;
	private String contract_code;
	private String type;
	private BigDecimal energy_excl_tolerance;
	private BigDecimal energy;
	private BigDecimal charge;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getShipper_code() {
		return shipper_code;
	}
	public void setShipper_code(String shipper_code) {
		this.shipper_code = shipper_code;
	}
	public Date getGas_day() {
		return gas_day;
	}
	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getEnergy_excl_tolerance() {
		return energy_excl_tolerance;
	}
	public void setEnergy_excl_tolerance(BigDecimal energy_excl_tolerance) {
		this.energy_excl_tolerance = energy_excl_tolerance;
	}
	public BigDecimal getEnergy() {
		return energy;
	}
	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}
	public BigDecimal getCharge() {
		return charge;
	}
	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append("month;shipper_code;gas_day;variable;contract_code;type;energy_excl_tolerance;energy;charge");
		return builder.toString();
	}
	
	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((month==null ? "" : month)+";");
		builder.append((shipper_code==null ? "" : shipper_code)+";");
		builder.append((gas_day==null ? "" : sdf.format(gas_day))+";");
		builder.append((variable==null ? "" : variable)+";");
		builder.append((contract_code==null ? "" : contract_code)+";");
		builder.append((type==null ? "" : type)+";");
		builder.append((energy_excl_tolerance==null ? "" : energy_excl_tolerance.toPlainString())+";");
		builder.append((energy==null ? "" : energy.toPlainString())+";");
		builder.append((charge==null ? "" : charge.toPlainString()));
		return builder.toString();
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TariffOveruseCSVBean [month=");
		builder.append(month);
		builder.append(", shipper_code=");
		builder.append(shipper_code);
		builder.append(", gas_day=");
		builder.append(gas_day);
		builder.append(", variable=");
		builder.append(variable);
		builder.append(", contract_code=");
		builder.append(contract_code);
		builder.append(", type=");
		builder.append(type);
		builder.append(", energy_excl_tolerance=");
		builder.append(energy_excl_tolerance);
		builder.append(", energy=");
		builder.append(energy);
		builder.append(", charge=");
		builder.append(charge);
		builder.append("]");
		return builder.toString();
	}
	
	
}
