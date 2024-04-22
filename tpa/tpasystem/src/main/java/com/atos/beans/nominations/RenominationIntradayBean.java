package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.atos.beans.UserAudBean;

public class RenominationIntradayBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4341204774785510709L;
	
	private BigDecimal idn_intraday_renom_cab;
	private String intraday_renom_code;
	private Date gas_day;
	private BigDecimal hour;
	private BigDecimal idn_user_group;
	private String user_group_code;
	private String user_group_name;
	private String shortName;
	private String status;
	
	private List<RenominationIntradayDetBean> detail = null;
	
	public BigDecimal getIdn_intraday_renom_cab() {
		return idn_intraday_renom_cab;
	}
	public void setIdn_intraday_renom_cab(BigDecimal idn_intraday_renom_cab) {
		this.idn_intraday_renom_cab = idn_intraday_renom_cab;
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
	public BigDecimal getHour() {
		BigDecimal hour_temp = hour.subtract(BigDecimal.valueOf(1));
		return hour_temp;
	}
	public void setHour(BigDecimal hour) {
		this.hour = hour;
	}
	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}
	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}
	public String getUser_group_code() {
		return user_group_code;
	}
	public void setUser_group_code(String user_group_code) {
		this.user_group_code = user_group_code;
	}
	public String getUser_group_name() {
		return user_group_name;
	}
	public void setUser_group_name(String user_group_name) {
		this.user_group_name = user_group_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<RenominationIntradayDetBean> getDetail() {
		return detail;
	}
	public void setDetail(List<RenominationIntradayDetBean> detail) {
		this.detail = detail;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Override
	public String toString() {
		return "RenominationIntradayBean [idn_intraday_renom_cab=" + idn_intraday_renom_cab + ", intraday_renom_code="
				+ intraday_renom_code + ", gas_day=" + gas_day + ", hour=" + hour + ", idn_user_group=" + idn_user_group
				+ ", user_group_code=" + user_group_code + ", user_group_name=" + user_group_name + ", shortName="
				+ shortName + ", status=" + status + ", detail=" + detail + "]";
	}
	

	

}