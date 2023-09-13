package com.atos.beans.booking;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractCapacityPathPeriodBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -765638516278594494L;
	private BigDecimal idn_contract;
	private Date start_date;
	private Date end_date;
	private String is_contract_complete;
	private BigDecimal num_agreements;
	
	private String period_text;

	public BigDecimal getIdn_contract() {
		return idn_contract;
	}
	public void setIdn_contract(BigDecimal idn_contract) {
		this.idn_contract = idn_contract;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
		if(this.end_date!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.period_text = sdf.format(this.start_date) + " - " + sdf.format(this.end_date);
		}
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
		if(this.start_date!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.period_text = sdf.format(this.start_date) + " - " + sdf.format(this.end_date);
		}
	}
	public String getIs_contract_complete() {
		return is_contract_complete;
	}
	public void setIs_contract_complete(String is_contract_complete) {
		this.is_contract_complete = is_contract_complete;
	}
	public BigDecimal getNum_agreements() {
		return num_agreements;
	}
	public void setNum_agreements(BigDecimal num_agreements) {
		this.num_agreements = num_agreements;
	}

	public String getPeriod_text() {
		return period_text;
	}
	public void setPeriod_text(String period_text) {
		this.period_text = period_text;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathPeriodBean [idn_contract=");
		builder.append(idn_contract);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", is_contract_complete=");
		builder.append(is_contract_complete);
		builder.append(", num_agreements=");
		builder.append(num_agreements);
		builder.append(", period_text=");
		builder.append(period_text);
		builder.append("]");
		return builder.toString();
	}
	
	
}
