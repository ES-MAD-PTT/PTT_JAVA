package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BalanceReportBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2246969116931702730L;
	
	private SimpleDateFormat sdf;			// Para construir el balanceId.
	private Date gasDay;
	private String shipperCode;
	private String shipperName;
	private String shortName;	

	private BigDecimal contractId;
	private String contractCode;
	private String isTotal;
	private String isTotalAllShippers;
	private Date versionDate;				// Este valor sera igual a todos los registros.
											// Corresponde al mayor timestamp de todos los repartos incluidos en todos los dias de balance.
	private BigDecimal eTotalEntry;
	private BigDecimal wTotalEntry;
	private BigDecimal ewTotalEntry;
	private BigDecimal eTotalExit;
	private BigDecimal wTotalExit;
	private BigDecimal ewTotalExit;
	private BigDecimal eImbalanceZone;
	private BigDecimal wImbalanceZone;
	private BigDecimal ewImbalanceZone;
	private BigDecimal totalImbalanceZone;	
	private BigDecimal eInstructedFlow;
	private BigDecimal wInstructedFlow;
	private BigDecimal ewInstructedFlow;
	private BigDecimal eShrinkageQuantity;  // Shrinkage Value - SV
	private BigDecimal wShrinkageQuantity;
	private BigDecimal ewShrinkageQuantity;
	private BigDecimal ePark;
	private BigDecimal wPark;
	private BigDecimal ewPark;
	private BigDecimal eUnpark;
	private BigDecimal wUnpark;
	private BigDecimal ewUnpark;
	private BigDecimal eSOTDPark;
	private BigDecimal wSOTDPark;
	private BigDecimal ewSOTDPark;
	private BigDecimal eEOTDPark;
	private BigDecimal wEOTDPark;
	private BigDecimal ewEOTDPark;
	private BigDecimal eMinInventory;
	private BigDecimal wMinInventory;
	private BigDecimal ewMinInventory;
	private BigDecimal eResBalancingGas;
	private BigDecimal wResBalancingGas;
	private BigDecimal ewResBalancingGas;
	private BigDecimal totalAIP;	// Valor absoluto de desbalance positivo. (Accumulated Imbalance Positive)
	private BigDecimal totalAIN;	// Valor absoluto de desbalance negativo. (Accumulated Imbalance Negative)
	private BigDecimal imbalancePercentage;
	private BigDecimal eImbalanceStock;
	private BigDecimal wImbalanceStock;
	private BigDecimal ewImbalanceStock;
	
	private BigDecimal eMonthAdjust;
	private BigDecimal wMonthAdjust;
	private BigDecimal ewMonthAdjust;
	
	private BigDecimal eAccImbalanceMonth;
	private BigDecimal wAccImbalanceMonth;
	private BigDecimal ewAccImbalanceMonth;
	private BigDecimal eAccImbalance;
	private BigDecimal wAccImbalance;
	private BigDecimal ewAccImbalance;
	private BigDecimal eUsedCapacity;
	private BigDecimal wUsedCapacity;
	private BigDecimal ewUsedCapacity;
	// Cantidades en agrupaciones de puntos de entrada y salida.
	private BigDecimal GSP;
	private BigDecimal BYPASS;
	private BigDecimal LNG;
	private BigDecimal YDN;
	private BigDecimal YTG;
	private BigDecimal ZTK;
	private BigDecimal eFrom;
	private BigDecimal wFrom;
	//Item 35
	private BigDecimal eBvw10;
	private BigDecimal wBvw10;
	//Fin Item 35
	private BigDecimal eEGAT;
	private BigDecimal eIPP;
	private BigDecimal eSPP;
	private BigDecimal eIND;
	private BigDecimal eNGD;
	private BigDecimal eNGV;
	private BigDecimal wEGAT;
	private BigDecimal wIPP;
	private BigDecimal wSPP;
	private BigDecimal wIND;
	private BigDecimal wNGD;
	private BigDecimal wNGV;
	private BigDecimal ewEGAT;
	private BigDecimal ewIPP;
	private BigDecimal ewSPP;
	private BigDecimal ewIND;
	private BigDecimal ewNGD;
	private BigDecimal ewNGV;
	//CH 601
	private BigDecimal e_others;
	private BigDecimal w_others;
	private BigDecimal ew_others;
	//Item 35
	private BigDecimal eF2g;
	private BigDecimal wF2g;
	private BigDecimal e_east;
	private BigDecimal e_west;
	//Fin Item 35
	
	public BalanceReportBean() {
		sdf = new SimpleDateFormat("yyyyMMdd");
		this.gasDay = null;
		this.shipperCode = null;
		this.shipperName = null;
		this.shortName = null;
		this.contractId = null;
		this.contractCode = null;
		this.isTotal = null;
		this.isTotalAllShippers = null;
		this.versionDate = null;
		this.eTotalEntry = null;
		this.wTotalEntry = null;
		this.ewTotalEntry = null;
		this.eTotalExit = null;
		this.wTotalExit = null;
		this.ewTotalExit = null;
		this.eImbalanceZone = null;
		this.wImbalanceZone = null;
		this.ewImbalanceZone = null;
		this.totalImbalanceZone = null;
		this.eInstructedFlow = null;
		this.wInstructedFlow = null;
		this.ewInstructedFlow = null;
		this.eShrinkageQuantity = null;
		this.wShrinkageQuantity = null;
		this.ewShrinkageQuantity = null;
		this.ePark = null;
		this.wPark = null;
		this.ewPark = null;
		this.eUnpark = null;
		this.wUnpark = null;
		this.ewUnpark = null;
		this.eSOTDPark = null;
		this.wSOTDPark = null;
		this.ewSOTDPark = null;
		this.eEOTDPark = null;
		this.wEOTDPark = null;
		this.ewEOTDPark = null;
		this.eMinInventory = null;
		this.wMinInventory = null;
		this.ewMinInventory = null;
		this.eResBalancingGas = null;
		this.wResBalancingGas = null;
		this.ewResBalancingGas = null;
		this.totalAIP = null;
		this.totalAIN = null;
		this.imbalancePercentage = null;
		this.eImbalanceStock = null;
		this.wImbalanceStock = null;
		this.ewImbalanceStock = null;
		this.eMonthAdjust = null;
		this.wMonthAdjust = null;
		this.ewMonthAdjust = null;
		this.eAccImbalanceMonth = null;
		this.wAccImbalanceMonth = null;
		this.ewAccImbalanceMonth = null;
		this.eAccImbalance = null;
		this.wAccImbalance = null;
		this.ewAccImbalance = null;
		this.eUsedCapacity = null;
		this.wUsedCapacity = null;
		this.ewUsedCapacity = null;
		this.GSP = null;
		this.BYPASS = null;
		this.LNG = null;
		this.YDN = null;
		this.YTG = null;
		this.ZTK = null;
		this.eFrom = null;
		this.wFrom = null;
		this.eBvw10 = null;
		this.wBvw10 = null;
		this.eEGAT = null;
		this.eIPP = null;
		this.eSPP = null;
		this.eIND = null;
		this.eNGD = null;
		this.eNGV = null;
		this.wEGAT = null;
		this.wIPP = null;
		this.wSPP = null;
		this.wIND = null;
		this.wNGD = null;
		this.wNGV = null;
		this.ewEGAT = null;
		this.ewIPP = null;
		this.ewSPP = null;
		this.ewIND = null;
		this.ewNGD = null;
		this.ewNGV = null;
		this.e_others =null;
		this.w_others=null;
		this.ew_others=null;
		this.eF2g = null;
		this.wF2g = null;
		this.e_east = null;
		this.e_west = null;
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
	
	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
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
	
	public String getIsTotalAllShippers() {
		return isTotalAllShippers;
	}

	public void setIsTotalAllShippers(String isTotalAllShippers) {
		this.isTotalAllShippers = isTotalAllShippers;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public BigDecimal geteTotalEntry() {
		return eTotalEntry;
	}

	public void seteTotalEntry(BigDecimal eTotalEntry) {
		this.eTotalEntry = eTotalEntry;
	}

	public BigDecimal getwTotalEntry() {
		return wTotalEntry;
	}

	public void setwTotalEntry(BigDecimal wTotalEntry) {
		this.wTotalEntry = wTotalEntry;
	}

	public BigDecimal getEwTotalEntry() {
		return ewTotalEntry;
	}

	public void setEwTotalEntry(BigDecimal ewTotalEntry) {
		this.ewTotalEntry = ewTotalEntry;
	}

	public BigDecimal geteTotalExit() {
		return eTotalExit;
	}

	public void seteTotalExit(BigDecimal eTotalExit) {
		this.eTotalExit = eTotalExit;
	}

	public BigDecimal getwTotalExit() {
		return wTotalExit;
	}

	public void setwTotalExit(BigDecimal wTotalExit) {
		this.wTotalExit = wTotalExit;
	}

	public BigDecimal getEwTotalExit() {
		return ewTotalExit;
	}

	public void setEwTotalExit(BigDecimal ewTotalExit) {
		this.ewTotalExit = ewTotalExit;
	}

	public BigDecimal geteImbalanceZone() {
		return eImbalanceZone;
	}

	public void seteImbalanceZone(BigDecimal eImbalanceZone) {
		this.eImbalanceZone = eImbalanceZone;
	}

	public BigDecimal getwImbalanceZone() {
		return wImbalanceZone;
	}

	public void setwImbalanceZone(BigDecimal wImbalanceZone) {
		this.wImbalanceZone = wImbalanceZone;
	}

	public BigDecimal getEwImbalanceZone() {
		return ewImbalanceZone;
	}

	public void setEwImbalanceZone(BigDecimal ewImbalanceZone) {
		this.ewImbalanceZone = ewImbalanceZone;
	}

	public BigDecimal getTotalImbalanceZone() {
		return totalImbalanceZone;
	}

	public void setTotalImbalanceZone(BigDecimal totalImbalanceZone) {
		this.totalImbalanceZone = totalImbalanceZone;
	}

	public BigDecimal geteInstructedFlow() {
		return eInstructedFlow;
	}

	public void seteInstructedFlow(BigDecimal eInstructedFlow) {
		this.eInstructedFlow = eInstructedFlow;
	}

	public BigDecimal getwInstructedFlow() {
		return wInstructedFlow;
	}

	public void setwInstructedFlow(BigDecimal wInstructedFlow) {
		this.wInstructedFlow = wInstructedFlow;
	}

	public BigDecimal getEwInstructedFlow() {
		return ewInstructedFlow;
	}

	public void setEwInstructedFlow(BigDecimal ewInstructedFlow) {
		this.ewInstructedFlow = ewInstructedFlow;
	}

	public BigDecimal geteShrinkageQuantity() {
		return eShrinkageQuantity;
	}

	public void seteShrinkageQuantity(BigDecimal eShrinkageQuantity) {
		this.eShrinkageQuantity = eShrinkageQuantity;
	}

	public BigDecimal getwShrinkageQuantity() {
		return wShrinkageQuantity;
	}

	public void setwShrinkageQuantity(BigDecimal wShrinkageQuantity) {
		this.wShrinkageQuantity = wShrinkageQuantity;
	}

	public BigDecimal getEwShrinkageQuantity() {
		return ewShrinkageQuantity;
	}

	public void setEwShrinkageQuantity(BigDecimal ewShrinkageQuantity) {
		this.ewShrinkageQuantity = ewShrinkageQuantity;
	}

	public BigDecimal getePark() {
		return ePark;
	}

	public void setePark(BigDecimal ePark) {
		this.ePark = ePark;
	}

	public BigDecimal getwPark() {
		return wPark;
	}

	public void setwPark(BigDecimal wPark) {
		this.wPark = wPark;
	}

	public BigDecimal getEwPark() {
		return ewPark;
	}

	public void setEwPark(BigDecimal ewPark) {
		this.ewPark = ewPark;
	}

	public BigDecimal geteUnpark() {
		return eUnpark;
	}

	public void seteUnpark(BigDecimal eUnpark) {
		this.eUnpark = eUnpark;
	}

	public BigDecimal getwUnpark() {
		return wUnpark;
	}

	public void setwUnpark(BigDecimal wUnpark) {
		this.wUnpark = wUnpark;
	}

	public BigDecimal getEwUnpark() {
		return ewUnpark;
	}

	public void setEwUnpark(BigDecimal ewUnpark) {
		this.ewUnpark = ewUnpark;
	}

	public BigDecimal geteSOTDPark() {
		return eSOTDPark;
	}

	public void seteSOTDPark(BigDecimal eSOTDPark) {
		this.eSOTDPark = eSOTDPark;
	}

	public BigDecimal getwSOTDPark() {
		return wSOTDPark;
	}

	public void setwSOTDPark(BigDecimal wSOTDPark) {
		this.wSOTDPark = wSOTDPark;
	}

	public BigDecimal getEwSOTDPark() {
		return ewSOTDPark;
	}

	public void setEwSOTDPark(BigDecimal ewSOTDPark) {
		this.ewSOTDPark = ewSOTDPark;
	}

	public BigDecimal geteEOTDPark() {
		return eEOTDPark;
	}

	public void seteEOTDPark(BigDecimal eEOTDPark) {
		this.eEOTDPark = eEOTDPark;
	}

	public BigDecimal getwEOTDPark() {
		return wEOTDPark;
	}

	public void setwEOTDPark(BigDecimal wEOTDPark) {
		this.wEOTDPark = wEOTDPark;
	}

	public BigDecimal getEwEOTDPark() {
		return ewEOTDPark;
	}

	public void setEwEOTDPark(BigDecimal ewEOTDPark) {
		this.ewEOTDPark = ewEOTDPark;
	}

	public BigDecimal geteMinInventory() {
		return eMinInventory;
	}

	public void seteMinInventory(BigDecimal eMinInventory) {
		this.eMinInventory = eMinInventory;
	}

	public BigDecimal getwMinInventory() {
		return wMinInventory;
	}

	public void setwMinInventory(BigDecimal wMinInventory) {
		this.wMinInventory = wMinInventory;
	}

	public BigDecimal getEwMinInventory() {
		return ewMinInventory;
	}

	public void setEwMinInventory(BigDecimal ewMinInventory) {
		this.ewMinInventory = ewMinInventory;
	}

	public BigDecimal geteResBalancingGas() {
		return eResBalancingGas;
	}

	public void seteResBalancingGas(BigDecimal eResBalancingGas) {
		this.eResBalancingGas = eResBalancingGas;
	}

	public BigDecimal getwResBalancingGas() {
		return wResBalancingGas;
	}

	public void setwResBalancingGas(BigDecimal wResBalancingGas) {
		this.wResBalancingGas = wResBalancingGas;
	}

	public BigDecimal getEwResBalancingGas() {
		return ewResBalancingGas;
	}

	public void setEwResBalancingGas(BigDecimal ewResBalancingGas) {
		this.ewResBalancingGas = ewResBalancingGas;
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
	
	public BigDecimal geteMonthAdjust() {
		return eMonthAdjust;
	}

	public void seteMonthAdjust(BigDecimal eMonthAdjust) {
		this.eMonthAdjust = eMonthAdjust;
	}

	public BigDecimal getwMonthAdjust() {
		return wMonthAdjust;
	}

	public void setwMonthAdjust(BigDecimal wMonthAdjust) {
		this.wMonthAdjust = wMonthAdjust;
	}

	public BigDecimal getEwMonthAdjust() {
		return ewMonthAdjust;
	}

	public void setEwMonthAdjust(BigDecimal ewMonthAdjust) {
		this.ewMonthAdjust = ewMonthAdjust;
	}

	public BigDecimal geteAccImbalanceMonth() {
		return eAccImbalanceMonth;
	}

	public void seteAccImbalanceMonth(BigDecimal eAccImbalanceMonth) {
		this.eAccImbalanceMonth = eAccImbalanceMonth;
	}

	public BigDecimal getwAccImbalanceMonth() {
		return wAccImbalanceMonth;
	}

	public void setwAccImbalanceMonth(BigDecimal wAccImbalanceMonth) {
		this.wAccImbalanceMonth = wAccImbalanceMonth;
	}

	public BigDecimal getEwAccImbalanceMonth() {
		return ewAccImbalanceMonth;
	}

	public void setEwAccImbalanceMonth(BigDecimal ewAccImbalanceMonth) {
		this.ewAccImbalanceMonth = ewAccImbalanceMonth;
	}

	public BigDecimal geteAccImbalance() {
		return eAccImbalance;
	}

	public void seteAccImbalance(BigDecimal eAccImbalance) {
		this.eAccImbalance = eAccImbalance;
	}

	public BigDecimal getwAccImbalance() {
		return wAccImbalance;
	}

	public void setwAccImbalance(BigDecimal wAccImbalance) {
		this.wAccImbalance = wAccImbalance;
	}

	public BigDecimal getEwAccImbalance() {
		return ewAccImbalance;
	}

	public void setEwAccImbalance(BigDecimal ewAccImbalance) {
		this.ewAccImbalance = ewAccImbalance;
	}

	public BigDecimal geteUsedCapacity() {
		return eUsedCapacity;
	}

	public void seteUsedCapacity(BigDecimal eUsedCapacity) {
		this.eUsedCapacity = eUsedCapacity;
	}

	public BigDecimal getwUsedCapacity() {
		return wUsedCapacity;
	}

	public void setwUsedCapacity(BigDecimal wUsedCapacity) {
		this.wUsedCapacity = wUsedCapacity;
	}

	public BigDecimal getEwUsedCapacity() {
		return ewUsedCapacity;
	}

	public void setEwUsedCapacity(BigDecimal ewUsedCapacity) {
		this.ewUsedCapacity = ewUsedCapacity;
	}

	public BigDecimal getGSP() {
		return GSP;
	}

	public void setGSP(BigDecimal gSP) {
		GSP = gSP;
	}

	public BigDecimal getBYPASS() {
		return BYPASS;
	}

	public void setBYPASS(BigDecimal bYPASS) {
		BYPASS = bYPASS;
	}

	public BigDecimal getLNG() {
		return LNG;
	}

	public void setLNG(BigDecimal lNG) {
		LNG = lNG;
	}

	public BigDecimal getYDN() {
		return YDN;
	}

	public void setYDN(BigDecimal yDN) {
		YDN = yDN;
	}

	public BigDecimal getYTG() {
		return YTG;
	}

	public void setYTG(BigDecimal yTG) {
		YTG = yTG;
	}

	public BigDecimal getZTK() {
		return ZTK;
	}

	public void setZTK(BigDecimal zTK) {
		ZTK = zTK;
	}

	public BigDecimal geteFrom() {
		return eFrom;
	}

	public void seteFrom(BigDecimal eFrom) {
		this.eFrom = eFrom;
	}

	public BigDecimal getwFrom() {
		return wFrom;
	}

	public void setwFrom(BigDecimal wFrom) {
		this.wFrom = wFrom;
	}

	public BigDecimal geteEGAT() {
		return eEGAT;
	}

	public void seteEGAT(BigDecimal eEGAT) {
		this.eEGAT = eEGAT;
	}

	public BigDecimal geteIPP() {
		return eIPP;
	}

	public void seteIPP(BigDecimal eIPP) {
		this.eIPP = eIPP;
	}

	public BigDecimal geteSPP() {
		return eSPP;
	}

	public void seteSPP(BigDecimal eSPP) {
		this.eSPP = eSPP;
	}

	public BigDecimal geteIND() {
		return eIND;
	}

	public void seteIND(BigDecimal eIND) {
		this.eIND = eIND;
	}

	public BigDecimal geteNGD() {
		return eNGD;
	}

	public void seteNGD(BigDecimal eNGD) {
		this.eNGD = eNGD;
	}

	public BigDecimal geteNGV() {
		return eNGV;
	}

	public void seteNGV(BigDecimal eNGV) {
		this.eNGV = eNGV;
	}

	public BigDecimal getwEGAT() {
		return wEGAT;
	}

	public void setwEGAT(BigDecimal wEGAT) {
		this.wEGAT = wEGAT;
	}

	public BigDecimal getwIPP() {
		return wIPP;
	}

	public void setwIPP(BigDecimal wIPP) {
		this.wIPP = wIPP;
	}

	public BigDecimal getwSPP() {
		return wSPP;
	}

	public void setwSPP(BigDecimal wSPP) {
		this.wSPP = wSPP;
	}

	public BigDecimal getwIND() {
		return wIND;
	}

	public void setwIND(BigDecimal wIND) {
		this.wIND = wIND;
	}

	public BigDecimal getwNGD() {
		return wNGD;
	}

	public void setwNGD(BigDecimal wNGD) {
		this.wNGD = wNGD;
	}

	public BigDecimal getwNGV() {
		return wNGV;
	}

	public void setwNGV(BigDecimal wNGV) {
		this.wNGV = wNGV;
	}

	public BigDecimal getEwEGAT() {
		return ewEGAT;
	}

	public void setEwEGAT(BigDecimal ewEGAT) {
		this.ewEGAT = ewEGAT;
	}

	public BigDecimal getEwIPP() {
		return ewIPP;
	}

	public void setEwIPP(BigDecimal ewIPP) {
		this.ewIPP = ewIPP;
	}

	public BigDecimal getEwSPP() {
		return ewSPP;
	}

	public void setEwSPP(BigDecimal ewSPP) {
		this.ewSPP = ewSPP;
	}

	public BigDecimal getEwIND() {
		return ewIND;
	}

	public void setEwIND(BigDecimal ewIND) {
		this.ewIND = ewIND;
	}

	public BigDecimal getEwNGD() {
		return ewNGD;
	}

	public void setEwNGD(BigDecimal ewNGD) {
		this.ewNGD = ewNGD;
	}

	public BigDecimal getEwNGV() {
		return ewNGV;
	}

	public void setEwNGV(BigDecimal ewNGV) {
		this.ewNGV = ewNGV;
	}

	public BigDecimal getE_others() {
		return e_others;
	}

	public void setE_others(BigDecimal e_others) {
		this.e_others = e_others;
	}

	public BigDecimal getW_others() {
		return w_others;
	}

	public void setW_others(BigDecimal w_others) {
		this.w_others = w_others;
	}

	public BigDecimal getEw_others() {
		return ew_others;
	}

	public void setEw_others(BigDecimal ew_others) {
		this.ew_others = ew_others;
	}
	
	//Item 35

	public BigDecimal geteBvw10() {
		return eBvw10;
	}

	public void seteBvw10(BigDecimal eBvw10) {
		this.eBvw10 = eBvw10;
	}

	public BigDecimal getwBvw10() {
		return wBvw10;
	}

	public void setwBvw10(BigDecimal wBvw10) {
		this.wBvw10 = wBvw10;
	}

	public BigDecimal geteF2g() {
		return eF2g;
	}

	public void seteF2g(BigDecimal eF2g) {
		this.eF2g = eF2g;
	}

	public BigDecimal getwF2g() {
		return wF2g;
	}

	public void setwF2g(BigDecimal wF2g) {
		this.wF2g = wF2g;
	}

	public BigDecimal getE_east() {
		return e_east;
	}

	public void setE_east(BigDecimal e_east) {
		this.e_east = e_east;
	}

	public BigDecimal getE_west() {
		return e_west;
	}

	public void setE_west(BigDecimal e_west) {
		this.e_west = e_west;
	}
	
	//Fin Item 35

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"gasDay;shipperCode;shipperName;shortName;contractCode;eTotalEntry;wTotalEntry;ewTotalEntry;eTotalExit;wTotalExit;ewTotalExit;eImbalanceZone;wImbalanceZone;totalImbalanceZone;eInstructedFlow;wInstructedFlow;eShrinkageQuantity;wShrinkageQuantity;ePark;wPark;eUnpark;wUnpark;eSOTDPark;wSOTDPark;eEOTDPark;wEOTDPark;eMinInventory;wMinInventory;eResBalancingGas;wResBalancingGas;eMonthAdjust;wMonthAdjust;eImbalanceStock;wImbalanceStock;totalAIP;totalAIN;imbalancePercentage;eAccImbalanceMonth;wAccImbalanceMonth;eAccImbalance;wAccImbalance;eUsedCapacity;wUsedCapacity;GSP;BYPASS;LNG;YDN;YTG;ZTK;eFrom;wFrom;eBvw10;wBvw10;eEGAT;eIPP;e_others;wEGAT;wIPP;w_others;ewEGAT;ewIPP;ew_others;eF2g;wF2g;e_east;e_west");
		return builder.toString();
	}

	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder builder = new StringBuilder();

		builder.append((gasDay==null ? "" : sdf.format(gasDay))+";");
		builder.append((shipperCode==null ? "" : shipperCode) +";");
		builder.append((shipperName==null ? "" : shipperName) +";");
		builder.append((shortName==null ? "" : shortName) +";");
		builder.append((contractCode==null ? "" : contractCode) +";");
		builder.append((eTotalEntry==null ? "" : eTotalEntry.toPlainString()) +";");
		builder.append((wTotalEntry==null ? "" : wTotalEntry.toPlainString()) +";");
		builder.append((ewTotalEntry==null ? "" : ewTotalEntry.toPlainString()) +";");
		builder.append((eTotalExit==null ? "" : eTotalExit.toPlainString()) +";");
		builder.append((wTotalExit==null ? "" : wTotalExit.toPlainString()) +";");
		builder.append((ewTotalExit==null ? "" : ewTotalExit.toPlainString()) +";");
		builder.append((eImbalanceZone==null ? "" : eImbalanceZone.toPlainString()) +";");
		builder.append((wImbalanceZone==null ? "" : wImbalanceZone.toPlainString()) +";");
		builder.append((totalImbalanceZone==null ? "" : totalImbalanceZone.toPlainString()) +";");
		builder.append((eInstructedFlow==null ? "" : eInstructedFlow.toPlainString()) +";");
		builder.append((wInstructedFlow==null ? "" : wInstructedFlow.toPlainString()) +";");
		builder.append((eShrinkageQuantity==null ? "" : eShrinkageQuantity.toPlainString()) +";");
		builder.append((wShrinkageQuantity==null ? "" : wShrinkageQuantity.toPlainString()) +";");
		builder.append((ePark==null ? "" : ePark.toPlainString()) +";");
		builder.append((wPark==null ? "" : wPark.toPlainString()) +";");
		builder.append((eUnpark==null ? "" : eUnpark.toPlainString()) +";");
		builder.append((wUnpark==null ? "" : wUnpark.toPlainString()) +";");
		builder.append((eSOTDPark==null ? "" : eSOTDPark.toPlainString()) +";");
		builder.append((wSOTDPark==null ? "" : wSOTDPark.toPlainString()) +";");
		builder.append((eEOTDPark==null ? "" : eEOTDPark.toPlainString()) +";");
		builder.append((wEOTDPark==null ? "" : wEOTDPark.toPlainString()) +";");
		builder.append((eMinInventory==null ? "" : eMinInventory.toPlainString()) +";");
		builder.append((wMinInventory==null ? "" : wMinInventory.toPlainString()) +";");
		builder.append((eResBalancingGas==null ? "" : eResBalancingGas.toPlainString()) +";");
		builder.append((wResBalancingGas==null ? "" : wResBalancingGas.toPlainString()) +";");
		builder.append((eMonthAdjust==null ? "" : eMonthAdjust.toPlainString()) +";");
		builder.append((wMonthAdjust==null ? "" : wMonthAdjust.toPlainString()) +";");
		builder.append((eImbalanceStock==null ? "" : eImbalanceStock.toPlainString()) +";");
		builder.append((wImbalanceStock==null ? "" : wImbalanceStock.toPlainString()) +";");
		builder.append((totalAIP==null ? "" : totalAIP.toPlainString()) +";");
		builder.append((totalAIN==null ? "" : totalAIN.toPlainString()) +";");
		builder.append((imbalancePercentage==null ? "" : imbalancePercentage.toPlainString()) +";");
		builder.append((eAccImbalanceMonth==null ? "" : eAccImbalanceMonth.toPlainString()) +";");
		builder.append((wAccImbalanceMonth==null ? "" : wAccImbalanceMonth.toPlainString()) +";");
		builder.append((eAccImbalance==null ? "" : eAccImbalance.toPlainString()) +";");
		builder.append((wAccImbalance==null ? "" : wAccImbalance.toPlainString()) +";");
		builder.append((eUsedCapacity==null ? "" : eUsedCapacity.toPlainString()) +";");
		builder.append((wUsedCapacity==null ? "" : wUsedCapacity.toPlainString()) +";");
		builder.append((GSP==null ? "" : GSP.toPlainString()) +";");
		builder.append((BYPASS==null ? "" : BYPASS.toPlainString()) +";");
		builder.append((LNG==null ? "" : LNG.toPlainString()) +";");
		builder.append((YDN==null ? "" : YDN.toPlainString()) +";");
		builder.append((YTG==null ? "" : YTG.toPlainString()) +";");
		builder.append((ZTK==null ? "" : ZTK.toPlainString()) +";");
		builder.append((eFrom==null ? "" : eFrom.toPlainString()) +";");
		builder.append((wFrom==null ? "" : wFrom.toPlainString()) +";");
		builder.append((eBvw10==null ? "" : eBvw10.toPlainString()) +";");
		builder.append((wBvw10==null ? "" : wBvw10.toPlainString()) +";");
		builder.append((eEGAT==null ? "" : eEGAT.toPlainString()) +";");
		builder.append((eIPP==null ? "" : eIPP.toPlainString()) +";");
		builder.append((e_others==null ? "" : e_others.toPlainString()) +";");
		builder.append((wEGAT==null ? "" : wEGAT.toPlainString()) +";");
		builder.append((wIPP==null ? "" : wIPP.toPlainString()) +";");
		builder.append((w_others==null ? "" : w_others.toPlainString()) +";");
		builder.append((ewEGAT==null ? "" : ewEGAT.toPlainString()) +";");
		builder.append((ewIPP==null ? "" : ewIPP.toPlainString()) +";");
		builder.append((ew_others==null ? "" : ew_others.toPlainString()) +";");
		builder.append((eF2g==null ? "" : eF2g.toPlainString()) +";");
		builder.append((wF2g==null ? "" : wF2g.toPlainString()) +";");
		builder.append((e_east==null ? "" : e_east.toPlainString()) +";");
		builder.append((e_west==null ? "" : e_west.toPlainString()));
		return builder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BalanceReportBean [sdf=");
		builder.append(sdf);
		builder.append(", gasDay=");
		builder.append(gasDay);
		builder.append(", shipperCode=");
		builder.append(shipperCode);
		builder.append(", shipperName=");
		builder.append(shipperName);
		builder.append(", shortName=");
		builder.append(shortName);
		builder.append(", contractId=");
		builder.append(contractId);
		builder.append(", contractCode=");
		builder.append(contractCode);
		builder.append(", isTotal=");
		builder.append(isTotal);
		builder.append(", isTotalAllShippers=");
		builder.append(isTotalAllShippers);
		builder.append(", versionDate=");
		builder.append(versionDate);
		builder.append(", eTotalEntry=");
		builder.append(eTotalEntry);
		builder.append(", wTotalEntry=");
		builder.append(wTotalEntry);
		builder.append(", ewTotalEntry=");
		builder.append(ewTotalEntry);
		builder.append(", eTotalExit=");
		builder.append(eTotalExit);
		builder.append(", wTotalExit=");
		builder.append(wTotalExit);
		builder.append(", ewTotalExit=");
		builder.append(ewTotalExit);
		builder.append(", eImbalanceZone=");
		builder.append(eImbalanceZone);
		builder.append(", wImbalanceZone=");
		builder.append(wImbalanceZone);
		builder.append(", ewImbalanceZone=");
		builder.append(ewImbalanceZone);
		builder.append(", totalImbalanceZone=");
		builder.append(totalImbalanceZone);
		builder.append(", eInstructedFlow=");
		builder.append(eInstructedFlow);
		builder.append(", wInstructedFlow=");
		builder.append(wInstructedFlow);
		builder.append(", ewInstructedFlow=");
		builder.append(ewInstructedFlow);
		builder.append(", eShrinkageQuantity=");
		builder.append(eShrinkageQuantity);
		builder.append(", wShrinkageQuantity=");
		builder.append(wShrinkageQuantity);
		builder.append(", ewShrinkageQuantity=");
		builder.append(ewShrinkageQuantity);
		builder.append(", ePark=");
		builder.append(ePark);
		builder.append(", wPark=");
		builder.append(wPark);
		builder.append(", ewPark=");
		builder.append(ewPark);
		builder.append(", eUnpark=");
		builder.append(eUnpark);
		builder.append(", wUnpark=");
		builder.append(wUnpark);
		builder.append(", ewUnpark=");
		builder.append(ewUnpark);
		builder.append(", eSOTDPark=");
		builder.append(eSOTDPark);
		builder.append(", wSOTDPark=");
		builder.append(wSOTDPark);
		builder.append(", ewSOTDPark=");
		builder.append(ewSOTDPark);
		builder.append(", eEOTDPark=");
		builder.append(eEOTDPark);
		builder.append(", wEOTDPark=");
		builder.append(wEOTDPark);
		builder.append(", ewEOTDPark=");
		builder.append(ewEOTDPark);
		builder.append(", eMinInventory=");
		builder.append(eMinInventory);
		builder.append(", wMinInventory=");
		builder.append(wMinInventory);
		builder.append(", ewMinInventory=");
		builder.append(ewMinInventory);
		builder.append(", eResBalancingGas=");
		builder.append(eResBalancingGas);
		builder.append(", wResBalancingGas=");
		builder.append(wResBalancingGas);
		builder.append(", ewResBalancingGas=");
		builder.append(ewResBalancingGas);
		builder.append(", totalAIP=");
		builder.append(totalAIP);
		builder.append(", totalAIN=");
		builder.append(totalAIN);
		builder.append(", imbalancePercentage=");
		builder.append(imbalancePercentage);
		builder.append(", eImbalanceStock=");
		builder.append(eImbalanceStock);
		builder.append(", wImbalanceStock=");
		builder.append(wImbalanceStock);
		builder.append(", ewImbalanceStock=");
		builder.append(ewImbalanceStock);
		builder.append(", eMonthAdjust=");
		builder.append(eMonthAdjust);
		builder.append(", wMonthAdjust=");
		builder.append(wMonthAdjust);
		builder.append(", ewMonthAdjust=");
		builder.append(ewMonthAdjust);
		builder.append(", eAccImbalanceMonth=");
		builder.append(eAccImbalanceMonth);
		builder.append(", wAccImbalanceMonth=");
		builder.append(wAccImbalanceMonth);
		builder.append(", ewAccImbalanceMonth=");
		builder.append(ewAccImbalanceMonth);
		builder.append(", eAccImbalance=");
		builder.append(eAccImbalance);
		builder.append(", wAccImbalance=");
		builder.append(wAccImbalance);
		builder.append(", ewAccImbalance=");
		builder.append(ewAccImbalance);
		builder.append(", eUsedCapacity=");
		builder.append(eUsedCapacity);
		builder.append(", wUsedCapacity=");
		builder.append(wUsedCapacity);
		builder.append(", ewUsedCapacity=");
		builder.append(ewUsedCapacity);
		builder.append(", GSP=");
		builder.append(GSP);
		builder.append(", BYPASS=");
		builder.append(BYPASS);
		builder.append(", LNG=");
		builder.append(LNG);
		builder.append(", YDN=");
		builder.append(YDN);
		builder.append(", YTG=");
		builder.append(YTG);
		builder.append(", ZTK=");
		builder.append(ZTK);
		builder.append(", eFrom=");
		builder.append(eFrom);
		builder.append(", wFrom=");
		builder.append(wFrom);
		builder.append(", eBvw10=");
		builder.append(eBvw10);
		builder.append(", wBvw10=");
		builder.append(wBvw10);
		builder.append(", eEGAT=");
		builder.append(eEGAT);
		builder.append(", eIPP=");
		builder.append(eIPP);
		builder.append(", eSPP=");
		builder.append(eSPP);
		builder.append(", eIND=");
		builder.append(eIND);
		builder.append(", eNGD=");
		builder.append(eNGD);
		builder.append(", eNGV=");
		builder.append(eNGV);
		builder.append(", wEGAT=");
		builder.append(wEGAT);
		builder.append(", wIPP=");
		builder.append(wIPP);
		builder.append(", wSPP=");
		builder.append(wSPP);
		builder.append(", wIND=");
		builder.append(wIND);
		builder.append(", wNGD=");
		builder.append(wNGD);
		builder.append(", wNGV=");
		builder.append(wNGV);
		builder.append(", ewEGAT=");
		builder.append(ewEGAT);
		builder.append(", ewIPP=");
		builder.append(ewIPP);
		builder.append(", ewSPP=");
		builder.append(ewSPP);
		builder.append(", ewIND=");
		builder.append(ewIND);
		builder.append(", ewNGD=");
		builder.append(ewNGD);
		builder.append(", ewNGV=");
		builder.append(ewNGV);
		builder.append(", e_others=");
		builder.append(e_others);
		builder.append(", w_others=");
		builder.append(w_others);
		builder.append(", ew_others=");
		builder.append(ew_others);
		builder.append(", eF2g=");
		builder.append(eF2g);
		builder.append(", wF2g=");
		builder.append(wF2g);
		builder.append(", e_east=");
		builder.append(e_east);
		builder.append(", e_west=");
		builder.append(e_west);
		builder.append("]");
		return builder.toString();
	}

}