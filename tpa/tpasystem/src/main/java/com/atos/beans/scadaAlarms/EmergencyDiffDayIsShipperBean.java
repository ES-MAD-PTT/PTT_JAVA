package com.atos.beans.scadaAlarms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class EmergencyDiffDayIsShipperBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -8210572551497660753L;

	private BigDecimal idn_tso_event;
	private boolean isShipper;
	private BigDecimal idn_user_group;
	
	
	
	public EmergencyDiffDayIsShipperBean() {
		super();
		this.idn_tso_event = null;
		this.isShipper = false;
		this.idn_user_group = null;
	}

	public EmergencyDiffDayIsShipperBean(BigDecimal idn_tso_event, boolean isShipper, BigDecimal idn_user_group) {
		super();
		this.idn_tso_event = idn_tso_event;
		this.isShipper = isShipper;
		this.idn_user_group = idn_user_group;
	}

	public BigDecimal getIdn_tso_event() {
		return idn_tso_event;
	}

	public void setIdn_tso_event(BigDecimal idn_tso_event) {
		this.idn_tso_event = idn_tso_event;
	}

	public boolean isShipper() {
		return isShipper;
	}

	public void setShipper(boolean isShipper) {
		this.isShipper = isShipper;
	}
	
	
	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}
	
	
		
}
