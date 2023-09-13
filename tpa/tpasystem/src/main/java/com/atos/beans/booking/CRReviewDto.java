package com.atos.beans.booking;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class CRReviewDto implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8401565058431590546L;

	public static final int DETAIL_REGISTER = 0;
	public static final int SUBTOTAL_REGISTER = 1;
	public static final int TOTAL_REGISTER = 2;
	public static final int DIFF_REGISTER = 3;

	private String zone;
	private String area;
	private String subArea;
	private String entryMeterId;
	private String entryPoint;
	private String newConnection;
	private BigDecimal pressureRangeMin;
	private BigDecimal pressureRangeMax;
	private BigDecimal temperatureRangeMin;
	private BigDecimal temperatureRangeMax;
	private BigDecimal gcvRangeMin;
	private BigDecimal gcvRangeMax;
	private BigDecimal wiRangeMin;
	private BigDecimal wiRangeMax;
	private BigDecimal c2Min;
	private BigDecimal c2Max;
	private BigDecimal co2Min;
	private BigDecimal co2Max;
	private BigDecimal o2Min;
	private BigDecimal o2Max;
	private BigDecimal n2Max;
	private BigDecimal n2Min;
	private BigDecimal h2sMin;
	private BigDecimal h2sMax;
	private BigDecimal totalSMax;
	private BigDecimal totalSMin;
	private BigDecimal hgMin;
	private BigDecimal hgMax;
	private BigDecimal h2oMax;
	private BigDecimal h2oMin;
	private BigDecimal hcDewMin;
	private BigDecimal hcDewMax;
	private BigDecimal prMin;
	private BigDecimal prMax;
	private Date dateTo;
	private Date dateFrom;
	private String blockValve;
	private int registerType;
	private Map<String, Double> capacityDailyBooking;
	private Map<String, Double> maximunHourBooking;
	private Map<String, Double> capacityDailyBookingMM;
	private Map<String, Double> maximunHourBookingMM;
	private Map<String, Double> subTotalcapacityDailyBookingByZone;
	private Map<String, Double> subtotalMaximunHourBooking;
	private Map<String, Double> subtotalCapacityDailyBookingScf;
	private Map<String, Double> subtotalMaxHourBookingScf;
	private Map<String, Double> diffEntryExit;
	private Map<String, Double> diffMaximunHourBooking;

	public Double getDiffEntryExit(String period) {
		return diffEntryExit.get(period);
	}

	public Double getSubtotalValueForMaxHourBookingScf(String period) {
		return subtotalMaxHourBookingScf.get(period + "-" + zone);
	}

	public Double getSubtotalValueForDailyBookingScf(String period) {
		return subtotalCapacityDailyBookingScf.get(period + "-" + zone);
	}

	public Double getSubtotalValueForMaxHourBooking(String period) {
		return subtotalMaximunHourBooking.get(period + "-" + zone);
	}

	public Double getSubtotalValueForDailyBooking(String period) {
		return subTotalcapacityDailyBookingByZone.get(period + "-" + zone);
	}

	public Double getTotalValueForMaxHourBookingScf(String period) {
		return subtotalMaxHourBookingScf.get(period);
	}

	public Double getTotalValueForDailyBookingScf(String period) {
		return subtotalCapacityDailyBookingScf.get(period);
	}

	public Double getTotalValueForMaxHourBooking(String period) {
		return subtotalMaximunHourBooking.get(period);
	}

	public Double getTotalValueForDailyBooking(String period) {
		return subTotalcapacityDailyBookingByZone.get(period);
	}

	public Double getValueForDiffMaximunHourBooking(String period) {
		if (diffMaximunHourBooking != null) {
			return diffMaximunHourBooking.get(period);
		} else {
			return null;
		}
	}

	public Double getValueForDiffCapacityDailyBooking(String period) {
		if (diffEntryExit != null) {
			return diffEntryExit.get(period);
		} else {
			return null;
		}
	}
	public Double getValueForDailyBooking(String period) {
		if (capacityDailyBooking != null) {
			return capacityDailyBooking.get(period);
		} else {
			return null;
		}
	}

	public Double getValueForDailyBookingMM(String period) {
		if (capacityDailyBookingMM != null) {
			String key = getEntryPoint() + "-" + period;
			return capacityDailyBookingMM.get(key);
		} else {
			return null;
		}
	}

	public Double getValueForMaximunHourBooking(String period) {
		if (maximunHourBooking != null) {
			String key = getEntryPoint() + "-" + period;
		return maximunHourBooking.get(key);
		} else {
			return null;
		}
	}

	public Double getValueForMaximunHourBookingMM(String period) {
		String key = getEntryPoint() + "-" + period;

		if (maximunHourBookingMM != null) {
			return maximunHourBookingMM.get(key);
		} else {
			return null;
		}
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public BigDecimal getPrMin() {
		return prMin;
	}

	public void setPrMin(BigDecimal prMin) {
		this.prMin = prMin;
	}

	public BigDecimal getPrMax() {
		return prMax;
	}

	public void setPrMax(BigDecimal prMax) {
		this.prMax = prMax;
	}



	public Map<String, Double> getCapacityDailyBooking() {
		return capacityDailyBooking;
	}

	public void setCapacityDailyBooking(Map<String, Double> capacityDailyBooking) {
		this.capacityDailyBooking = capacityDailyBooking;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getRegisterType() {
		return registerType;
	}

	public void setRegisterType(int registerType) {
		this.registerType = registerType;
	}

	public Map<String, Double> getMaximunHourBooking() {
		return maximunHourBooking;
	}

	public void setMaximunHourBooking(Map<String, Double> maximunHourBooking) {
		this.maximunHourBooking = maximunHourBooking;
	}

	public Map<String, Double> getCapacityDailyBookingMM() {
		return capacityDailyBookingMM;
	}

	public void setCapacityDailyBookingMM(Map<String, Double> capacityDailyBookingMM) {
		this.capacityDailyBookingMM = capacityDailyBookingMM;
	}

	public Map<String, Double> getMaximunHourBookingMM() {
		return maximunHourBookingMM;
	}

	public void setMaximunHourBookingMM(Map<String, Double> maximunHourBookingMM) {
		this.maximunHourBookingMM = maximunHourBookingMM;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSubArea() {
		return subArea;
	}

	public void setSubArea(String subArea) {
		this.subArea = subArea;
	}

	public String getEntryMeterId() {
		return entryMeterId;
	}

	public void setEntryMeterId(String entryMeterId) {
		this.entryMeterId = entryMeterId;
	}

	public String getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}

	public String getNewConnection() {
		return newConnection;
	}

	public void setNewConnection(String newConnection) {
		this.newConnection = newConnection;
	}

	public BigDecimal getPressureRangeMin() {
		return pressureRangeMin;
	}

	public void setPressureRangeMin(BigDecimal pressureRangeMin) {
		this.pressureRangeMin = pressureRangeMin;
	}

	public BigDecimal getPressureRangeMax() {
		return pressureRangeMax;
	}

	public void setPressureRangeMax(BigDecimal pressureRangeMax) {
		this.pressureRangeMax = pressureRangeMax;
	}

	public BigDecimal getTemperatureRangeMin() {
		return temperatureRangeMin;
	}

	public void setTemperatureRangeMin(BigDecimal temperatureRangeMin) {
		this.temperatureRangeMin = temperatureRangeMin;
	}

	public BigDecimal getTemperatureRangeMax() {
		return temperatureRangeMax;
	}

	public void setTemperatureRangeMax(BigDecimal temperatureRangeMax) {
		this.temperatureRangeMax = temperatureRangeMax;
	}

	public BigDecimal getGcvRangeMin() {
		return gcvRangeMin;
	}

	public void setGcvRangeMin(BigDecimal gcvRangeMin) {
		this.gcvRangeMin = gcvRangeMin;
	}

	public BigDecimal getGcvRangeMax() {
		return gcvRangeMax;
	}

	public void setGcvRangeMax(BigDecimal gcvRangeMax) {
		this.gcvRangeMax = gcvRangeMax;
	}

	public BigDecimal getWiRangeMin() {
		return wiRangeMin;
	}

	public void setWiRangeMin(BigDecimal wiRangeMin) {
		this.wiRangeMin = wiRangeMin;
	}

	public BigDecimal getWiRangeMax() {
		return wiRangeMax;
	}

	public void setWiRangeMax(BigDecimal wiRangeMax) {
		this.wiRangeMax = wiRangeMax;
	}

	public BigDecimal getC2Min() {
		return c2Min;
	}

	public void setC2Min(BigDecimal c2Min) {
		this.c2Min = c2Min;
	}

	public BigDecimal getC2Max() {
		return c2Max;
	}

	public void setC2Max(BigDecimal c2Max) {
		this.c2Max = c2Max;
	}

	public BigDecimal getCo2Min() {
		return co2Min;
	}

	public void setCo2Min(BigDecimal co2Min) {
		this.co2Min = co2Min;
	}

	public BigDecimal getCo2Max() {
		return co2Max;
	}

	public void setCo2Max(BigDecimal co2Max) {
		this.co2Max = co2Max;
	}

	public BigDecimal getO2Min() {
		return o2Min;
	}

	public void setO2Min(BigDecimal o2Min) {
		this.o2Min = o2Min;
	}

	public BigDecimal getO2Max() {
		return o2Max;
	}

	public void setO2Max(BigDecimal o2Max) {
		this.o2Max = o2Max;
	}

	public BigDecimal getN2Max() {
		return n2Max;
	}

	public void setN2Max(BigDecimal n2Max) {
		this.n2Max = n2Max;
	}

	public BigDecimal getN2Min() {
		return n2Min;
	}

	public void setN2Min(BigDecimal n2Min) {
		this.n2Min = n2Min;
	}

	public BigDecimal getH2sMin() {
		return h2sMin;
	}

	public void setH2sMin(BigDecimal h2sMin) {
		this.h2sMin = h2sMin;
	}

	public BigDecimal getH2sMax() {
		return h2sMax;
	}

	public void setH2sMax(BigDecimal h2sMax) {
		this.h2sMax = h2sMax;
	}

	public BigDecimal getTotalSMax() {
		return totalSMax;
	}

	public void setTotalSMax(BigDecimal totalSMax) {
		this.totalSMax = totalSMax;
	}

	public BigDecimal getTotalSMin() {
		return totalSMin;
	}

	public void setTotalSMin(BigDecimal totalSMin) {
		this.totalSMin = totalSMin;
	}

	public BigDecimal getHgMin() {
		return hgMin;
	}

	public void setHgMin(BigDecimal hgMin) {
		this.hgMin = hgMin;
	}

	public BigDecimal getHgMax() {
		return hgMax;
	}

	public void setHgMax(BigDecimal hgMax) {
		this.hgMax = hgMax;
	}

	public BigDecimal getH2oMax() {
		return h2oMax;
	}

	public void setH2oMax(BigDecimal h20Max) {
		this.h2oMax = h20Max;
	}

	public BigDecimal getH2oMin() {
		return h2oMin;
	}

	public void setH2oMin(BigDecimal h20Min) {
		this.h2oMin = h20Min;
	}

	public BigDecimal getHcDewMin() {
		return hcDewMin;
	}

	public void setHcDewMin(BigDecimal hcDewMin) {
		this.hcDewMin = hcDewMin;
	}

	public BigDecimal getHcDewMax() {
		return hcDewMax;
	}

	public void setHcDewMax(BigDecimal hcDewMax) {
		this.hcDewMax = hcDewMax;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getBlockValve() {
		return blockValve;
	}

	public void setBlockValve(String blockValve) {
		this.blockValve = blockValve;
	}
	public Map<String, Double> getSubTotalcapacityDailyBookingByZone() {
		return subTotalcapacityDailyBookingByZone;
	}
	public void setSubTotalcapacityDailyBookingByZone(Map<String, Double> subTotalcapacityDailyBookingByZone) {
		this.subTotalcapacityDailyBookingByZone = subTotalcapacityDailyBookingByZone;
	}

	public Map<String, Double> getSubtotalMaximunHourBooking() {
		return subtotalMaximunHourBooking;
	}

	public void setSubtotalMaximunHourBooking(Map<String, Double> subtotalMaximunHourBooking) {
		this.subtotalMaximunHourBooking = subtotalMaximunHourBooking;
	}

	public Map<String, Double> getSubtotalCapacityDailyBookingScf() {
		return subtotalCapacityDailyBookingScf;
	}

	public void setSubtotalCapacityDailyBookingScf(Map<String, Double> subtotalCapacityDailyBookingScf) {
		this.subtotalCapacityDailyBookingScf = subtotalCapacityDailyBookingScf;
	}

	public Map<String, Double> getSubtotalMaxHourBookingScf() {
		return subtotalMaxHourBookingScf;
	}

	public void setSubtotalMaxHourBookingScf(Map<String, Double> subtotalMaxHourBookingScf) {
		this.subtotalMaxHourBookingScf = subtotalMaxHourBookingScf;
	}

	public Map<String, Double> getDiffEntryExit() {
		return diffEntryExit;
	}

	public void setDiffEntryExit(Map<String, Double> diffEntryExit) {
		this.diffEntryExit = diffEntryExit;
	}

	public Map<String, Double> getDiffMaximunHourBooking() {
		return diffMaximunHourBooking;
	}

	public void setDiffMaximunHourBooking(Map<String, Double> diffMaximunHourBooking) {
		this.diffMaximunHourBooking = diffMaximunHourBooking;
	}

}
