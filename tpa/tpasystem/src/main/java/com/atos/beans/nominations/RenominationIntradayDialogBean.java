package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.atos.beans.UserAudBean;

public class RenominationIntradayDialogBean extends UserAudBean implements Serializable{

	private static final long serialVersionUID = 8208055045914772127L;
	
	private BigDecimal shipperId;
	private String shipperCode;
	private boolean allShippers;
	private String strallShippers;
	private boolean isShipper;
	private BigDecimal idn_system;
	private BigDecimal hour;
	private BigDecimal idn_intraday_renom_cab;
	private String intraday_renom_code;
	private Date gas_day;
	private String username;
	

	private List<RenominationIntradayDialogDetBean> detail= new ArrayList<RenominationIntradayDialogDetBean>();

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

	public void setShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}

	public BigDecimal getIdn_system() {
		return idn_system;
	}

	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
	}

	public BigDecimal getHour() {
		return hour;
	}

	public void setHour(BigDecimal hour) {
		this.hour = hour;
	}

	public List<RenominationIntradayDialogDetBean> getDetail() {
		return detail;
	}

	public void setDetail(List<RenominationIntradayDialogDetBean> detail) {
		this.detail = detail;
	}

	public String getIntraday_renom_code() {
		return intraday_renom_code;
	}

	public void setIntraday_renom_code(String intraday_renom_code) {
		this.intraday_renom_code = intraday_renom_code;
	}

	public Date getGas_day() {
		return gas_day;
	}

	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getIdn_intraday_renom_cab() {
		return idn_intraday_renom_cab;
	}

	public void setIdn_intraday_renom_cab(BigDecimal idn_intraday_renom_cab) {
		this.idn_intraday_renom_cab = idn_intraday_renom_cab;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenominationIntradayDialogBean [shipperId=");
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
		builder.append(", hour=");
		builder.append(hour);
		builder.append(", idn_intraday_renom_cab=");
		builder.append(idn_intraday_renom_cab);
		builder.append(", intraday_renom_code=");
		builder.append(intraday_renom_code);
		builder.append(", gas_day=");
		builder.append(gas_day);
		builder.append(", username=");
		builder.append(username);
		builder.append(", detail=");
		builder.append(detail);
		builder.append("]");
		return builder.toString();
	}




	
}