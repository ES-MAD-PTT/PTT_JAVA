package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipperDailyStatusBean implements Serializable {


	private static final long serialVersionUID = 2246969116931702730L;
	
	private SimpleDateFormat sdf;			// Para construir el balanceId.
	private Date gasDay;
	private String shipperCode;
	private String shortName;
	private BigDecimal contractId;
	private String contractCode;
	private String isTotal;
//	private Date versionDate;				// Este valor sera igual a todos los registros.
											// Corresponde al mayor timestamp de todos los repartos incluidos en todos los dias de balance.
	private BigDecimal eEotdPark;
	private BigDecimal wEotdPark;
	private BigDecimal ewEotdPark;
	
	private BigDecimal eEotdMinInv;
	private BigDecimal wEotdMinInv;
	private BigDecimal ewEotdMinInv;
	
	private BigDecimal eEotdBalGas;
	private BigDecimal wEotdBalGas;
	private BigDecimal ewEotdBalGas;
	
	
	
	private BigDecimal eImbalanceStock;
	private BigDecimal wImbalanceStock;
	private BigDecimal ewImbalanceStock;
	
	private BigDecimal eAccImbalanceStock; 
	private BigDecimal wAccImbalanceStock;
	private BigDecimal ewAccImbalanceStock;
	
	
	public ShipperDailyStatusBean() {
		sdf = new SimpleDateFormat("yyyyMMdd");
		this.gasDay = null;
		this.shipperCode = null;
		this.shortName = null;
		this.contractId = null;
		this.contractCode = null;
		this.isTotal = null;
	//	this.versionDate = null;
		this.eEotdPark = null;
		this.wEotdPark = null;
		this.ewEotdPark = null;
		this.eEotdMinInv = null;
		this.wEotdMinInv = null;
		this.ewEotdMinInv = null;
		
		this.eEotdBalGas = null;
		this.wEotdBalGas = null;
		this.ewEotdBalGas = null;
		
		
		this.eImbalanceStock = null;
		this.wImbalanceStock = null;
		this.ewImbalanceStock = null;
		this.eAccImbalanceStock = null;
		this.wAccImbalanceStock = null;
		this.ewAccImbalanceStock = null;
	
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

/*	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}
*/
	public BigDecimal geteEotdPark() {
		return eEotdPark;
	}

	public void seteEotdPark(BigDecimal eEotdPark) {
		this.eEotdPark = eEotdPark;
	}

	public BigDecimal getwEotdPark() {
		return wEotdPark;
	}

	public void setwEotdPark(BigDecimal wEotdPark) {
		this.wEotdPark = wEotdPark;
	}

	public BigDecimal getEwEotdPark() {
		return ewEotdPark;
	}

	public void setEwEotdPark(BigDecimal ewEotdPark) {
		this.ewEotdPark = ewEotdPark;
	}

	public BigDecimal geteEotdMinInv() {
		return eEotdMinInv;
	}

	public void seteEotdMinInv(BigDecimal eEotdMinInv) {
		this.eEotdMinInv = eEotdMinInv;
	}

	public BigDecimal getwEotdMinInv() {
		return wEotdMinInv;
	}

	public void setwEotdMinInv(BigDecimal wEotdMinInv) {
		this.wEotdMinInv = wEotdMinInv;
	}

	public BigDecimal getEwEotdMinInv() {
		return ewEotdMinInv;
	}

	public void setEwEotdMinInv(BigDecimal ewEotdMinInv) {
		this.ewEotdMinInv = ewEotdMinInv;
	}

	public BigDecimal geteImbalanceStock() {
		return eImbalanceStock;
	}

	public void seteImbalanceStock(BigDecimal eImbalanceStock) {
		this.eImbalanceStock = eImbalanceStock;
	}

	public BigDecimal getwImbalanceStock() {
		return wImbalanceStock;
	}

	public void setwImbalanceStock(BigDecimal wImbalanceStock) {
		this.wImbalanceStock = wImbalanceStock;
	}

	public BigDecimal getEwImbalanceStock() {
		return ewImbalanceStock;
	}

	public void setEwImbalanceStock(BigDecimal ewImbalanceStock) {
		this.ewImbalanceStock = ewImbalanceStock;
	}

	public BigDecimal geteAccImbalanceStock() {
		return eAccImbalanceStock;
	}

	public void seteAccImbalanceStock(BigDecimal eAccImbalanceStock) {
		this.eAccImbalanceStock = eAccImbalanceStock;
	}

	public BigDecimal getwAccImbalanceStock() {
		return wAccImbalanceStock;
	}

	public void setwAccImbalanceStock(BigDecimal wAccImbalanceStock) {
		this.wAccImbalanceStock = wAccImbalanceStock;
	}

	public BigDecimal getEwAccImbalanceStock() {
		return ewAccImbalanceStock;
	}

	public void setEwAccImbalanceStock(BigDecimal ewAccImbalanceStock) {
		this.ewAccImbalanceStock = ewAccImbalanceStock;
	}

	
	public BigDecimal geteEotdBalGas() {
		return eEotdBalGas;
	}

	public void seteEotdBalGas(BigDecimal eEotdBalGas) {
		this.eEotdBalGas = eEotdBalGas;
	}

	public BigDecimal getwEotdBalGas() {
		return wEotdBalGas;
	}

	public void setwEotdBalGas(BigDecimal wEotdBalGas) {
		this.wEotdBalGas = wEotdBalGas;
	}

	public BigDecimal getEwEotdBalGas() {
		return ewEotdBalGas;
	}

	public void setEwEotdBalGas(BigDecimal ewEotdBalGas) {
		this.ewEotdBalGas = ewEotdBalGas;
	}

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"gasDay;shipperCode;shortName;contractCode;eEotdPark;wEotdPark;ewEotdPark;eEotdMinInv;wEotdMinInv;ewEotdMinInv;eEotdBalGas;wEotdBalGas;ewEotdBalGas;eImbalanceStock;wImbalanceStock;ewImbalanceStock;eAccImbalanceStock;wAccImbalanceStock;ewAccImbalanceStock");
		return builder.toString();
	}

	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		builder.append((gasDay==null ? "" : sdf.format(gasDay)) +";");
		builder.append((shipperCode==null ? "" : shipperCode) +";");
		builder.append((shortName==null ? "" : shortName) +";");
		builder.append((contractCode==null ? "" : contractCode) +";");
		builder.append((eEotdPark==null ? "" : eEotdPark.doubleValue()) +";");
		builder.append((wEotdPark==null ? "" : wEotdPark.doubleValue()) +";");
		builder.append((ewEotdPark==null ? "" : ewEotdPark.doubleValue()) +";");
		builder.append((eEotdMinInv==null ? "" : eEotdMinInv.doubleValue()) +";");
		builder.append((wEotdMinInv==null ? "" : wEotdMinInv.doubleValue()) +";");
		builder.append((ewEotdMinInv==null ? "" : ewEotdMinInv.doubleValue()) +";");
		builder.append((eEotdBalGas==null ? "" : eEotdBalGas.doubleValue()) +";");
		builder.append((wEotdBalGas==null ? "" : wEotdBalGas.doubleValue()) +";");
		builder.append((ewEotdBalGas==null ? "" : ewEotdBalGas.doubleValue()) +";");
		builder.append((eImbalanceStock==null ? "" : eImbalanceStock.doubleValue()) +";");
		builder.append((wImbalanceStock==null ? "" : wImbalanceStock.doubleValue()) +";");
		builder.append((ewImbalanceStock==null ? "" : ewImbalanceStock.doubleValue()) +";");
		builder.append((eAccImbalanceStock==null ? "" : eAccImbalanceStock.toPlainString()) +";");
		builder.append((wAccImbalanceStock==null ? "" : wAccImbalanceStock.toPlainString() ) +";");
		builder.append((ewAccImbalanceStock==null ? "" : ewAccImbalanceStock.doubleValue()));
		return builder.toString();
	}

	
	@Override
	public String toString() {
		return "ShipperDailyStatusBean [sdf=" + sdf + ", gasDay=" + gasDay + ", shipperCode=" + shipperCode
				+ ", shortName=" + shortName + ", contractId=" + contractId + ", contractCode=" + contractCode
				+ ", isTotal=" + isTotal + ", eEotdPark=" + eEotdPark + ", wEotdPark=" + wEotdPark + ", ewEotdPark="
				+ ewEotdPark + ", eEotdMinInv=" + eEotdMinInv + ", wEotdMinInv=" + wEotdMinInv + ", ewEotdMinInv="
				+ ewEotdMinInv + ", eEotdBalGas=" + eEotdBalGas + ", wEotdBalGas=" + wEotdBalGas + ", ewEotdBalGas="
				+ ewEotdBalGas + ", eImbalanceStock=" + eImbalanceStock + ", wImbalanceStock=" + wImbalanceStock
				+ ", ewImbalanceStock=" + ewImbalanceStock + ", eAccImbalanceStock=" + eAccImbalanceStock
				+ ", wAccImbalanceStock=" + wAccImbalanceStock + ", ewAccImbalanceStock=" + ewAccImbalanceStock + "]";
	}




	

}