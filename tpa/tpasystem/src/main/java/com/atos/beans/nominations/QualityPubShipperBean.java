package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QualityPubShipperBean implements Serializable {

	private static final long serialVersionUID = 2852585390518027567L;
	
	private BigDecimal areaId;
	private String areaCode;
	private Date nominationDate;
	private String parameterCode;
	private BigDecimal value;
	private String unitDesc;
	private String isWarned;
	
	public QualityPubShipperBean() {
		super();
		this.areaId = null;
		this.areaCode = null;
		this.nominationDate = null;
		this.parameterCode = null;
		this.value = null;
		this.unitDesc = null;
		this.isWarned = null;
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

	
	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append("areaCode;nominationDate;parameterCode;value;unitDesc;isWarned");
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
		builder.append((isWarned==null ? "" : isWarned));
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QualityPubShipperBean [areaId=");
		builder.append(areaId);
		builder.append(", areaCode=");
		builder.append(areaCode);
		builder.append(", nominationDate=");
		builder.append(nominationDate);
		builder.append(", parameterCode=");
		builder.append(parameterCode);
		builder.append(", value=");
		builder.append(value);
		builder.append(", unitDesc=");
		builder.append(unitDesc);
		builder.append(", isWarned=");
		builder.append(isWarned);
		builder.append("]");
		return builder.toString();
	}

	
}
