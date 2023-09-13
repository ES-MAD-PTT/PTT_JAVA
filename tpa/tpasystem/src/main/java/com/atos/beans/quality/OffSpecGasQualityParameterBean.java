package com.atos.beans.quality;

import java.io.Serializable;
import java.math.BigDecimal;

import com.atos.beans.UserAudBean;

public class OffSpecGasQualityParameterBean extends UserAudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8596905575275707742L;

	private BigDecimal incidentGasParamId;
	private BigDecimal incidentId;
	private BigDecimal paramId;
	private String paramDesc;
	private String unitDesc;
	private BigDecimal value;

	public OffSpecGasQualityParameterBean() {
		this.incidentGasParamId = null;
		this.incidentId = null;
		this.paramId = null;
		this.paramDesc = null;
		this.unitDesc = null;
		this.value = null;
	}

	public OffSpecGasQualityParameterBean(OffSpecGasQualityParameterBean _bean) {
		this.incidentGasParamId = _bean.getIncidentGasParamId();
		this.incidentId = _bean.getIncidentId();
		this.paramId = _bean.getParamId();
		this.paramDesc = _bean.getParamDesc();
		this.unitDesc = _bean.getUnitDesc();
		this.value = _bean.getValue();
	}
	
	public BigDecimal getIncidentGasParamId() {
		return incidentGasParamId;
	}

	public void setIncidentGasParamId(BigDecimal incidentGasParamId) {
		this.incidentGasParamId = incidentGasParamId;
	}

	public BigDecimal getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(BigDecimal incidentId) {
		this.incidentId = incidentId;
	}

	public BigDecimal getParamId() {
		return paramId;
	}

	public void setParamId(BigDecimal paramId) {
		this.paramId = paramId;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "OffSpecGasQualityParameterBean [incidentGasParamId=" + incidentGasParamId + ", incidentId=" + incidentId
				+ ", paramId=" + paramId + ", paramDesc=" + paramDesc + ", unitDesc=" + unitDesc + ", value=" + value
				+ "]";
	}

}
