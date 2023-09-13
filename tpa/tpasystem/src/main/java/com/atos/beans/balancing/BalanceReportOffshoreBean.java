package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalanceReportOffshoreBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7332139434396129895L;
	
	private SimpleDateFormat sdf;			// Para construir el balanceId.
	private Date gasDay;
	private String shipperCode;
	private BigDecimal contractId;
	private String contractCode;
	private String isTotal;
	private Date versionDate;				// Este valor sera igual a todos los registros.
											// Corresponde al mayor timestamp de todos los repartos incluidos en todos los dias de balance.
	private BigDecimal totalEntry;
	private BigDecimal totalExit;
	private BigDecimal imbalanceZone;
	private BigDecimal instructedFlow;
	private BigDecimal shrinkageQuantity;  // Shrinkage Value - SV
	private BigDecimal strippingQuantity;  // Stripping Value - STV	
	private BigDecimal park;
	private BigDecimal unpark;
	private BigDecimal SOTDPark;
	private BigDecimal EOTDPark;
	private BigDecimal minInventory;
	private BigDecimal resBalancingGas;
	private BigDecimal monthAdjAlloc;	// Monthly Adjustment Allocation
	private BigDecimal totalAIP;	// Valor absoluto de desbalance positivo. (Accumulated Imbalance Positive)
	private BigDecimal totalAIN;	// Valor absoluto de desbalance negativo. (Accumulated Imbalance Negative)
	private BigDecimal imbalancePercentage;
	private BigDecimal imbalanceStock;
	private BigDecimal accImbalanceMonth;
	private BigDecimal accImbalance;
	private BigDecimal usedCapacity;
	// Cantidades en agrupaciones de puntos de entrada y salida.
	private BigDecimal erawan;
	private BigDecimal baanpot;
	private BigDecimal satun;
	private BigDecimal platong_ii;
	private BigDecimal platong;
	private BigDecimal funan;
	private BigDecimal n_pailin;
	private BigDecimal pailin;
	private BigDecimal bongkot;
	private BigDecimal arthit;
	private BigDecimal gbs;
	private BigDecimal tantawan;
	private BigDecimal benchamas;
	private BigDecimal jda18_dp2;
	private BigDecimal jda_b_17;
	private BigDecimal rayongGas;
	private BigDecimal rayongLiquid;
	private BigDecimal khanomGas;
	private BigDecimal khanomLiquid;
	
	public BalanceReportOffshoreBean() {
		sdf = new SimpleDateFormat("yyyyMMdd");
		this.gasDay = null;
		this.shipperCode = null;
		this.contractId = null;
		this.contractCode = null;
		this.isTotal = null;
		this.versionDate = null;
		this.totalEntry = null;
		this.totalEntry = null;
		this.imbalanceZone = null;
		this.instructedFlow = null;
		this.shrinkageQuantity = null;
		this.strippingQuantity = null;
		this.park = null;
		this.unpark = null;
		this.SOTDPark = null;
		this.EOTDPark = null;
		this.minInventory = null;
		this.resBalancingGas = null;
		this.monthAdjAlloc = null;
		this.totalAIP = null;
		this.totalAIN = null;
		this.imbalancePercentage = null;
		this.imbalanceStock = null;
		this.accImbalanceMonth = null;
		this.accImbalance = null;
		this.usedCapacity = null;
		this.erawan = null;
		this.baanpot = null;
		this.satun = null;
		this.platong_ii = null;
		this.platong = null;
		this.funan = null;
		this.n_pailin = null;
		this.pailin = null;
		this.bongkot = null;
		this.arthit = null;
		this.gbs = null;
		this.tantawan = null;
		this.benchamas = null;
		this.jda18_dp2 = null;
		this.jda_b_17 = null;
		this.rayongGas = null;
		this.rayongLiquid = null;
		this.khanomGas = null;
		this.khanomLiquid = null;
	}

	// Se construye un id de cada registro para luego poder seleccionar filas en la pantalla.
	public String getBalanceId() {
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

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public BigDecimal getTotalEntry() {
		return totalEntry;
	}

	public void setTotalEntry(BigDecimal totalEntry) {
		this.totalEntry = totalEntry;
	}

	public BigDecimal getTotalExit() {
		return totalExit;
	}

	public void setTotalExit(BigDecimal totalExit) {
		this.totalExit = totalExit;
	}

	public BigDecimal getImbalanceZone() {
		return imbalanceZone;
	}

	public void setImbalanceZone(BigDecimal imbalanceZone) {
		this.imbalanceZone = imbalanceZone;
	}

	public BigDecimal getInstructedFlow() {
		return instructedFlow;
	}

	public void setInstructedFlow(BigDecimal instructedFlow) {
		this.instructedFlow = instructedFlow;
	}

	public BigDecimal getShrinkageQuantity() {
		return shrinkageQuantity;
	}

	public void setShrinkageQuantity(BigDecimal shrinkageQuantity) {
		this.shrinkageQuantity = shrinkageQuantity;
	}

	public BigDecimal getStrippingQuantity() {
		return strippingQuantity;
	}

	public void setStrippingQuantity(BigDecimal strippingQuantity) {
		this.strippingQuantity = strippingQuantity;
	}

	public BigDecimal getPark() {
		return park;
	}

	public void setPark(BigDecimal park) {
		this.park = park;
	}

	public BigDecimal getUnpark() {
		return unpark;
	}

	public void setUnpark(BigDecimal unpark) {
		this.unpark = unpark;
	}

	public BigDecimal getSOTDPark() {
		return SOTDPark;
	}

	public void setSOTDPark(BigDecimal sOTDPark) {
		SOTDPark = sOTDPark;
	}

	public BigDecimal getEOTDPark() {
		return EOTDPark;
	}

	public void setEOTDPark(BigDecimal eOTDPark) {
		EOTDPark = eOTDPark;
	}

	public BigDecimal getMinInventory() {
		return minInventory;
	}

	public void setMinInventory(BigDecimal minInventory) {
		this.minInventory = minInventory;
	}

	public BigDecimal getResBalancingGas() {
		return resBalancingGas;
	}

	public void setResBalancingGas(BigDecimal resBalancingGas) {
		this.resBalancingGas = resBalancingGas;
	}

	public BigDecimal getMonthAdjAlloc() {
		return monthAdjAlloc;
	}

	public void setMonthAdjAlloc(BigDecimal monthAdjAlloc) {
		this.monthAdjAlloc = monthAdjAlloc;
	}

	public BigDecimal getTotalAIP() {
		return totalAIP;
	}

	public void setTotalAIP(BigDecimal totalAIP) {
		this.totalAIP = totalAIP;
	}

	public BigDecimal getTotalAIN() {
		return totalAIN;
	}

	public void setTotalAIN(BigDecimal totalAIN) {
		this.totalAIN = totalAIN;
	}

	public BigDecimal getImbalancePercentage() {
		return imbalancePercentage;
	}

	public void setImbalancePercentage(BigDecimal imbalancePercentage) {
		this.imbalancePercentage = imbalancePercentage;
	}

	public BigDecimal getImbalanceStock() {
		return imbalanceStock;
	}

	public void setImbalanceStock(BigDecimal imbalanceStock) {
		this.imbalanceStock = imbalanceStock;
	}

	public BigDecimal getAccImbalanceMonth() {
		return accImbalanceMonth;
	}

	public void setAccImbalanceMonth(BigDecimal accImbalanceMonth) {
		this.accImbalanceMonth = accImbalanceMonth;
	}

	public BigDecimal getAccImbalance() {
		return accImbalance;
	}

	public void setAccImbalance(BigDecimal accImbalance) {
		this.accImbalance = accImbalance;
	}

	public BigDecimal getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(BigDecimal usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public BigDecimal getErawan() {
		return erawan;
	}

	public void setErawan(BigDecimal erawan) {
		this.erawan = erawan;
	}

	public BigDecimal getBaanpot() {
		return baanpot;
	}

	public void setBaanpot(BigDecimal baanpot) {
		this.baanpot = baanpot;
	}

	public BigDecimal getSatun() {
		return satun;
	}

	public void setSatun(BigDecimal satun) {
		this.satun = satun;
	}

	public BigDecimal getPlatong_ii() {
		return platong_ii;
	}

	public void setPlatong_ii(BigDecimal platong_ii) {
		this.platong_ii = platong_ii;
	}

	public BigDecimal getPlatong() {
		return platong;
	}

	public void setPlatong(BigDecimal platong) {
		this.platong = platong;
	}

	public BigDecimal getFunan() {
		return funan;
	}

	public void setFunan(BigDecimal funan) {
		this.funan = funan;
	}

	public BigDecimal getN_pailin() {
		return n_pailin;
	}

	public void setN_pailin(BigDecimal n_pailin) {
		this.n_pailin = n_pailin;
	}

	public BigDecimal getPailin() {
		return pailin;
	}

	public void setPailin(BigDecimal pailin) {
		this.pailin = pailin;
	}

	public BigDecimal getBongkot() {
		return bongkot;
	}

	public void setBongkot(BigDecimal bongkot) {
		this.bongkot = bongkot;
	}

	public BigDecimal getArthit() {
		return arthit;
	}

	public void setArthit(BigDecimal arthit) {
		this.arthit = arthit;
	}

	public BigDecimal getGbs() {
		return gbs;
	}

	public void setGbs(BigDecimal gbs) {
		this.gbs = gbs;
	}

	public BigDecimal getTantawan() {
		return tantawan;
	}

	public void setTantawan(BigDecimal tantawan) {
		this.tantawan = tantawan;
	}

	public BigDecimal getBenchamas() {
		return benchamas;
	}

	public void setBenchamas(BigDecimal benchamas) {
		this.benchamas = benchamas;
	}

	public BigDecimal getJda18_dp2() {
		return jda18_dp2;
	}

	public void setJda18_dp2(BigDecimal jda18_dp2) {
		this.jda18_dp2 = jda18_dp2;
	}

	public BigDecimal getJda_b_17() {
		return jda_b_17;
	}

	public void setJda_b_17(BigDecimal jda_b_17) {
		this.jda_b_17 = jda_b_17;
	}

	public BigDecimal getRayongGas() {
		return rayongGas;
	}

	public void setRayongGas(BigDecimal rayongGas) {
		this.rayongGas = rayongGas;
	}

	public BigDecimal getRayongLiquid() {
		return rayongLiquid;
	}

	public void setRayongLiquid(BigDecimal rayongLiquid) {
		this.rayongLiquid = rayongLiquid;
	}

	public BigDecimal getKhanomGas() {
		return khanomGas;
	}

	public void setKhanomGas(BigDecimal khanomGas) {
		this.khanomGas = khanomGas;
	}

	public BigDecimal getKhanomLiquid() {
		return khanomLiquid;
	}

	public void setKhanomLiquid(BigDecimal khanomLiquid) {
		this.khanomLiquid = khanomLiquid;
	}

	@Override
	public String toString() {
		return "BalanceReportOffshoreBean [sdf=" + sdf + ", gasDay=" + gasDay + ", shipperCode=" + shipperCode
				+ ", contractId=" + contractId + ", contractCode=" + contractCode + ", isTotal=" + isTotal
				+ ", versionDate=" + versionDate + ", totalEntry=" + totalEntry + ", totalExit=" + totalExit
				+ ", imbalanceZone=" + imbalanceZone + ", instructedFlow=" + instructedFlow + ", shrinkageQuantity="
				+ shrinkageQuantity + ", strippingQuantity=" + strippingQuantity + ", park=" + park + ", unpark="
				+ unpark + ", SOTDPark=" + SOTDPark + ", EOTDPark=" + EOTDPark + ", minInventory=" + minInventory
				+ ", resBalancingGas=" + resBalancingGas + ", monthAdjAlloc=" + monthAdjAlloc + ", totalAIP=" + totalAIP
				+ ", totalAIN=" + totalAIN + ", imbalancePercentage=" + imbalancePercentage + ", imbalanceStock="
				+ imbalanceStock + ", accImbalanceMonth=" + accImbalanceMonth + ", accImbalance=" + accImbalance
				+ ", usedCapacity=" + usedCapacity + ", erawan=" + erawan + ", baanpot=" + baanpot + ", satun=" + satun
				+ ", platong_ii=" + platong_ii + ", platong=" + platong + ", funan=" + funan + ", n_pailin=" + n_pailin
				+ ", pailin=" + pailin + ", bongkot=" + bongkot + ", arthit=" + arthit + ", gbs=" + gbs + ", tantawan="
				+ tantawan + ", benchamas=" + benchamas + ", jda18_dp2=" + jda18_dp2 + ", jda_b_17=" + jda_b_17
				+ ", rayongGas=" + rayongGas + ", rayongLiquid=" + rayongLiquid + ", khanomGas=" + khanomGas
				+ ", khanomLiquid=" + khanomLiquid + "]";
	}
}