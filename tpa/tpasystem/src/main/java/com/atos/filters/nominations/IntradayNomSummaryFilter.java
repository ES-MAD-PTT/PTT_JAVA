package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntradayNomSummaryFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6164414217990922096L;
	// Filters
	private Date gasDayTo;
	private Date gasDayFrom;
	private BigDecimal shipperId;
	private String shipperCode;
	private BigDecimal contractCode;
	private BigDecimal contractId;
	private BigDecimal systemPoint;
	private boolean checkIntraday;
	private String strCheckIntraday;
	private boolean isShipper;
	private BigDecimal idn_system;
	
	public IntradayNomSummaryFilter() {
		super();
		this.gasDayTo = null;
		this.gasDayFrom = null;
		this.shipperId = null;
		this.shipperCode = null;
		this.contractCode = null;
		this.contractId = null;
		this.systemPoint = null;
		this.checkIntraday = false;
		this.isShipper = false;
		this.idn_system = null;
		this.strCheckIntraday = null;
	}
	
	public IntradayNomSummaryFilter(Date gasDayTo, Date gasDayFrom, BigDecimal shipperId, String shipperCode, BigDecimal contractCode,
			BigDecimal contractId, BigDecimal systemPoint, boolean checkIntraday, boolean isShipper, BigDecimal idn_system, String strCheckIntraday) {
		super();
		this.gasDayTo = gasDayTo;
		this.gasDayFrom = gasDayFrom;
		this.shipperId = shipperId;
		this.shipperCode = shipperCode;
		this.contractCode = contractCode;
		this.contractId = contractId;
		this.systemPoint = systemPoint;
		this.checkIntraday = checkIntraday;
		this.isShipper = isShipper;
		this.idn_system = idn_system;
		this.strCheckIntraday = strCheckIntraday;
	}

	public Date getGasDayTo() {
		return gasDayTo;
	}

	public void setGasDayTo(Date gasDayTo) {
		this.gasDayTo = gasDayTo;
	}

	public Date getGasDayFrom() {
		return gasDayFrom;
	}

	public void setGasDayFrom(Date gasDayFrom) {
		this.gasDayFrom = gasDayFrom;
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

	public BigDecimal getContractCode() {
		return contractCode;
	}

	public void setContractCode(BigDecimal contractCode) {
		this.contractCode = contractCode;
	}

	public BigDecimal getSystemPoint() {
		return systemPoint;
	}

	public void setSystemPoint(BigDecimal systemPoint) {
		this.systemPoint = systemPoint;
	}

	public boolean isCheckIntraday() {
		return checkIntraday;
	}

	public void setCheckIntraday(boolean checkIntraday) {
		this.checkIntraday = checkIntraday;
	}

	public boolean getIsShipper() {
		return isShipper;
	}

	public void setIsShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public BigDecimal getContractId() {
		return contractId;
	}

	public void setContractId(BigDecimal contractId) {
		this.contractId = contractId;
	}

	public String getStrCheckIntraday() {
		return strCheckIntraday;
	}

	public void setStrCheckIntraday(String strCheckIntraday) {
		this.strCheckIntraday = strCheckIntraday;
	}
	
}
