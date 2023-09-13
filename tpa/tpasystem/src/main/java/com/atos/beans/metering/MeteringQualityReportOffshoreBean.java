package com.atos.beans.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class MeteringQualityReportOffshoreBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6267201431749661420L;

	private String gasQualityParam;
	private String unit;
	private BigDecimal rayongMin;
	private BigDecimal rayongMax;
	private BigDecimal khanomMin;
	private BigDecimal khanomMax;
		
	public MeteringQualityReportOffshoreBean() {
		this.gasQualityParam = null;
		this.unit= null;
		this.rayongMin= null;
		this.rayongMax= null;
		this.khanomMin= null;
		this.khanomMax= null;
	}

	public String getGasQualityParam() {
		return gasQualityParam;
	}

	public void setGasQualityParam(String gasQualityParam) {
		this.gasQualityParam = gasQualityParam;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getRayongMin() {
		return rayongMin;
	}

	public void setRayongMin(BigDecimal rayongMin) {
		this.rayongMin = rayongMin;
	}

	public BigDecimal getRayongMax() {
		return rayongMax;
	}

	public void setRayongMax(BigDecimal rayongMax) {
		this.rayongMax = rayongMax;
	}

	public BigDecimal getKhanomMin() {
		return khanomMin;
	}

	public void setKhanomMin(BigDecimal khanomMin) {
		this.khanomMin = khanomMin;
	}

	public BigDecimal getKhanomMax() {
		return khanomMax;
	}

	public void setKhanomMax(BigDecimal khanomMax) {
		this.khanomMax = khanomMax;
	}

	@Override
	public String toString() {
		return "MeteringQualityReportOffshoreBean [gasQualityParam=" + gasQualityParam + ", unit=" + unit
				+ ", rayongMin=" + rayongMin + ", rayongMax=" + rayongMax + ", khanomMin=" + khanomMin + ", khanomMax="
				+ khanomMax + "]";
	}
}
