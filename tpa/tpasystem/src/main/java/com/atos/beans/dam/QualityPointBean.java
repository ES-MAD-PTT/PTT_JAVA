package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class QualityPointBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idnSystemPoint;
	private String pointCode; // private String id;
	private String pointDesc; // private String name;
	private String typeCode;
	private String typeDesc;
	private Date startDate;
	private Date endDate;
	private BigDecimal idnSystemPointGroup;
	private BigDecimal idnSystemPointType;
	private BigDecimal idnSystemPointParam;

	public QualityPointBean() {
		super();
	}

	public QualityPointBean(BigDecimal idnSystemPoint, String pointCode, String pointDesc, String typeCode,
			String typeDesc, Date startDate, Date endDate, BigDecimal idnSystemPointGroup,
			BigDecimal idnSystemPointType) {
		super();
		this.idnSystemPoint = idnSystemPoint;
		this.pointCode = pointCode;
		this.pointDesc = pointDesc;
		this.typeCode = typeCode;
		this.typeDesc = typeDesc;
		this.startDate = startDate;
		this.endDate = endDate;
		this.idnSystemPointGroup = idnSystemPointGroup;
		this.idnSystemPointType = idnSystemPointType;
	}

	public BigDecimal getIdnSystemPoint() {
		return idnSystemPoint;
	}

	public void setIdnSystemPoint(BigDecimal idnSystemPoint) {
		this.idnSystemPoint = idnSystemPoint;
	}

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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getIdnSystemPointGroup() {
		return idnSystemPointGroup;
	}

	public void setIdnSystemPointGroup(BigDecimal idnSystemPointGroup) {
		this.idnSystemPointGroup = idnSystemPointGroup;
	}

	public BigDecimal getIdnSystemPointType() {
		return idnSystemPointType;
	}

	public void setIdnSystemPointType(BigDecimal idnSystemPointType) {
		this.idnSystemPointType = idnSystemPointType;
	}

	public BigDecimal getIdnSystemPointParam() {
		return idnSystemPointParam;
	}

	public void setIdnSystemPointParam(BigDecimal idnSystemPointParam) {
		this.idnSystemPointParam = idnSystemPointParam;
	}

	@Override
	public String toString() {
		return "QualityPointBean [idnSystemPoint=" + idnSystemPoint + ", pointCode=" + pointCode + ", pointDesc="
				+ pointDesc + ", typeCode=" + typeCode + ", typeDesc=" + typeDesc + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", idnSystemPointGroup=" + idnSystemPointGroup + ", idnSystemPointType="
				+ idnSystemPointType + "]";
	}

}
