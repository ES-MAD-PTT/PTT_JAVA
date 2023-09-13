package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntradayBaseInventoryBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private Date gasDay;
	private String zoneCode;
	private BigDecimal zoneId;
	private String modeCode;
	private BigDecimal modeId;
	
	private BigDecimal hv_value;
	private BigDecimal base_inv_value;
	private BigDecimal high_threshold_top;
	private BigDecimal high_threshold_mid;
	private BigDecimal high_threshold;
	private BigDecimal low_threshold;
	private BigDecimal low_threshold_mid;
	private BigDecimal low_threshold_top;
	
	
	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}
	
	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}
	
	public BigDecimal getModeId() {
		return modeId;
	}

	public void setModeId(BigDecimal modeId) {
		this.modeId = modeId;
	}

	public BigDecimal getHv_value() {
		return hv_value;
	}

	public void setHv_value(BigDecimal hv_value) {
		this.hv_value = hv_value;
	}

	public BigDecimal getBase_inv_value() {
		return base_inv_value;
	}

	public void setBase_inv_value(BigDecimal base_inv_value) {
		this.base_inv_value = base_inv_value;
	}

	public BigDecimal getHigh_threshold_top() {
		return high_threshold_top;
	}

	public void setHigh_threshold_top(BigDecimal high_threshold_top) {
		this.high_threshold_top = high_threshold_top;
	}

	public BigDecimal getHigh_threshold_mid() {
		return high_threshold_mid;
	}

	public void setHigh_threshold_mid(BigDecimal high_threshold_mid) {
		this.high_threshold_mid = high_threshold_mid;
	}

	public BigDecimal getHigh_threshold() {
		return high_threshold;
	}

	public void setHigh_threshold(BigDecimal high_threshold) {
		this.high_threshold = high_threshold;
	}

	public BigDecimal getLow_threshold() {
		return low_threshold;
	}

	public void setLow_threshold(BigDecimal low_threshold) {
		this.low_threshold = low_threshold;
	}

	public BigDecimal getLow_threshold_mid() {
		return low_threshold_mid;
	}

	public void setLow_threshold_mid(BigDecimal low_threshold_mid) {
		this.low_threshold_mid = low_threshold_mid;
	}

	public BigDecimal getLow_threshold_top() {
		return low_threshold_top;
	}

	public void setLow_threshold_top(BigDecimal low_threshold_top) {
		this.low_threshold_top = low_threshold_top;
	}
}
