package com.atos.filters.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class CRManagementFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2145652756282740523L;
	private String capacityRequestCode;
	private BigDecimal shipperId;
	// shipperCode solo se utiliza cuando en la CapacityRequestQuery, el nombre del shipper sea fijo.
	private String shipperCode;
	private BigDecimal[] contractTypeId;
	private String[] status;
	private Date startDate;
	private Date endDate;
	
	private BigDecimal idn_system;//offshore

	public CRManagementFilter() {
		this.capacityRequestCode = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.contractTypeId = null;
		this.status = null;
		this.startDate = null;
		this.endDate = null;
		this.idn_system=null;
	}
	
	public CRManagementFilter(CRManagementFilter _filter) {
		this();
		
		if(_filter != null) {
			this.capacityRequestCode = _filter.getCapacityRequestCode();
			this.shipperId = _filter.getShipperId();
			this.shipperCode = _filter.shipperCode;
			if(_filter.getContractTypeId()!= null) {
				this.contractTypeId = new BigDecimal[_filter.getContractTypeId().length];
				System.arraycopy( _filter.getContractTypeId(), 0, this.contractTypeId, 0, _filter.getContractTypeId().length );
			}
			if(_filter.getStatus()!= null) {
				this.status = new String[_filter.getStatus().length];
				System.arraycopy( _filter.getStatus(), 0, this.status, 0, _filter.getStatus().length );
			}
			this.startDate = _filter.getStartDate();
			this.endDate = _filter.getEndDate();
			
			//offshore
			this.idn_system=_filter.getIdn_system();
		}
	}
	
	public String getCapacityRequestCode() {
		return capacityRequestCode;
	}

	public void setCapacityRequestCode(String capacityRequestCode) {
		this.capacityRequestCode = capacityRequestCode;
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

	public BigDecimal[] getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(BigDecimal[] contractTypeId) {
		this.contractTypeId = contractTypeId;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
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

	
	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	@Override
	public String toString() {
		return "CRManagementFilter [capacityRequestCode=" + capacityRequestCode + ", shipperId=" + shipperId
				+ ", shipperCode=" + shipperCode + ", contractTypeId=" + Arrays.toString(contractTypeId) + ", status="
				+ Arrays.toString(status) + ", startDate=" + startDate + ", endDate=" + endDate + ", idn_system="
				+ idn_system + "]";
	}
}
