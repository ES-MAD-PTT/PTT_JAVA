package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GasQualityDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String zone;
	private String area;
	private String subarea;
	private String point;
	private String meterId;
	private String gasParameter;
	private Double minvalue;
	private Double maxvalue;
	private BigDecimal sortValue;
	private String newConn;
	private Date dateFrom;
	private Date dateTo;
	/**
	 * devuelve zone.
	 * 
	 * @return zone
	 */
	public String getZone() {
		return zone;
	}

	/**
	 * establece el valor de zone.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setZone(String valor) {
		zone = valor;
	}

	/**
	 * devuelve area.
	 * 
	 * @return area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * establece el valor de area.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setArea(String valor) {
		area = valor;
	}

	/**
	 * devuelve subarea.
	 * 
	 * @return subarea
	 */
	public String getSubarea() {
		return subarea;
	}

	/**
	 * establece el valor de subarea.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setSubarea(String valor) {
		subarea = valor;
	}

	/**
	 * devuelve point.
	 * 
	 * @return point
	 */
	public String getPoint() {
		return point;
	}

	/**
	 * establece el valor de point.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setPoint(String valor) {
		point = valor;
	}

	/**
	 * devuelve meterId.
	 * 
	 * @return meterId
	 */
	public String getMeterId() {
		return meterId;
	}

	/**
	 * establece el valor de meterId.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setMeterId(String valor) {
		meterId = valor;
	}

	/**
	 * devuelve gasParameter.
	 * 
	 * @return gasParameter
	 */
	public String getGasParameter() {
		return gasParameter;
	}

	/**
	 * establece el valor de gasParameter.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setGasParameter(String valor) {
		gasParameter = valor;
	}

	/**
	 * devuelve minvalue.
	 * 
	 * @return minvalue
	 */
	public Double getMinvalue() {
		return minvalue;
	}

	/**
	 * establece el valor de minvalue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setMinvalue(Double valor) {
		minvalue = valor;
	}

	/**
	 * devuelve maxvalue.
	 * 
	 * @return maxvalue
	 */
	public Double getMaxvalue() {
		return maxvalue;
	}

	/**
	 * establece el valor de maxvalue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setMaxvalue(Double valor) {
		maxvalue = valor;
	}

	/**
	 * devuelve sortValue.
	 * 
	 * @return sortValue
	 */
	public BigDecimal getSortValue() {
		return sortValue;
	}

	/**
	 * establece el valor de sortValue.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setSortValue(BigDecimal valor) {
		sortValue = valor;
	}

	/**
	 * devuelve la realacion entre columnas y propiedades.
	 *
	 * @return Map con la relacion
	 */
	public static Map<String, String> getColumToProperty() {
		HashMap<String, String> columToProp = new HashMap<String, String>();
		columToProp.put("Zone", "zone");
		columToProp.put("Area", "area");
		columToProp.put("SubArea", "subarea");
		columToProp.put("Point", "point");
		columToProp.put("Meter_ID", "meterId");
		columToProp.put("Gas_Parameter", "gasParameter");
		columToProp.put("MinValue", "minvalue");
		columToProp.put("MaxValue", "maxvalue");
		columToProp.put("Sort_Value", "sortValue");

		return columToProp;

	}

	@Override
	public String toString() {
		StringBuffer toStr = new StringBuffer();
		toStr.append(zone);
		toStr.append(',');
		toStr.append(area);
		toStr.append(',');
		toStr.append(subarea);
		toStr.append(',');
		toStr.append(point);
		toStr.append(',');
		toStr.append(meterId);
		toStr.append(',');
		toStr.append(gasParameter);
		toStr.append(',');
		toStr.append(minvalue);
		toStr.append(',');
		toStr.append(maxvalue);
		toStr.append(',');
		toStr.append(sortValue);
		toStr.append(',');

		return toStr.toString().replaceAll("null", "");

	}

	public String getNewConn() {
		return newConn;
	}

	public void setNewConn(String newConn) {
		this.newConn = newConn;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

}