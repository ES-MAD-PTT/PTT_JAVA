package com.atos.beans.allocation;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class AllocationReportDetailDto extends AllocationReportDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nominationPoint;

	public String getNominationPoint() {
		return nominationPoint;
	}

	public void setNominationPoint(String nominationPoint) {
		this.nominationPoint = nominationPoint;
	}
	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
			builder.append("gas_day;");
			builder.append("shipper_code;");
			builder.append("shipper_name;");
			builder.append("contract_code;");
			builder.append("point_code;");
			builder.append("nomination_point;");
			builder.append("point_type_code;");
			builder.append("contracted_value;");
			builder.append("nominated_value;");
			builder.append("allocated_value;");
			builder.append("balancing_gas");
		return builder.toString();
	}
	
	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((gasDay==null ? "" : sdf.format(gasDay))+";");
		builder.append((shipperCode==null ? "" : shipperCode)+";");
		builder.append((shipperName==null ? "" : shipperName)+";");
		builder.append((contractCode==null ? "" : contractCode)+";");
		builder.append((pointCode==null ? "" : pointCode)+";");
		builder.append((nominationPoint== null ? "" : nominationPoint)+";");
		builder.append((pointTypeCode== null ? "" : pointTypeCode)+";");
		builder.append((contractedValue==null ? "" : contractedValue.doubleValue())+";");
		builder.append((nominatedValue==null ? "" : nominatedValue.doubleValue())+";");
		builder.append((allocatedValue==null ? "" : allocatedValue.doubleValue())+";");
		builder.append((balancingGas==null ? "" : balancingGas.doubleValue())+";");
		return builder.toString();
	}
	
}
