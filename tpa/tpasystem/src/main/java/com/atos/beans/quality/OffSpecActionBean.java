package com.atos.beans.quality;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class OffSpecActionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -440409295342451290L;

	private BigDecimal idnOffspecAction;
	private String actionCode;
	private String actionDesc;
	
	public OffSpecActionBean() {}
	
	public OffSpecActionBean(BigDecimal idnOffspecAction, String actionCode, String actionDesc) {
		this.idnOffspecAction = idnOffspecAction;
		this.actionCode = actionCode;
		this.actionDesc = actionDesc;
	}
	public BigDecimal getIdnOffspecAction() {
		return idnOffspecAction;
	}
	public void setIdnOffspecAction(BigDecimal idnOffspecAction) {
		this.idnOffspecAction = idnOffspecAction;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionDesc() {
		return actionDesc;
	}
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}
	
	@Override
	public String toString() {
		return "OffSpecActionBean [idnOffspecAction=" + idnOffspecAction + ", actionCode=" + actionCode
				+ ", actionDesc=" + actionDesc + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(actionCode, actionDesc, idnOffspecAction);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OffSpecActionBean other = (OffSpecActionBean) obj;
		return Objects.equals(actionCode, other.actionCode) && Objects.equals(actionDesc, other.actionDesc)
				&& Objects.equals(idnOffspecAction, other.idnOffspecAction);
	}
	
	
}
