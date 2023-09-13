package com.atos.filters.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class AllocationReviewFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5467770145599269113L;

	private Date gasDay;
	private BigDecimal shipperId;
	private BigDecimal contractId;
	private BigDecimal[] zoneIds;
	private BigDecimal[] areaIds;
	private BigDecimal[] nomPointIds;
	private String[] statusCode;
	private String reviewCode;
	private BigDecimal factorFromDefaultUnit;	// Para que en la consulta se transforme la unidad de energia.
	private BigDecimal systemId;
	
	public AllocationReviewFilter() {
		this.gasDay = null;
		this.shipperId = null;
		this.contractId = null;
		this.zoneIds = null;
		this.areaIds = null;
		this.nomPointIds = null;
		this.statusCode = null;
		this.reviewCode = null;
		this.factorFromDefaultUnit = null;
		this.systemId = null;
	}

	public AllocationReviewFilter(AllocationReviewFilter _filter) {
		this.gasDay = _filter.getGasDay();
		this.shipperId = _filter.getShipperId();
		this.contractId = _filter.getContractId();
		if(_filter.getZoneIds()!= null) {
			this.zoneIds = new BigDecimal[_filter.getZoneIds().length];
			System.arraycopy( _filter.getZoneIds(), 0, this.zoneIds, 0, _filter.getZoneIds().length );
		}
		if(_filter.getAreaIds()!= null) {
			this.areaIds = new BigDecimal[_filter.getAreaIds().length];
			System.arraycopy( _filter.getAreaIds(), 0, this.areaIds, 0, _filter.getAreaIds().length );
		}
		if(_filter.getNomPointIds()!= null) {
			this.nomPointIds = new BigDecimal[_filter.getNomPointIds().length];
			System.arraycopy( _filter.getNomPointIds(), 0, this.nomPointIds, 0, _filter.getNomPointIds().length );
		}
		if(_filter.getStatusCode()!= null) {
			this.statusCode = new String[_filter.getStatusCode().length];
			System.arraycopy( _filter.getStatusCode(), 0, this.statusCode, 0, _filter.getStatusCode().length );
		}
		this.reviewCode = _filter.getReviewCode();
		this.factorFromDefaultUnit = _filter.getFactorFromDefaultUnit();
		this.systemId = _filter.getSystemId();
	}

	public Date getGasDay() {
		return gasDay;
	}

	public void setGasDay(Date gasDay) {
		this.gasDay = gasDay;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public BigDecimal[] getZoneIds() {
		return zoneIds;
	}

	public void setZoneIds(BigDecimal[] zoneIds) {
		this.zoneIds = zoneIds;
	}

	public BigDecimal[] getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(BigDecimal[] areaIds) {
		this.areaIds = areaIds;
	}

	public BigDecimal[] getNomPointIds() {
		return nomPointIds;
	}

	public void setNomPointIds(BigDecimal[] nomPointIds) {
		this.nomPointIds = nomPointIds;
	}

	public String[] getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String[] statusCode) {
		this.statusCode = statusCode;
	}

	public String getReviewCode() {
		return reviewCode;
	}

	public void setReviewCode(String reviewCode) {
		this.reviewCode = reviewCode;
	}

	public BigDecimal getFactorFromDefaultUnit() {
		return factorFromDefaultUnit;
	}

	public void setFactorFromDefaultUnit(BigDecimal factorFromDefaultUnit) {
		this.factorFromDefaultUnit = factorFromDefaultUnit;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "AllocationReviewFilter [gasDay=" + gasDay + ", shipperId=" + shipperId + ", contractId=" + contractId
				+ ", zoneIds=" + Arrays.toString(zoneIds) + ", areaIds=" + Arrays.toString(areaIds) + ", nomPointIds="
				+ Arrays.toString(nomPointIds) + ", statusCode=" + Arrays.toString(statusCode) + ", reviewCode="
				+ reviewCode + ", factorFromDefaultUnit=" + factorFromDefaultUnit + ", systemId=" + systemId + "]";
	}

}
