package com.atos.beans.balancing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ReserveBalancingGasContractDetailBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2243583078442192836L;

	// Al construirse el objeto se asigna el randomId para asignarle un identificador unico
	// en la pantalla (antes de que se inserte en BD) y poder hacer seleccion multiple.
	private Double randomId;
	private BigDecimal detailId;
	private BigDecimal contractId;
	private BigDecimal pointId;
	private String pointCode;
	private BigDecimal pointTypeId;	
	private String pointTypeCode;
	private BigDecimal zoneId;	
	private String zoneCode;
	private Date startDate;
	private Date endDate;
	private BigDecimal dailyReservedCap;		// Unidad MMBTU/d
	private BigDecimal areaId;
	private BigDecimal idnSystem;
	
	public ReserveBalancingGasContractDetailBean() {
		this.randomId = new Double(Math.random());
		this.detailId = null;
		this.contractId = null;
		this.pointId = null;
		this.pointCode = null;
		this.pointTypeId = null;
		this.pointTypeCode = null;
		this.zoneId = null;
		this.zoneCode = null;
		this.startDate = null;
		this.endDate = null;
		this.dailyReservedCap = null;
		this.areaId = null;
	}

	public Double getRandomId() {
		return randomId;
	}

	public void setRandomId(Double randomId) {
		this.randomId = randomId;
	}

	public BigDecimal getDetailId() {
		return detailId;
	}

	public void setDetailId(BigDecimal detailId) {
		this.detailId = detailId;
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public BigDecimal getPointId() {
		return pointId;
	}

	public void setPointId(BigDecimal pointId) {
		this.pointId = pointId;
	}

	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public BigDecimal getPointTypeId() {
		return pointTypeId;
	}

	public void setPointTypeId(BigDecimal pointTypeId) {
		this.pointTypeId = pointTypeId;
	}

	public String getPointTypeCode() {
		return pointTypeCode;
	}

	public void setPointTypeCode(String pointTypeCode) {
		this.pointTypeCode = pointTypeCode;
	}

	public BigDecimal getZoneId() {
		return zoneId;
	}

	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getDailyReservedCap() {
		return dailyReservedCap;
	}

	public void setDailyReservedCap(BigDecimal dailyReservedCap) {
		this.dailyReservedCap = dailyReservedCap;
	}

	@Override
	public String toString() {
		return "ReserveBalancingGasContractDetailBean [randomId=" + randomId + ", detailId=" + detailId
				+ ", contractId=" + contractId + ", pointId=" + pointId + ", pointCode=" + pointCode + ", pointTypeId="
				+ pointTypeId + ", pointTypeCode=" + pointTypeCode + ", zoneId=" + zoneId + ", zoneCode=" + zoneCode
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", dailyReservedCap=" + dailyReservedCap + "]";
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public BigDecimal getIdnSystem() {
		return idnSystem;
	}

	public void setIdnSystem(BigDecimal idnSystem) {
		this.idnSystem = idnSystem;
	}

}
