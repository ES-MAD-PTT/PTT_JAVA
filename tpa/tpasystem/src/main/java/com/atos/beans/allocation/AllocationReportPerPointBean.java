package com.atos.beans.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AllocationReportPerPointBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private Date gasDay;
	private String shipperCode;
	private String shipperName;	
	private String shortName;
	private String pointCode;
	private String pointTypeCode;
	private BigDecimal allocationTPA;
	private BigDecimal allocationReview;
	private BigDecimal difference;
	private BigDecimal idnShipperGroup;
	private String isTotal;
	
	
	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public String getPointTypeCode() {
		return pointTypeCode;
	}

	public void setPointTypeCode(String pointTypeCode) {
		this.pointTypeCode = pointTypeCode;
	}

	public BigDecimal getAllocationTPA() {
		return allocationTPA;
	}

	public void setAllocationTPA(BigDecimal allocationTPA) {
		this.allocationTPA = allocationTPA;
	}

	public BigDecimal getAllocationReview() {
		return allocationReview;
	}

	public void setAllocationReview(BigDecimal allocationReview) {
		this.allocationReview = allocationReview;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}
	
	public BigDecimal getIdnShipperGroup() {
		return idnShipperGroup;
	}

	public void setIdnShipperGroup(BigDecimal idnShipperGroup) {
		this.idnShipperGroup = idnShipperGroup;
	}

	public String getIsTotal() {
		return isTotal;
	}

	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * devuelve la realacion entre columnas y propiedades.
	 *
	 * @return Map con la relacion
	 */
	/*public static Map<String, String> getColumToProperty() {
		HashMap<String, String> columToProp = new HashMap<String, String>();				
		columToProp.put("GAS_DAY", "gasDay");
		columToProp.put("SHIPPER_CODE", "shipperCode");
		columToProp.put("SHIPPER_NAME", "shipperName");		
		columToProp.put("POINT_CODE", "pointCode");
		columToProp.put("POINT_TYPE_CODE", "pointTypeCode");
		columToProp.put("CONTRACT_CODE", "allocationTPA");
		columToProp.put("CONTRACT_CODE", "allocationReview");
		columToProp.put("CONTRACT_CODE", "difference");
		columToProp.put("IS_TOTAL", "isTotal");
		
		return columToProp;

}*/

	@Override
	public String toString() {
		StringBuffer toStr = new StringBuffer();
				
		toStr.append(gasDay);
		toStr.append(',');
		toStr.append(shipperCode);
		toStr.append(',');
		toStr.append(shipperName);
		toStr.append(',');
		toStr.append(shortName);
		toStr.append(',');
		toStr.append(pointCode);
		toStr.append(',');
		toStr.append(pointTypeCode);
		toStr.append(',');
		toStr.append(allocationTPA);
		toStr.append(',');
		toStr.append(allocationReview);
		toStr.append(',');
		toStr.append(difference);
		toStr.append(',');
		toStr.append(idnShipperGroup);
		toStr.append(',');
		toStr.append(isTotal);
		toStr.append(',');
		
		return toStr.toString().replaceAll("null", "");

	}
}