package com.atos.beans.allocation;

import java.io.Serializable;

public class AllocationIntradayDetailBean extends AllocationIntradayBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nominationPoint;

	public String getNominationPoint() {
		return nominationPoint;
	}

	public void setNominationPoint(String nominationPoint) {
		this.nominationPoint = nominationPoint;
	}

}
