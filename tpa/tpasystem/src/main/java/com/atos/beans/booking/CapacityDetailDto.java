package com.atos.beans.booking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CapacityDetailDto {
	private String zone;
	private String area;
	private String entryPoint;
	private String contractType;
	private Date monthYear;
	private String comments;
	private Integer operationFileId;
	private Double dailyBookingmbtu;
	private Double hourlyBookingMmbtu;
	private Double dailyBookingMmscfd;
	private Double hourlyBookingMmscfd;
	private SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", new Locale("en"));
	private SimpleDateFormat sdfYear = new SimpleDateFormat("MMM/yyyy");
	private boolean dailyBookingmbtEdited, hourlyBookingMmbtuEdited, dailyBookingMmscfdEdited,
			hourlyBookingMmscfdEdited;
	private boolean exitPoint;

	public String getYear() {
		if (contractType.startsWith("SHORT")) {
			return sdf.format(monthYear);
		} else {
			return sdfYear.format(monthYear);
		}
	}
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
	 * devuelve entryPoint.
	 * 
	 * @return entryPoint
	 */
	public String getEntryPoint() {
		return entryPoint;
	}

	/**
	 * establece el valor de entryPoint.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setEntryPoint(String valor) {
		entryPoint = valor;
	}

	/**
	 * devuelve contractType.
	 * 
	 * @return contractType
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * establece el valor de contractType.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setContractType(String valor) {
		contractType = valor;
	}

	/**
	 * devuelve monthYear.
	 * 
	 * @return monthYear
	 */
	public Date getMonthYear() {
		return monthYear;
	}

	/**
	 * establece el valor de monthYear.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setMonthYear(Date valor) {
		monthYear = valor;
	}

	/**
	 * devuelve dailyBookingmbtu.
	 * 
	 * @return dailyBookingmbtu
	 */
	public Double getDailyBookingmbtu() {
		return dailyBookingmbtu;
	}

	/**
	 * establece el valor de dailyBookingmbtu.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setDailyBookingmbtu(Double valor) {
		dailyBookingmbtu = valor;
	}

	/**
	 * devuelve hourlyBookingMmbtu.
	 * 
	 * @return hourlyBookingMmbtu
	 */
	public Double getHourlyBookingMmbtu() {
		return hourlyBookingMmbtu;
	}

	/**
	 * establece el valor de hourlyBookingMmbtu.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setHourlyBookingMmbtu(Double valor) {
		hourlyBookingMmbtu = valor;
	}

	/**
	 * devuelve dailyBookingMmscfd.
	 * 
	 * @return dailyBookingMmscfd
	 */
	public Double getDailyBookingMmscfd() {
		return dailyBookingMmscfd;
	}

	/**
	 * establece el valor de dailyBookingMmscfd.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setDailyBookingMmscfd(Double valor) {
		dailyBookingMmscfd = valor;
	}

	/**
	 * devuelve hourlyBookingMmscfd.
	 * 
	 * @return hourlyBookingMmscfd
	 */
	public Double getHourlyBookingMmscfd() {
		return hourlyBookingMmscfd;
	}

	/**
	 * establece el valor de hourlyBookingMmscfd.
	 * 
	 * @param valor
	 *            nuevo valor
	 */
	public void setHourlyBookingMmscfd(Double valor) {
		hourlyBookingMmscfd = valor;
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
		columToProp.put("Entry_Point", "entryPoint");
		columToProp.put("Contract_Type", "contractType");
		columToProp.put("Month_Year", "monthYear");
		columToProp.put("Daily_Booking_mbtu", "dailyBookingmbtu");
		columToProp.put("Hourly_Booking_MMBTU", "hourlyBookingMmbtu");
		columToProp.put("Daily_Booking_MMscfd", "dailyBookingMmscfd");
		columToProp.put("Hourly_Booking_MMscfd", "hourlyBookingMmscfd");

		return columToProp;

	}

	@Override
	public String toString() {
		StringBuffer toStr = new StringBuffer();
		toStr.append(zone);
		toStr.append(',');
		toStr.append(area);
		toStr.append(',');
		toStr.append(entryPoint);
		toStr.append(',');
		toStr.append(contractType);
		toStr.append(',');
		toStr.append(monthYear);
		toStr.append(',');
		toStr.append(dailyBookingmbtu);
		toStr.append(',');
		toStr.append(hourlyBookingMmbtu);
		toStr.append(',');
		toStr.append(dailyBookingMmscfd);
		toStr.append(',');
		toStr.append(hourlyBookingMmscfd);
		toStr.append(',');

		return toStr.toString().replaceAll("null", "");

	}

	public boolean isDailyBookingmbtEdited() {
		return dailyBookingmbtEdited;
	}

	public void setDailyBookingmbtEdited(boolean dailyBookingmbtEdited) {
		this.dailyBookingmbtEdited = dailyBookingmbtEdited;
	}

	public boolean isHourlyBookingMmbtuEdited() {
		return hourlyBookingMmbtuEdited;
	}

	public void setHourlyBookingMmbtuEdited(boolean hourlyBookingMmbtuEdited) {
		this.hourlyBookingMmbtuEdited = hourlyBookingMmbtuEdited;
	}

	public boolean isDailyBookingMmscfdEdited() {
		return dailyBookingMmscfdEdited;
	}

	public void setDailyBookingMmscfdEdited(boolean dailyBookingMmscfdEdited) {
		this.dailyBookingMmscfdEdited = dailyBookingMmscfdEdited;
	}

	public boolean isHourlyBookingMmscfdEdited() {
		return hourlyBookingMmscfdEdited;
	}

	public void setHourlyBookingMmscfdEdited(boolean hourlyBookingMmscfdEdited) {
		this.hourlyBookingMmscfdEdited = hourlyBookingMmscfdEdited;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getOperationFileId() {
		return operationFileId;
	}

	public void setOperationFileId(Integer operationFileId) {
		this.operationFileId = operationFileId;
	}

	public boolean isExitPoint() {
		return exitPoint;
	}

	public void setExitPoint(boolean exitPoint) {
		this.exitPoint = exitPoint;
	}

}