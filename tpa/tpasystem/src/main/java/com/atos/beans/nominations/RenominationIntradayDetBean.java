package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class RenominationIntradayDetBean extends UserAudBean implements Serializable{

	private static final long serialVersionUID = -2063272265824872176L;
	
	private BigDecimal idn_intraday_renom_det;
	private BigDecimal idn_intraday_renom_cab; 
	private BigDecimal idn_system_point;
	private String point_code;
	private BigDecimal minutes;
	private BigDecimal energy;
	private BigDecimal volume;
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
	public BigDecimal getMinutes() {
		return minutes;
	}
	public void setMinutes(BigDecimal minutes) {
		this.minutes = minutes;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenominationIntradayDetBean [idn_intraday_renom_det=");
		builder.append(idn_intraday_renom_det);
		builder.append(", idn_intraday_renom_cab=");
		builder.append(idn_intraday_renom_cab);
		builder.append(", idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", point_code=");
		builder.append(point_code);
		builder.append(", minutes=");
		builder.append(minutes);
		builder.append(", energy=");
		builder.append(energy);
		builder.append(", volume=");
		builder.append(volume);
		builder.append("]");
		return builder.toString();
	}
	

	
}