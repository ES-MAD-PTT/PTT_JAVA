package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

public class SignedContractTempPointBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 321494738953939105L;

	private String zoneCode;
	private String customerCode;		// Solo se usara en puntos de salida.
	private String pointCode;
	private BigDecimal minPressure;
	private BigDecimal maxPressure;
	private BigDecimal volume;			// Solo se usara en puntos de entrada.
	private BigDecimal quantity;
	private BigDecimal maxHourQuantity;
	private boolean bShadowColor;		// Este atributo solo se usa a la hora de pintar tablas en el documento.
	
	public SignedContractTempPointBean() {
		this.zoneCode = null;
		this.customerCode = null;
		this.pointCode = null;
		this.minPressure = null;
		this.maxPressure = null;
		this.volume = null;
		this.quantity = null;
		this.maxHourQuantity = null;
		this.bShadowColor = false;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public BigDecimal getMinPressure() {
		return minPressure;
	}

	public void setMinPressure(BigDecimal minPressure) {
		this.minPressure = minPressure;
	}

	public BigDecimal getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(BigDecimal maxPressure) {
		this.maxPressure = maxPressure;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getMaxHourQuantity() {
		return maxHourQuantity;
	}

	public void setMaxHourQuantity(BigDecimal maxHourQuantity) {
		this.maxHourQuantity = maxHourQuantity;
	}

	public boolean isbShadowColor() {
		return bShadowColor;
	}

	public void setbShadowColor(boolean bShadowColor) {
		this.bShadowColor = bShadowColor;
	}

	@Override
	public String toString() {
		return "SignedContractTempPointBean [zoneCode=" + zoneCode + ", customerCode=" + customerCode + ", pointCode="
				+ pointCode + ", minPressure=" + minPressure + ", maxPressure=" + maxPressure + ", volume=" + volume
				+ ", quantity=" + quantity + ", maxHourQuantity=" + maxHourQuantity + ", bShadowColor=" + bShadowColor
				+ "]";
	}
}
