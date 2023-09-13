package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class ContractPointBean extends UserAudBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7578572507426643509L;

	private BigDecimal contractPointId;
	private BigDecimal contractAgreementId;
	private BigDecimal systemPointId;
	private Float quantity;
	private Float maxHourQuantity;
	private Float volume;
	private Float maxHourVolume;
	private BigDecimal releasedToId;
	private BigDecimal receivedFromId;
	private String isNewConnection;		// Valores posibles: "Y" y "N".
	// Stores metering id received in excel file (for informative purposes only)
	private String meterinPoint;

	public ContractPointBean() {
		this.contractPointId = null;
		this.contractAgreementId = null;
		this.systemPointId = null;
		this.quantity = null;
		this.maxHourQuantity = null;
		this.volume = null;
		this.maxHourVolume = null;
		this.releasedToId = null;
		this.receivedFromId = null;
		this.isNewConnection = null;
		this.meterinPoint = null;
	}

	public BigDecimal getContractPointId() {
		return contractPointId;
	}

	public void setContractPointId(BigDecimal contractPointId) {
		this.contractPointId = contractPointId;
	}

	public BigDecimal getContractAgreementId() {
		return contractAgreementId;
	}

	public void setContractAgreementId(BigDecimal contractAgreementId) {
		this.contractAgreementId = contractAgreementId;
	}

	public BigDecimal getSystemPointId() {
		return systemPointId;
	}

	public void setSystemPointId(BigDecimal systemPointId) {
		this.systemPointId = systemPointId;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Float getMaxHourQuantity() {
		return maxHourQuantity;
	}

	public void setMaxHourQuantity(Float maxHourQuantity) {
		this.maxHourQuantity = maxHourQuantity;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public Float getMaxHourVolume() {
		return maxHourVolume;
	}

	public void setMaxHourVolume(Float maxHourVolume) {
		this.maxHourVolume = maxHourVolume;
	}

	public BigDecimal getReleasedToId() {
		return releasedToId;
	}

	public void setReleasedToId(BigDecimal releasedToId) {
		this.releasedToId = releasedToId;
	}

	public BigDecimal getReceivedFromId() {
		return receivedFromId;
	}

	public void setReceivedFromId(BigDecimal receivedFromId) {
		this.receivedFromId = receivedFromId;
	}

	public String getIsNewConnection() {
		return isNewConnection;
	}

	public void setIsNewConnection(String isNewConnection) {
		this.isNewConnection = isNewConnection;
	}

	public String getMeterinPoint() {
		return meterinPoint;
	}

	public void setMeterinPoint(String meterinPoint) {
		this.meterinPoint = meterinPoint;
	}

	@Override
	public String toString() {
		return "ContractPointBean [contractPointId=" + contractPointId + ", contractAgreementId=" + contractAgreementId
				+ ", systemPointId=" + systemPointId + ", quantity=" + quantity + ", maxHourQuantity=" + maxHourQuantity
				+ ", volume=" + volume + ", maxHourVolume=" + maxHourVolume + ", releasedToId=" + releasedToId
				+ ", receivedFromId=" + receivedFromId + ", isNewConnection=" + isNewConnection + ", meterinPoint="
				+ meterinPoint + "]";
	}

	
}
