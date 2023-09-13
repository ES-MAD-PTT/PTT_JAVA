package com.atos.beans.nominations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QualityPublicationShipperBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160599673733279558L;
	private BigDecimal areaId;
	private String areaCode;
	private Date nominationDate;
	private String parameterCode;
	private BigDecimal value;
	private String unitDesc;
	private String isWarned;
	private BigDecimal idShipper;	
	private String nameShipper;	
	
	public QualityPublicationShipperBean() {
		super();
		this.areaId = null;
		this.areaCode = null;
		this.nominationDate = null;
		this.parameterCode = null;
		this.value = null;
		this.unitDesc = null;
		this.isWarned = null;
		this.idShipper = null;
		this.nameShipper = null;
	}

	public String getNameShipper() {
		return nameShipper;
	}

	public void setNameShipper(String nameShipper) {
		this.nameShipper = nameShipper;
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Date getNominationDate() {
		return nominationDate;
	}

	public void setNominationDate(Date nominationDate) {
		this.nominationDate = nominationDate;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	
	public String getIsWarned() {
		return isWarned;
	}

	public void setIsWarned(String isWarned) {
		this.isWarned = isWarned;
	}

	public BigDecimal getIdShipper() {
		return idShipper;
	}

	public void setIdShipper(BigDecimal idShipper) {
		this.idShipper = idShipper;
	}

	@Override
	public String toString() {
		return "QualityPublicationShipperBean [areaId=" + areaId + ", areaCode=" + areaCode + ", nominationDate="
				+ nominationDate + ", parameterCode=" + parameterCode + ", value=" + value + ", unitDesc=" + unitDesc
				+ ", isWarned=" + isWarned + ", idShipper=" + idShipper + ", nameShipper=" + nameShipper + "]";
	}
	
}
