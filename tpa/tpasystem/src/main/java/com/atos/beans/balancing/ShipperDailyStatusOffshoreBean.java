package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipperDailyStatusOffshoreBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5836289765191879382L;
	
	private SimpleDateFormat sdf;			// Para construir el dailyStatusId.
	private Date gasDay;
	private String shipperCode;
	private BigDecimal contractId;
	private String contractCode;
	private String isTotal;
	private BigDecimal eotdPark;
	private BigDecimal eotdMinInv;
	private BigDecimal eotdBalGas;
	private BigDecimal imbalanceStock;
	private BigDecimal accImbalanceStock; 
	
	public ShipperDailyStatusOffshoreBean() {
		sdf = new SimpleDateFormat("yyyyMMdd");
		this.gasDay = null;
		this.shipperCode = null;
		this.contractId = null;
		this.contractCode = null;
		this.isTotal = null;
		this.eotdPark = null;
		this.eotdMinInv = null;
		this.eotdBalGas = null;
		this.imbalanceStock = null;
		this.accImbalanceStock = null;
	}

	// Se construye un id de cada registro para luego poder seleccionar filas en la pantalla.
	public String getDailyStatusId() {
		String res = "";
		
		if(gasDay!=null)
			res = sdf.format(gasDay);
		if(shipperCode!=null)
			res += shipperCode;
		if(contractId!=null)
			res += contractId;
		
		return res;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

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

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getIsTotal() {
		return isTotal;
	}

	public void setIsTotal(String isTotal) {
		this.isTotal = isTotal;
	}

	public BigDecimal getEotdPark() {
		return eotdPark;
	}

	public void setEotdPark(BigDecimal eotdPark) {
		this.eotdPark = eotdPark;
	}

	public BigDecimal getEotdMinInv() {
		return eotdMinInv;
	}

	public void setEotdMinInv(BigDecimal eotdMinInv) {
		this.eotdMinInv = eotdMinInv;
	}

	public BigDecimal getEotdBalGas() {
		return eotdBalGas;
	}

	public void setEotdBalGas(BigDecimal eotdBalGas) {
		this.eotdBalGas = eotdBalGas;
	}

	public BigDecimal getImbalanceStock() {
		return imbalanceStock;
	}

	public void setImbalanceStock(BigDecimal imbalanceStock) {
		this.imbalanceStock = imbalanceStock;
	}

	public BigDecimal getAccImbalanceStock() {
		return accImbalanceStock;
	}

	public void setAccImbalanceStock(BigDecimal accImbalanceStock) {
		this.accImbalanceStock = accImbalanceStock;
	}

	@Override
	public String toString() {
		return "ShipperDailyStatusOffshoreBean [sdf=" + sdf + ", gasDay=" + gasDay + ", shipperCode=" + shipperCode
				+ ", contractId=" + contractId + ", contractCode=" + contractCode + ", isTotal=" + isTotal
				+ ", eotdPark=" + eotdPark + ", eotdMinInv=" + eotdMinInv + ", eotdBalGas=" + eotdBalGas
				+ ", imbalanceStock=" + imbalanceStock + ", accImbalanceStock=" + accImbalanceStock + "]";
	}
}