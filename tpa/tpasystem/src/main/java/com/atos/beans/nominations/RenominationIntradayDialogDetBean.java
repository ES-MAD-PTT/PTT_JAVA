package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class RenominationIntradayDialogDetBean extends UserAudBean implements Serializable{
	
	private static final long serialVersionUID = 8675517048526692736L;
	
	private Date gas_day;
	private BigDecimal minutes;
	private BigDecimal idn_system_point;
	private String point_code;
	private BigDecimal energy;
	private BigDecimal volume;
	private String updated = "N";
	
	private BigDecimal idn_intraday_renom_det;
	private BigDecimal idn_intraday_renom_cab;
	private String username;
	
	public Date getGas_day() {
		return gas_day;
	}
	public void setGas_day(Date gas_day) {
		this.gas_day = gas_day;
	}
	public BigDecimal getMinutes() {
		return minutes;
	}
	public void setMinutes(BigDecimal minutes) {
		this.minutes = minutes;
	}
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
	public BigDecimal getEnergy() {
		return energy;
	}
	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public BigDecimal getIdn_intraday_renom_det() {
		return idn_intraday_renom_det;
	}
	public void setIdn_intraday_renom_det(BigDecimal idn_intraday_renom_det) {
		this.idn_intraday_renom_det = idn_intraday_renom_det;
	}
	public BigDecimal getIdn_intraday_renom_cab() {
		return idn_intraday_renom_cab;
	}
	public void setIdn_intraday_renom_cab(BigDecimal idn_intraday_renom_cab) {
		this.idn_intraday_renom_cab = idn_intraday_renom_cab;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenominationIntradayDialogDetBean [gas_day=");
		builder.append(gas_day);
		builder.append(", minutes=");
		builder.append(minutes);
		builder.append(", idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", point_code=");
		builder.append(point_code);
		builder.append(", energy=");
		builder.append(energy);
		builder.append(", volume=");
		builder.append(volume);
		builder.append(", updated=");
		builder.append(updated);
		builder.append(", idn_intraday_renom_det=");
		builder.append(idn_intraday_renom_det);
		builder.append(", idn_intraday_renom_cab=");
		builder.append(idn_intraday_renom_cab);
		builder.append(", username=");
		builder.append(username);
		builder.append("]");
		return builder.toString();
	}




	
}