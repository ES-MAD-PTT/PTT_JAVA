package com.atos.beans.allocation;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdjustBeanQryResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4257893999847915883L;
	private Date gasDay;
	private String originShipper;
	private String shortName;
	private String originContract;
	private String originZone;
	private Double originValue;
	private String destinationShipper;
	private String destinationContract;
	private String destinationZone;
	private Double destinationValue;
	private String status;
	private String reviewCode;
	private String comments;

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
	 * devuelve originShipper.
	 * 
	 * @return originShipper
	 */
	public String getOriginShipper() {
		return originShipper;
	}

	/**
	 * establece el valor de originShipper.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setOriginShipper(String valor) {
		originShipper = valor;
	}

	/**
	 * devuelve originContract.
	 * 
	 * @return originContract
	 */
	public String getOriginContract() {
		return originContract;
	}

	/**
	 * establece el valor de originContract.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setOriginContract(String valor) {
		originContract = valor;
	}

	/**
	 * devuelve originZone.
	 * 
	 * @return originZone
	 */
	public String getOriginZone() {
		return originZone;
	}

	/**
	 * establece el valor de originZone.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setOriginZone(String valor) {
		originZone = valor;
	}

	/**
	 * devuelve originValue.
	 * 
	 * @return originValue
	 */
	public Double getOriginValue() {
		return originValue;
	}

	/**
	 * establece el valor de originValue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setOriginValue(Double valor) {
		originValue = valor;
	}

	/**
	 * devuelve destinationShipper.
	 * 
	 * @return destinationShipper
	 */
	public String getDestinationShipper() {
		return destinationShipper;
	}

	/**
	 * establece el valor de destinationShipper.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setDestinationShipper(String valor) {
		destinationShipper = valor;
	}

	/**
	 * devuelve destinationContract.
	 * 
	 * @return destinationContract
	 */
	public String getDestinationContract() {
		return destinationContract;
	}

	/**
	 * establece el valor de destinationContract.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setDestinationContract(String valor) {
		destinationContract = valor;
	}

	/**
	 * devuelve destinationZone.
	 * 
	 * @return destinationZone
	 */
	public String getDestinationZone() {
		return destinationZone;
	}

	/**
	 * establece el valor de destinationZone.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setDestinationZone(String valor) {
		destinationZone = valor;
	}

	/**
	 * devuelve destinationValue.
	 * 
	 * @return destinationValue
	 */
	public Double getDestinationValue() {
		return destinationValue;
	}

	/**
	 * establece el valor de destinationValue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setDestinationValue(Double valor) {
		destinationValue = valor;
	}

	/**
	 * devuelve status.
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * establece el valor de status.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setStatus(String valor) {
		status = valor;
	}

	/**
	 * devuelve reviewCode.
	 * 
	 * @return reviewCode
	 */
	public String getReviewCode() {
		return reviewCode;
	}

	/**
	 * establece el valor de reviewCode.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setReviewCode(String valor) {
		reviewCode = valor;
	}

	/**
	 * devuelve comments.
	 * 
	 * @return comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * establece el valor de comments.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setComments(String valor) {
		comments = valor;
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
	public static Map<String, String> getColumToProperty() {
		HashMap<String, String> columToProp = new HashMap<String, String>();
		columToProp.put("GAS_DAY", "gasDay");
		columToProp.put("ORIGIN_SHIPPER", "originShipper");
		columToProp.put("ORIGIN_CONTRACT", "originContract");
		columToProp.put("ORIGIN_ZONE", "originZone");
		columToProp.put("ORIGIN_VALUE", "originValue");
		columToProp.put("DESTINATION_SHIPPER", "destinationShipper");
		columToProp.put("DESTINATION_CONTRACT", "destinationContract");
		columToProp.put("DESTINATION_ZONE", "destinationZone");
		columToProp.put("DESTINATION_VALUE", "destinationValue");
		columToProp.put("STATUS", "status");
		columToProp.put("REVIEW_CODE", "reviewCode");
		columToProp.put("COMMENTS", "comments");

		return columToProp;

	}

	@Override
	public String toString() {
		StringBuffer toStr = new StringBuffer();
		toStr.append(gasDay);
		toStr.append(',');
		toStr.append(originShipper);
		toStr.append(',');
		toStr.append(shortName);
		toStr.append(',');
		toStr.append(originContract);
		toStr.append(',');
		toStr.append(originZone);
		toStr.append(',');
		toStr.append(originValue);
		toStr.append(',');
		toStr.append(destinationShipper);
		toStr.append(',');
		toStr.append(destinationContract);
		toStr.append(',');
		toStr.append(destinationZone);
		toStr.append(',');
		toStr.append(destinationValue);
		toStr.append(',');
		toStr.append(status);
		toStr.append(',');
		toStr.append(reviewCode);
		toStr.append(',');
		toStr.append(comments);
		toStr.append(',');

		return toStr.toString().replaceAll("null", "");

	}

}
