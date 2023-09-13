package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QualityPublicationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160599673733279558L;
	private BigDecimal areaId;
	private String areaCode;
	private Date nominationDate;
	private String parameterCode;
	private BigDecimal value;
	private String unitDesc;
	private String isWarned;
	private BigDecimal simulation;
	private String isWarnedSim;
	
	public QualityPublicationBean() {
		super();
		this.areaId = null;
		this.areaCode = null;
		this.nominationDate = null;
		this.parameterCode = null;
		this.value = null;
		this.unitDesc = null;
		this.isWarned = null;
		this.simulation = null;
		this.isWarnedSim = null;
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Date getNominationDate() {
		return nominationDate;
	}

	public void setNominationDate(Date nominationDate) {
		this.nominationDate = nominationDate;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	
	public String getIsWarned() {
		return isWarned;
	}

	public void setIsWarned(String isWarned) {
		this.isWarned = isWarned;
	}

	public BigDecimal getSimulation() {
		return simulation;
	}

	public void setSimulation(BigDecimal simulation) {
		this.simulation = simulation;
	}

	public String getIsWarnedSim() {
		return isWarnedSim;
	}

	public void setIsWarnedSim(String isWarnedSim) {
		this.isWarnedSim = isWarnedSim;
	}

	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append("areaCode;nominationDate;parameterCode;value;unitDesc;isWarned;simulation;isWarnedSim");
		return builder.toString();
	}

	public String toCSV() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder builder = new StringBuilder();
		builder.append((areaCode==null ? "" : areaCode)+";");
		builder.append((nominationDate==null ? "" : sdf.format(nominationDate))+";");
		builder.append((parameterCode==null ? "" : parameterCode)+";");
		builder.append((value==null ? "" : value.doubleValue())+";");
		builder.append((unitDesc==null ? "" : unitDesc)+";");
		builder.append((isWarned==null ? "" : isWarned)+";");
		builder.append((simulation==null ? "" : simulation)+";");
		builder.append((isWarnedSim== null ? "" : isWarnedSim));
		return builder.toString();
	}

	
	@Override
	public String toString() {
		return "QualityPublicationBean [areaId=" + areaId + ", areaCode=" + areaCode + ", nominationDate="
				+ nominationDate + ", parameterCode=" + parameterCode + ", value=" + value + ", unitDesc=" + unitDesc
				+ ", isWarned=" + isWarned + ", simulation=" + simulation + ", isWarnedSim=" + isWarnedSim + "]";
	}
}
