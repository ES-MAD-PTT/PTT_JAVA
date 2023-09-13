package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class ContractCapacityPathEntryExitBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6269859618480164112L;
	private BigDecimal idn_system_point;
	private String point_code;
	private BigDecimal idn_area;
	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}
	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
	}
	public String getPoint_code() {
		return point_code;
	}
	public void setPoint_code(String point_code) {
		this.point_code = point_code;
	}
	public BigDecimal getIdn_area() {
		return idn_area;
	}
	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathEntryExitBean [idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", point_code=");
		builder.append(point_code);
		builder.append(", idn_area=");
		builder.append(idn_area);
		builder.append("]");
		return builder.toString();
	}

	
}
