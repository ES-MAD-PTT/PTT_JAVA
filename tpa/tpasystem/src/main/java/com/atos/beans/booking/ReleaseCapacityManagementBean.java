package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.UserAudBean;

public class ReleaseCapacityManagementBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4350464526810658057L;

	private BigDecimal capacityRequestId;
	private String requestCode;
	private Date submittedTimestamp;
	private BigDecimal shipperIdFrom;
	private String shortName;
	private String shipperCodeFrom;
	private BigDecimal contractIdFrom;
	private String contractCodeFrom;
	// En esta lista se guardaran los detalles punto-agreements, con los datos exclusivamente que se van a liberar. 
	private List<ReleaseCapacitySubmissionBean> detailsFrom;
	private String statusCode;
	private String action;
	private BigDecimal shipperIdTo; 
	private String shipperCodeTo;
	private BigDecimal contractIdTo;
	private String contractCodeTo;
	// En esta lista se guardaran los detalles punto-agreements, con todos los datos del contrato destino. 
	// Y en validateComparingContractsFroTo() se marcan los registros que luego deban generar agreements para la nueva
	// adenda destino.
	private List<ReleaseCapacitySubmissionBean> detailsTo;
	
	//offshore
	private BigDecimal idn_system;
	private String toOperator;
	
	public ReleaseCapacityManagementBean() {
		this.capacityRequestId = null;
		this.requestCode = null;
		this.submittedTimestamp = null;
		this.shipperIdFrom = null; 
		this.shipperCodeFrom = null;
		this.shortName = null;
		this.contractIdFrom = null;
		this.contractCodeFrom = null;
		this.detailsFrom = null;
		this.statusCode = null;
		this.action = null;		
		this.shipperIdTo = null; 
		this.shipperCodeTo = null;
		this.contractIdTo = null;
		this.contractCodeTo = null;
		this.detailsTo = null;
		this.idn_system= null;
		this.toOperator=null;
	}

	public BigDecimal getCapacityRequestId() {
		return capacityRequestId;
	}

	public void setCapacityRequestId(BigDecimal capacityRequestId) {
		this.capacityRequestId = capacityRequestId;
	}

	public Date getSubmittedTimestamp() {
		return submittedTimestamp;
	}

	public void setSubmittedTimestamp(Date submittedTimestamp) {
		this.submittedTimestamp = submittedTimestamp;
	}

	public BigDecimal getShipperIdFrom() {
		return shipperIdFrom;
	}

	public void setShipperIdFrom(BigDecimal shipperIdFrom) {
		this.shipperIdFrom = shipperIdFrom;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShipperCodeFrom() {
		return shipperCodeFrom;
	}

	public void setShipperCodeFrom(String shipperCodeFrom) {
		this.shipperCodeFrom = shipperCodeFrom;
	}

	public BigDecimal getContractIdFrom() {
		return contractIdFrom;
	}

	public void setContractIdFrom(BigDecimal contractIdFrom) {
		this.contractIdFrom = contractIdFrom;
	}

	public String getContractCodeFrom() {
		return contractCodeFrom;
	}

	public void setContractCodeFrom(String contractCodeFrom) {
		this.contractCodeFrom = contractCodeFrom;
	}

	public List<ReleaseCapacitySubmissionBean> getDetailsFrom() {
		return detailsFrom;
	}

	public void setDetailsFrom(List<ReleaseCapacitySubmissionBean> details) {
		this.detailsFrom = details;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public BigDecimal getShipperIdTo() {
		return shipperIdTo;
	}

	public void setShipperIdTo(BigDecimal shipperIdTo) {
		this.shipperIdTo = shipperIdTo;
	}

	public String getShipperCodeTo() {
		return shipperCodeTo;
	}

	public void setShipperCodeTo(String shipperCodeTo) {
		this.shipperCodeTo = shipperCodeTo;
	}

	public BigDecimal getContractIdTo() {
		return contractIdTo;
	}

	public void setContractIdTo(BigDecimal contractIdTo) {
		this.contractIdTo = contractIdTo;
	}

	public String getContractCodeTo() {
		return contractCodeTo;
	}

	public void setContractCodeTo(String contractCodeTo) {
		this.contractCodeTo = contractCodeTo;
	}

	public List<ReleaseCapacitySubmissionBean> getDetailsTo() {
		return detailsTo;
	}

	public void setDetailsTo(List<ReleaseCapacitySubmissionBean> detailsTo) {
		this.detailsTo = detailsTo;
	}
	
	

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}
	
	

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getToOperator() {
		return toOperator;
	}

	public void setToOperator(String toOperator) {
		this.toOperator = toOperator;
	}

	@Override
	public String toString() {
		return "ReleaseCapacityManagementBean [capacityRequestId=" + capacityRequestId + ", requestCode=" + requestCode
				+ ", submittedTimestamp=" + submittedTimestamp + ", shipperIdFrom=" + shipperIdFrom + ", shortName="
				+ shortName + ", shipperCodeFrom=" + shipperCodeFrom + ", contractIdFrom=" + contractIdFrom
				+ ", contractCodeFrom=" + contractCodeFrom + ", detailsFrom=" + detailsFrom + ", statusCode="
				+ statusCode + ", action=" + action + ", shipperIdTo=" + shipperIdTo + ", shipperCodeTo="
				+ shipperCodeTo + ", contractIdTo=" + contractIdTo + ", contractCodeTo=" + contractCodeTo
				+ ", detailsTo=" + detailsTo + ", idn_system=" + idn_system + ", toOperator=" + toOperator + "]";
	}
		
}
