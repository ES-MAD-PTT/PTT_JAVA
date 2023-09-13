package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractCapacityPathBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1221705423275881800L;
	
	private String zone;
	private BigDecimal idn_zone;
	private String area;
	private BigDecimal idn_area;
	private String point_code;
	private String point_desc;
	private Date date_from;
	private Date date_to;
	private BigDecimal idn_system_point;
	private BigDecimal idn_contract;

	private ArrayList<ContractCapacityPathValuesBean> values;

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public String getPoint_code() {
		return point_code;
	}

	public void setPoint_code(String point_code) {
		this.point_code = point_code;
	}

	public String getPoint_desc() {
		return point_desc;
	}

	public void setPoint_desc(String point_desc) {
		this.point_desc = point_desc;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}

	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
	}

	public BigDecimal getIdn_contract() {
		return idn_contract;
	}

	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}

	public ArrayList<ContractCapacityPathValuesBean> getValues() {
		return values;
	}

	public void setValues(ArrayList<ContractCapacityPathValuesBean> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathBean [zone=");
		builder.append(zone);
		builder.append(", idn_zone=");
		builder.append(idn_zone);
		builder.append(", area=");
		builder.append(area);
		builder.append(", idn_area=");
		builder.append(idn_area);
		builder.append(", point_code=");
		builder.append(point_code);
		builder.append(", point_desc=");
		builder.append(point_desc);
		builder.append(", date_from=");
		builder.append(date_from);
		builder.append(", date_to=");
		builder.append(date_to);
		builder.append(", idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", idn_contract=");
		builder.append(idn_contract);
		builder.append(", values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}

	
	
}
