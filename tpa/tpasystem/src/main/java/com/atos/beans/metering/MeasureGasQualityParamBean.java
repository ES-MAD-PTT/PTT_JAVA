package com.atos.beans.metering;

import java.io.Serializable;
import java.math.BigDecimal;

public class MeasureGasQualityParamBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -90017505561278025L;

	private BigDecimal measureGasParamId;
	private BigDecimal measurementId;
	private BigDecimal paramId;
	private String paramDesc;
	private String unitDesc;
	private BigDecimal value;

	public MeasureGasQualityParamBean() {
		this.measureGasParamId = null;
		this.measurementId = null;
		this.paramId = null;
		this.paramDesc = null;
		this.unitDesc = null;
		this.value = null;
	}

	public BigDecimal getMeasureGasParamId() {
		return measureGasParamId;
	}

	public void setMeasureGasParamId(BigDecimal measureGasParamId) {
		this.measureGasParamId = measureGasParamId;
	}

	public BigDecimal getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(BigDecimal measurementId) {
		this.measurementId = measurementId;
	}

	public BigDecimal getParamId() {
		return paramId;
	}

	public void setParamId(BigDecimal paramId) {
		this.paramId = paramId;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MeasureGasQualityParamBean [measureGasParamId=" + measureGasParamId + ", measurementId=" + measurementId
				+ ", paramId=" + paramId + ", paramDesc=" + paramDesc + ", unitDesc=" + unitDesc + ", value=" + value
				+ "]";
	}

}
