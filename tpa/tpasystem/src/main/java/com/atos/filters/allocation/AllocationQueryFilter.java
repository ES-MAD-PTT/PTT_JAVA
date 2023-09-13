package com.atos.filters.allocation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class AllocationQueryFilter implements Serializable {

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
	private Date generatedFrom;
	private Date generatedTo;
	private boolean lastVersion;
	private String strLastVersion; 	// "Y"/"N" Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
	private BigDecimal factorFromDefaultUnit;	// Para que en la consulta se transforme la unidad de energia.
	private BigDecimal systemId;
	
	public AllocationQueryFilter() {
		this.gasDay = null;
		this.shipperId = null;
		this.contractId = null;
		this.zoneIds = null;
		this.areaIds = null;
		this.nomPointIds = null;
		this.generatedFrom = null;
		this.generatedTo = null;		
		this.lastVersion = true;
		this.strLastVersion = null;
		this.factorFromDefaultUnit = null;
		this.systemId = null;
	}

	public AllocationQueryFilter(AllocationQueryFilter _filter) {
		this();
		
		if(_filter != null) {
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
			this.generatedFrom = _filter.getGeneratedFrom();
			this.generatedTo = _filter.getGeneratedTo();		
			this.lastVersion = _filter.isLastVersion();
			this.strLastVersion = _filter.getStrLastVersion();
			this.factorFromDefaultUnit = _filter.getFactorFromDefaultUnit();
			this.systemId = _filter.getSystemId();
		}
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

	public Date getGeneratedFrom() {
		return generatedFrom;
	}

	public void setGeneratedFrom(Date generatedFrom) {
		this.generatedFrom = generatedFrom;
	}

	public Date getGeneratedTo() {
		return generatedTo;
	}

	public void setGeneratedTo(Date generatedTo) {
		this.generatedTo = generatedTo;
	}

	public boolean isLastVersion() {
		return lastVersion;
	}

	// Si se marca "la ultima version", se limpia el filtro de fechas.
	public void setLastVersion(boolean lastVersion) {
		this.lastVersion = lastVersion;
		if(lastVersion){
			this.generatedFrom = null;
			this.generatedTo = null;				
		}
	}

	public String getStrLastVersion() {
		return (lastVersion? "Y" : "N");
	}

	// Esta variable corresponde al valor del booleano. Para filtrar en base de datos.
//	public void setStrLastVersion(String strLastVersion) {
//		this.strLastVersion = strLastVersion;
//	}

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
		return "AllocationQueryFilter [gasDay=" + gasDay + ", shipperId=" + shipperId + ", contractId=" + contractId
				+ ", zoneIds=" + Arrays.toString(zoneIds) + ", areaIds=" + Arrays.toString(areaIds) + ", nomPointIds="
				+ Arrays.toString(nomPointIds) + ", generatedFrom=" + generatedFrom + ", generatedTo=" + generatedTo
				+ ", lastVersion=" + lastVersion + ", strLastVersion=" + strLastVersion + ", factorFromDefaultUnit="
				+ factorFromDefaultUnit + ", systemId=" + systemId + "]";
	}

}
