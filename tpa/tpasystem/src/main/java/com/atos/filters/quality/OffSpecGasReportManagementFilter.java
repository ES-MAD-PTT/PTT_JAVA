package com.atos.filters.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OffSpecGasReportManagementFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3766977971589181166L;

	private BigDecimal incidentTypeId;
	private String incidentCode;
	private BigDecimal qualityPointId;
	private BigDecimal shipperId;
	// shipperCode solo se utiliza cuando en la CapacityRequestQuery, el nombre del shipper sea fijo.
	private String shipperCode;
	private BigDecimal[] statusId;
	private Date startDate;
	private Date endDate;
	// Estado de la respuesta. Valores para un select multiple:
	// 1 - NoRespondido
	// 2 - RespondidoOK
	// 3 - RespondidoKO
	public static final int resStatusNoResponsedId = 1;
	private List<String> resStatusId;
	private BigDecimal systemId;				// Obligatorio. Siempre debe estar relleno al consultar en la BD.
	
	public OffSpecGasReportManagementFilter() {
		this.incidentTypeId = null;
		this.incidentCode = null;
		this.qualityPointId = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.statusId = null;
		this.startDate = null;
		this.endDate = null;
		this.resStatusId = null;
		this.systemId = null;
	}

	public OffSpecGasReportManagementFilter(OffSpecGasReportManagementFilter _filter) {
		this();
		
		if(_filter != null) {
			this.incidentTypeId = _filter.getIncidentTypeId();
			this.incidentCode = _filter.getIncidentCode();
			this.qualityPointId = _filter.getQualityPointId();
			this.shipperId = _filter.getShipperId();
			this.shipperCode = _filter.getShipperCode();
			if(_filter.getStatusId()!= null) {
				this.statusId = new BigDecimal[_filter.getStatusId().length];
				System.arraycopy( _filter.getStatusId(), 0, this.statusId, 0, _filter.getStatusId().length );
			}
			this.startDate = _filter.getStartDate();
			this.endDate = _filter.getEndDate();
			this.resStatusId = _filter.getResStatusId();
			this.systemId = _filter.getSystemId();
		}
	}
	
	public BigDecimal getIncidentTypeId() {
		return incidentTypeId;
	}

	public void setIncidentTypeId(BigDecimal incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}

	public String getIncidentCode() {
		return incidentCode;
	}

	public void setIncidentCode(String incidentCode) {
		this.incidentCode = incidentCode;
	}

	public BigDecimal getQualityPointId() {
		return qualityPointId;
	}

	public void setQualityPointId(BigDecimal qualityPointId) {
		this.qualityPointId = qualityPointId;
	}

	public BigDecimal getShipperId() {
		return shipperId;
	}

	public void setShipperId(BigDecimal shipperId) {
		this.shipperId = shipperId;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public BigDecimal[] getStatusId() {
		return statusId;
	}

	public void setStatusId(BigDecimal[] statusId) {
		this.statusId = statusId;
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

	public List<String> getResStatusId() {
		return resStatusId;
	}

	public void setResStatusId(List<String> resStatusId) {
		this.resStatusId = resStatusId;
	}

	public BigDecimal getSystemId() {
		return systemId;
	}

	public void setSystemId(BigDecimal systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "OffSpecGasReportManagementFilter [incidentTypeId=" + incidentTypeId + ", incidentCode=" + incidentCode
				+ ", qualityPointId=" + qualityPointId + ", shipperId=" + shipperId + ", shipperCode=" + shipperCode
				+ ", statusId=" + Arrays.toString(statusId) + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", resStatusId=" + resStatusId + ", systemId=" + systemId + "]";
	}
}
