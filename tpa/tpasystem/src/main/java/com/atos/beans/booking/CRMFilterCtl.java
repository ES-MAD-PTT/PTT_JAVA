package com.atos.beans.booking;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.atos.filters.booking.CRManagementFilter;

@ManagedBean(name = "filterCtl")
@SessionScoped
public class CRMFilterCtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ORIGING_CAP_REQUEST = 1;
	public static final int ORIGING_CAP_QUERY = 2;
	private String requestCode;
	private String contractType;
	private String shipperName;
	private boolean returning;
	private int origin;

	private CRManagementFilter filter;

	public boolean isReturning() {
		return returning;
	}

	public void setReturning(boolean returning) {
		this.returning = returning;
	}

	public CRManagementFilter getFilter() {
		return filter;
	}

	public void setFilter(CRManagementFilter filter) {
		this.filter = filter;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

}
