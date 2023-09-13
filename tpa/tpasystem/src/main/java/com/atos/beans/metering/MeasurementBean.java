package com.atos.beans.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6371860709834194903L;

	private BigDecimal measurementId;
	private Date gasDay;
	private String zoneCode;
	private String areaCode;
	private String meteringPointCode;
	private String customerTypeDesc;
	private BigDecimal volume;
	private BigDecimal hv;
	private BigDecimal energy;
	private Date registerTimestamp;
	private Date versionDate;
	private String metInputCode;
	private String origin;
	private List<MeasureGasQualityParamBean> gasParams;
	
	public MeasurementBean() {
		this.measurementId = null;
		this.gasDay = null;
		this.zoneCode = null;
		this.areaCode = null;
		this.meteringPointCode = null;
		this.customerTypeDesc = null;
		this.volume = null;
		this.hv = null;
		this.energy = null;
		this.registerTimestamp = null;
		this.versionDate = null;
		this.metInputCode = null;
		this.origin = null;
		this.gasParams = new ArrayList<MeasureGasQualityParamBean>();
	}

	public BigDecimal getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(BigDecimal measurementId) {
		this.measurementId = measurementId;
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getMeteringPointCode() {
		return meteringPointCode;
	}

	public void setMeteringPointCode(String meteringPointCode) {
		this.meteringPointCode = meteringPointCode;
	}

	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}

	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getHv() {
		return hv;
	}

	public void setHv(BigDecimal hv) {
		this.hv = hv;
	}

	public BigDecimal getEnergy() {
		return energy;
	}

	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}

	public Date getRegisterTimestamp() {
		return registerTimestamp;
	}

	public void setRegisterTimestamp(Date registerTimestamp) {
		this.registerTimestamp = registerTimestamp;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	public String getMetInputCode() {
		return metInputCode;
	}

	public void setMetInputCode(String metInputCode) {
		this.metInputCode = metInputCode;
	}

	public List<MeasureGasQualityParamBean> getGasParams() {
		return gasParams;
	}

	public void setGasParams(List<MeasureGasQualityParamBean> gasParams) {
		this.gasParams = gasParams;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String toCSVHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"gasDay;zoneCode;areaCode;meteringPointCode;customerTypeDesc;volume;hv;energy;registerTimestamp;versionDate;metInputCode;origin");
		for(int i=0;i<gasParams.size();i++) {
			builder.append(";paramDesc;unitDesc;value");
		}

		
		return builder.toString();
	}

	
	public String toCSV() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append((gasDay==null ? "" : sdf.format(gasDay))+";");
		builder.append((zoneCode==null ? "" : zoneCode)+";");
		builder.append((areaCode== null ? "" : areaCode)+";");
		builder.append((meteringPointCode==null ? "" : meteringPointCode)+";");
		builder.append((customerTypeDesc==null ? "" : customerTypeDesc)+";");
		builder.append((volume==null ? "" : volume.doubleValue())+";");
		builder.append((hv==null ? "" : hv.doubleValue())+";");
		builder.append((energy==null ? "" : energy.doubleValue())+";");
		builder.append((registerTimestamp==null ? "" : sdf.format(registerTimestamp))+";");
		builder.append((versionDate==null ? "" : sdf.format(versionDate))+";");
		builder.append((metInputCode==null ? "" : metInputCode)+";");
		builder.append((origin==null ? "" : origin));
			
		for(int i=0;i<gasParams.size();i++) {
			
			builder.append(";" +(gasParams.get(i).getParamDesc()==null ? "" : gasParams.get(i).getParamDesc()));
			builder.append(";" +(gasParams.get(i).getUnitDesc()==null ? "" : gasParams.get(i).getUnitDesc()));
			builder.append(";" +(gasParams.get(i).getValue()== null ? "" : gasParams.get(i).getValue().doubleValue()));
		}
		return builder.toString();
	}

	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeasurementBean [measurementId=");
		builder.append(measurementId);
		builder.append(", gasDay=");
		builder.append(gasDay);
		builder.append(", zoneCode=");
		builder.append(zoneCode);
		builder.append(", areaCode=");
		builder.append(areaCode);
		builder.append(", meteringPointCode=");
		builder.append(meteringPointCode);
		builder.append(", customerTypeDesc=");
		builder.append(customerTypeDesc);
		builder.append(", volume=");
		builder.append(volume);
		builder.append(", hv=");
		builder.append(hv);
		builder.append(", energy=");
		builder.append(energy);
		builder.append(", registerTimestamp=");
		builder.append(registerTimestamp);
		builder.append(", versionDate=");
		builder.append(versionDate);
		builder.append(", metInputCode=");
		builder.append(metInputCode);
		builder.append(", origin=");
		builder.append(origin);
		builder.append(", gasParams=");
		builder.append(gasParams);
		builder.append("]");
		return builder.toString();
	}

}
