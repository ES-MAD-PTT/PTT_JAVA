package com.atos.beans.booking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class ContractCapacityPathEditionDatesBean extends UserAudBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1312962312610197517L;
	private Date start_date;
	private Date end_date;

	private ArrayList<ContractCapacityPathDetailBean> list = new ArrayList<ContractCapacityPathDetailBean>();

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public ArrayList<ContractCapacityPathDetailBean> getList() {
		return list;
	}

	public void setList(ArrayList<ContractCapacityPathDetailBean> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractCapacityPathEditionDatesBean [start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}
	
	
}
