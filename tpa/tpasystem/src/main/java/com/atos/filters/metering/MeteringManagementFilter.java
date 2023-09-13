package com.atos.filters.metering;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.atos.filters.booking.CRManagementFilter;

public class MeteringManagementFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 689265227361385489L;

	private Date gasDayFrom;
	private Date gasDayTo;
	private BigDecimal[] zoneIds;
	private BigDecimal[] areaIds;
	private BigDecimal[] systemPointId;
	private BigDecimal meteringInputId;

	private BigDecimal idn_user;
	private String type_code;

	public MeteringManagementFilter() {
		this.gasDayFrom = null;
		this.gasDayTo = null;
		this.zoneIds = null;
		this.areaIds = null;
		this.systemPointId = null;
		this.meteringInputId = null;
	}

	public MeteringManagementFilter(MeteringManagementFilter _filter) {
		this();
		
		if(_filter != null) {
			this.gasDayFrom = _filter.getGasDayFrom();
			this.gasDayTo = _filter.getGasDayTo();
			if(_filter.getZoneIds()!= null) {
				this.zoneIds = new BigDecimal[_filter.getZoneIds().length];
				System.arraycopy( _filter.getZoneIds(), 0, this.zoneIds, 0, _filter.getZoneIds().length );
			}
			if(_filter.getAreaIds()!= null) {
				this.areaIds = new BigDecimal[_filter.getAreaIds().length];
				System.arraycopy( _filter.getAreaIds(), 0, this.areaIds, 0, _filter.getAreaIds().length );
			}
			if(_filter.getSystemPointId()!= null) {
				this.systemPointId = new BigDecimal[_filter.getSystemPointId().length];
				System.arraycopy( _filter.getSystemPointId(), 0, this.systemPointId, 0, _filter.getSystemPointId().length );
			}
			this.meteringInputId = _filter.getMeteringInputId();
		}
		this.idn_user = _filter.getIdn_user();
		this.type_code = _filter.getType_code();
	}
	
	public Date getGasDayFrom() {
		return gasDayFrom;
	}

	public void setGasDayFrom(Date gasDayFrom) {
		this.gasDayFrom = gasDayFrom;
	}

	public Date getGasDayTo() {
		return gasDayTo;
	}

	public void setGasDayTo(Date gasDayTo) {
		this.gasDayTo = gasDayTo;
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

	public BigDecimal[] getSystemPointId() {
		return systemPointId;
	}

	public void setSystemPointId(BigDecimal[] systemPointId) {
		this.systemPointId = systemPointId;
	}

	public BigDecimal getMeteringInputId() {
		return meteringInputId;
	}

	public void setMeteringInputId(BigDecimal meteringInputId) {
		this.meteringInputId = meteringInputId;
	}

	public BigDecimal getIdn_user() {
		return idn_user;
	}

	public void setIdn_user(BigDecimal idn_user) {
		this.idn_user = idn_user;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeteringManagementFilter [gasDayFrom=");
		builder.append(gasDayFrom);
		builder.append(", gasDayTo=");
		builder.append(gasDayTo);
		builder.append(", zoneIds=");
		builder.append(Arrays.toString(zoneIds));
		builder.append(", areaIds=");
		builder.append(Arrays.toString(areaIds));
		builder.append(", systemPointId=");
		builder.append(Arrays.toString(systemPointId));
		builder.append(", meteringInputId=");
		builder.append(meteringInputId);
		builder.append(", idn_user=");
		builder.append(idn_user);
		builder.append(", type_code=");
		builder.append(type_code);
		builder.append("]");
		return builder.toString();
	}

}
