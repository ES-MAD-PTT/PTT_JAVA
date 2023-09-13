package com.atos.filters.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RenominationIntradayFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6164414217990922096L;
	// Filters
	private BigDecimal shipperId;
	private String shipperCode;
	private boolean allShippers;
	private String strallShippers;
	private boolean isShipper;
	private BigDecimal idn_system;
	private String status;
	
	
	
	public RenominationIntradayFilter() {
		super();
		this.shipperId = null;
		this.shipperCode = null;
		this.allShippers = false;
		this.strallShippers = null;
		this.isShipper = false;
		this.idn_system = null;
		this.status = null;
		// TODO Auto-generated constructor stub
	}

	public RenominationIntradayFilter(BigDecimal shipperId, String shipperCode, boolean allShippers,
			String strallShippers, boolean isShipper, BigDecimal idn_system, String status) {
		super();
		this.shipperId = shipperId;
		this.shipperCode = shipperCode;
		this.allShippers = allShippers;
		this.strallShippers = strallShippers;
		this.isShipper = isShipper;
		this.idn_system = idn_system;
		this.status = status;
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
	
	public boolean isAllShippers() {
		return allShippers;
	}
	
	public void setAllShippers(boolean allShippers) {
		this.allShippers = allShippers;
	}
	
	public String getStrallShippers() {
		return strallShippers;
	}
	
	public void setStrallShippers(String strallShippers) {
		this.strallShippers = strallShippers;
	}

	public boolean isShipper() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allShippers ? 1231 : 1237);
		result = prime * result + ((idn_system == null) ? 0 : idn_system.hashCode());
		result = prime * result + (isShipper ? 1231 : 1237);
		result = prime * result + ((shipperCode == null) ? 0 : shipperCode.hashCode());
		result = prime * result + ((shipperId == null) ? 0 : shipperId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((strallShippers == null) ? 0 : strallShippers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenominationIntradayFilter other = (RenominationIntradayFilter) obj;
		if (allShippers != other.allShippers)
			return false;
		if (idn_system == null) {
			if (other.idn_system != null)
				return false;
		} else if (!idn_system.equals(other.idn_system))
			return false;
		if (isShipper != other.isShipper)
			return false;
		if (shipperCode == null) {
			if (other.shipperCode != null)
				return false;
		} else if (!shipperCode.equals(other.shipperCode))
			return false;
		if (shipperId == null) {
			if (other.shipperId != null)
				return false;
		} else if (!shipperId.equals(other.shipperId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (strallShippers == null) {
			if (other.strallShippers != null)
				return false;
		} else if (!strallShippers.equals(other.strallShippers))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenominationIntradayFilter [shipperId=");
		builder.append(shipperId);
		builder.append(", shipperCode=");
		builder.append(shipperCode);
		builder.append(", allShippers=");
		builder.append(allShippers);
		builder.append(", strallShippers=");
		builder.append(strallShippers);
		builder.append(", isShipper=");
		builder.append(isShipper);
		builder.append(", idn_system=");
		builder.append(idn_system);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	} 	
	
}
	