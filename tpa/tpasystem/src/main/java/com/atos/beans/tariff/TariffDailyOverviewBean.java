package com.atos.beans.tariff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class TariffDailyOverviewBean  extends UserAudBean implements Serializable{

	private static final long serialVersionUID = -5255211382691787452L;
	
	private BigDecimal sortBy = new BigDecimal(1);
	
	private Date detailGasDay;
	private BigDecimal dailyBookedEntryCapacity;
    private BigDecimal dailyCapacityCharge;
    	
    private BigDecimal dailyAllocatedExitValue;
    private BigDecimal dailyAllocatedEntryValue; //CH715 SAT FASE III
    private BigDecimal dailyCommodityCharge;
    
    private BigDecimal dailyEntryCapacityOU;
    private BigDecimal dailyOriginalEntryCapacityOU; //CH710 SAT FASE III
    private BigDecimal dailyReducedEntryCapacityOU; // CH522 SAT quitar columna (no se pinta)
    private BigDecimal dailyEntryCapacityOUCharge;
    
    private BigDecimal dailyExitCapacityOU;
    private BigDecimal dailyOriginalExitCapacityOU; //CH710 SAT FASE III
    private BigDecimal dailyReducedExitCapacityOU;	// CH522 SAT quitar columna (no se pinta)
    private BigDecimal dailyExitCapacityOUCharge;
    
    private BigDecimal dailyOriginalImbalance; //CH711 SAT FASE III  se añade dailyOriginalImbalance
    private BigDecimal dailyImbalance;
    private BigDecimal dailyImbalanceCharge;
    
	
	public TariffDailyOverviewBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Date getDetailGasDay() {
		return detailGasDay;
	}


	public void setDetailGasDay(Date detailGasDay) {
		this.detailGasDay = detailGasDay;
	}


	public BigDecimal getDailyBookedEntryCapacity() {
		return dailyBookedEntryCapacity;
	}


	public void setDailyBookedEntryCapacity(BigDecimal dailyBookedEntryCapacity) {
		this.dailyBookedEntryCapacity = dailyBookedEntryCapacity;
	}


	public BigDecimal getDailyCapacityCharge() {
		return dailyCapacityCharge;
	}


	public void setDailyCapacityCharge(BigDecimal dailyCapacityCharge) {
		this.dailyCapacityCharge = dailyCapacityCharge;
	}


	public BigDecimal getDailyAllocatedExitValue() {
		return dailyAllocatedExitValue;
	}


	public void setDailyAllocatedExitValue(BigDecimal dailyAllocatedExitValue) {
		this.dailyAllocatedExitValue = dailyAllocatedExitValue;
	}


	public BigDecimal getDailyCommodityCharge() {
		return dailyCommodityCharge;
	}


	public void setDailyCommodityCharge(BigDecimal dailyCommodityCharge) {
		this.dailyCommodityCharge = dailyCommodityCharge;
	}


	

	public BigDecimal getDailyImbalance() {
		return dailyImbalance;
	}


	public void setDailyImbalance(BigDecimal dailyImbalance) {
		this.dailyImbalance = dailyImbalance;
	}


	public BigDecimal getDailyImbalanceCharge() {
		return dailyImbalanceCharge;
	}


	public void setDailyImbalanceCharge(BigDecimal dailyImbalanceCharge) {
		this.dailyImbalanceCharge = dailyImbalanceCharge;
	}


	public BigDecimal getDailyEntryCapacityOU() {
		return dailyEntryCapacityOU;
	}


	public void setDailyEntryCapacityOU(BigDecimal dailyEntryCapacityOU) {
		this.dailyEntryCapacityOU = dailyEntryCapacityOU;
	}


	public BigDecimal getDailyReducedEntryCapacityOU() {
		return dailyReducedEntryCapacityOU;
	}


	public void setDailyReducedEntryCapacityOU(BigDecimal dailyReducedEntryCapacityOU) {
		this.dailyReducedEntryCapacityOU = dailyReducedEntryCapacityOU;
	}


	public BigDecimal getDailyEntryCapacityOUCharge() {
		return dailyEntryCapacityOUCharge;
	}


	public void setDailyEntryCapacityOUCharge(BigDecimal dailyEntryCapacityOUCharge) {
		this.dailyEntryCapacityOUCharge = dailyEntryCapacityOUCharge;
	}


	public BigDecimal getDailyExitCapacityOU() {
		return dailyExitCapacityOU;
	}


	public void setDailyExitCapacityOU(BigDecimal dailyExitCapacityOU) {
		this.dailyExitCapacityOU = dailyExitCapacityOU;
	}


	public BigDecimal getDailyReducedExitCapacityOU() {
		return dailyReducedExitCapacityOU;
	}


	public void setDailyReducedExitCapacityOU(BigDecimal dailyReducedExitCapacityOU) {
		this.dailyReducedExitCapacityOU = dailyReducedExitCapacityOU;
	}


	public BigDecimal getDailyExitCapacityOUCharge() {
		return dailyExitCapacityOUCharge;
	}


	public void setDailyExitCapacityOUCharge(BigDecimal dailyExitCapacityOUCharge) {
		this.dailyExitCapacityOUCharge = dailyExitCapacityOUCharge;
	}




	public BigDecimal getSortBy() {
		return sortBy;
	}


	public void setSortBy(BigDecimal sortBy) {
		this.sortBy = sortBy;
	}


	public BigDecimal getDailyOriginalImbalance() {
		return dailyOriginalImbalance;
	}


	public void setDailyOriginalImbalance(BigDecimal dailyOriginalImbalance) {
		this.dailyOriginalImbalance = dailyOriginalImbalance;
	}


	public BigDecimal getDailyAllocatedEntryValue() {
		return dailyAllocatedEntryValue;
	}


	public void setDailyAllocatedEntryValue(BigDecimal dailyAllocatedEntryValue) {
		this.dailyAllocatedEntryValue = dailyAllocatedEntryValue;
	}


	public BigDecimal getDailyOriginalEntryCapacityOU() {
		return dailyOriginalEntryCapacityOU;
	}


	public void setDailyOriginalEntryCapacityOU(BigDecimal dailyOriginalEntryCapacityOU) {
		this.dailyOriginalEntryCapacityOU = dailyOriginalEntryCapacityOU;
	}


	public BigDecimal getDailyOriginalExitCapacityOU() {
		return dailyOriginalExitCapacityOU;
	}


	public void setDailyOriginalExitCapacityOU(BigDecimal dailyOriginalExitCapacityOU) {
		this.dailyOriginalExitCapacityOU = dailyOriginalExitCapacityOU;
	}


	@Override
	public String toString() {
		return "TariffDailyOverviewBean [sortBy=" + sortBy + ", detailGasDay=" + detailGasDay
				+ ", dailyBookedEntryCapacity=" + dailyBookedEntryCapacity + ", dailyCapacityCharge="
				+ dailyCapacityCharge + ", dailyAllocatedExitValue=" + dailyAllocatedExitValue
				+ ", dailyAllocatedEntryValue=" + dailyAllocatedEntryValue + ", dailyCommodityCharge="
				+ dailyCommodityCharge + ", dailyEntryCapacityOU=" + dailyEntryCapacityOU
				+ ", dailyOriginalEntryCapacityOU=" + dailyOriginalEntryCapacityOU + ", dailyReducedEntryCapacityOU="
				+ dailyReducedEntryCapacityOU + ", dailyEntryCapacityOUCharge=" + dailyEntryCapacityOUCharge
				+ ", dailyExitCapacityOU=" + dailyExitCapacityOU + ", dailyOriginalExitCapacityOU="
				+ dailyOriginalExitCapacityOU + ", dailyReducedExitCapacityOU=" + dailyReducedExitCapacityOU
				+ ", dailyExitCapacityOUCharge=" + dailyExitCapacityOUCharge + ", dailyOriginalImbalance="
				+ dailyOriginalImbalance + ", dailyImbalance=" + dailyImbalance + ", dailyImbalanceCharge="
				+ dailyImbalanceCharge + "]";
	}


}
