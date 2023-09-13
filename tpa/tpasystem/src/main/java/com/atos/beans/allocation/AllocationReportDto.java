package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AllocationReportDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal idnAllocation;
	private BigDecimal idnContract;
	private BigDecimal idnContractPoint;
	protected Date gasDay;
	protected String shipperCode;
	protected String shipperName;
	protected String contractCode;
	protected String pointCode;
	protected String pointTypeCode;
	protected BigDecimal contractedValue;
	protected BigDecimal nominatedValue;
	protected BigDecimal allocatedValue;
	protected BigDecimal balancingGas;
	private BigDecimal overusage;

	/**
	 * devuelve idnAllocation.
	 * 
	 * @return idnAllocation
	 */
	public BigDecimal getIdnAllocation() {
		return idnAllocation;
	}

	/**
	 * establece el valor de idnAllocation.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setIdnAllocation(BigDecimal valor) {
		idnAllocation = valor;
	}

	/**
	 * devuelve idnContract.
	 * 
	 * @return idnContract
	 */
	public BigDecimal getIdnContract() {
		return idnContract;
	}

	/**
	 * establece el valor de idnContract.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setIdnContract(BigDecimal valor) {
		idnContract = valor;
	}

	/**
	 * devuelve idnContractPoint.
	 * 
	 * @return idnContractPoint
	 */
	public BigDecimal getIdnContractPoint() {
		return idnContractPoint;
	}

	/**
	 * establece el valor de idnContractPoint.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setIdnContractPoint(BigDecimal valor) {
		idnContractPoint = valor;
	}

	/**
	 * devuelve gasDay.
	 * 
	 * @return gasDay
	 */
	public Date getGasDay() {
		return gasDay;
	}

	/**
	 * establece el valor de gasDay.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setGasDay(Date valor) {
		gasDay = valor;
	}

	/**
	 * devuelve shipperCode.
	 * 
	 * @return shipperCode
	 */
	public String getShipperCode() {
		return shipperCode;
	}

	/**
	 * establece el valor de shipperCode.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setShipperCode(String valor) {
		shipperCode = valor;
	}	

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	/**
	 * devuelve contractCode.
	 * 
	 * @return contractCode
	 */
	public String getContractCode() {
		return contractCode;
	}

	/**
	 * establece el valor de contractCode.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setContractCode(String valor) {
		contractCode = valor;
	}

	/**
	 * devuelve pointCode.
	 * 
	 * @return pointCode
	 */
	public String getPointCode() {
		return pointCode;
	}

	/**
	 * establece el valor de pointCode.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setPointCode(String valor) {
		pointCode = valor;
	}

	/**
	 * devuelve pointTypeCode.
	 * 
	 * @return pointTypeCode
	 */
	public String getPointTypeCode() {
		return pointTypeCode;
	}

	/**
	 * establece el valor de pointTypeCode.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setPointTypeCode(String valor) {
		pointTypeCode = valor;
	}

	/**
	 * devuelve contractedValue.
	 * 
	 * @return contractedValue
	 */
	public BigDecimal getContractedValue() {
		return contractedValue;
	}

	/**
	 * establece el valor de contractedValue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setContractedValue(BigDecimal valor) {
		contractedValue = valor;
	}

	/**
	 * devuelve nominatedValue.
	 * 
	 * @return nominatedValue
	 */
	public BigDecimal getNominatedValue() {
		return nominatedValue;
	}

	/**
	 * establece el valor de nominatedValue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setNominatedValue(BigDecimal valor) {
		nominatedValue = valor;
	}

	/**
	 * devuelve allocatedValue.
	 * 
	 * @return allocatedValue
	 */
	public BigDecimal getAllocatedValue() {
		return allocatedValue;
	}

	/**
	 * establece el valor de allocatedValue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setAllocatedValue(BigDecimal valor) {
		allocatedValue = valor;
	}

	/**
	 * devuelve balancingGas.
	 * 
	 * @return balancingGas
	 */
	public BigDecimal getBalancingGas() {
		return balancingGas;
	}

	/**
	 * establece el valor de balancingGas.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setBalancingGas(BigDecimal valor) {
		balancingGas = valor;
	}

	/**
	 * devuelve overusage.
	 * 
	 * @return overusage
	 */
	public BigDecimal getOverusage() {
		return overusage;
	}

	/**
	 * establece el valor de overusage.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setOverusage(BigDecimal valor) {
		overusage = valor;
	}

	/**
	 * devuelve la realacion entre columnas y propiedades.
	 *
	 * @return Map con la relacion
	 */
	public static Map<String, String> getColumToProperty() {
		HashMap<String, String> columToProp = new HashMap<String, String>();
		columToProp.put("IDN_ALLOCATION", "idnAllocation");
		columToProp.put("IDN_CONTRACT", "idnContract");
		columToProp.put("IDN_CONTRACT_POINT", "idnContractPoint");
		columToProp.put("GAS_DAY", "gasDay");
		columToProp.put("SHIPPER_CODE", "shipperCode");
		columToProp.put("SHIPPER_NAME", "shipperName");
		columToProp.put("CONTRACT_CODE", "contractCode");
		columToProp.put("POINT_CODE", "pointCode");
		columToProp.put("POINT_TYPE_CODE", "pointTypeCode");
		columToProp.put("CONTRACTED_VALUE", "contractedValue");
		columToProp.put("NOMINATED_VALUE", "nominatedValue");
		columToProp.put("ALLOCATED_VALUE", "allocatedValue");
		columToProp.put("BALANCING_GAS", "balancingGas");
		columToProp.put("OVERUSAGE", "overusage");

		return columToProp;

}

	@Override
	public String toString() {
		StringBuffer toStr = new StringBuffer();
		toStr.append(idnAllocation);
		toStr.append(',');
		toStr.append(idnContract);
		toStr.append(',');
		toStr.append(idnContractPoint);
		toStr.append(',');
		toStr.append(gasDay);
		toStr.append(',');
		toStr.append(shipperCode);
		toStr.append(',');
		toStr.append(shipperName);
		toStr.append(',');
		toStr.append(contractCode);
		toStr.append(',');
		toStr.append(pointCode);
		toStr.append(',');
		toStr.append(pointTypeCode);
		toStr.append(',');
		toStr.append(contractedValue);
		toStr.append(',');
		toStr.append(nominatedValue);
		toStr.append(',');
		toStr.append(allocatedValue);
		toStr.append(',');
		toStr.append(balancingGas);
		toStr.append(',');
		toStr.append(overusage);
		toStr.append(',');

		return toStr.toString().replaceAll("null", "");

	}
	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
			builder.append("gas_day;");
			builder.append("shipper_code;");
			builder.append("shipper_name;");
			builder.append("contract_code;");
			builder.append("point_code;");
			builder.append("point_type_code;");
			builder.append("contracted_value;");
			builder.append("nominated_value;");
			builder.append("allocated_value;");
			builder.append("balancing_gas");
			builder.append("overusage");
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
		builder.append((pointTypeCode== null ? "" : pointTypeCode)+";");
		builder.append((contractedValue==null ? "" : contractedValue.doubleValue())+";");
		builder.append((nominatedValue==null ? "" : nominatedValue.doubleValue())+";");
		builder.append((allocatedValue==null ? "" : allocatedValue.doubleValue())+";");
		builder.append((balancingGas==null ? "" : balancingGas.doubleValue())+";");
		builder.append((overusage==null ? "" : overusage.doubleValue()));
		return builder.toString();
	}
	
	
}