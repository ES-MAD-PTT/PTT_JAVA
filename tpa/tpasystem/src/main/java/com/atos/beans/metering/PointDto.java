package com.atos.beans.metering;

import java.io.Serializable;

public class PointDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pointCode;
	private String pointDesc;

	public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public String getPointDesc() {
		return pointDesc;
	}

	public void setPointDesc(String pointDesc) {
		this.pointDesc = pointDesc;
	}
}
