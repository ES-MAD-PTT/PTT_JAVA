package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.atos.beans.UserAudBean;

public class MeteredPointBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_system_point;
	private BigDecimal idn_system_point_nomination;
	private BigDecimal idn_area;
	private BigDecimal idn_subarea;
	private BigDecimal idn_system_point_group;
	private BigDecimal idn_system_point_type;
	private BigDecimal idn_customer_type;
	private BigDecimal idn_system_point_param;
	private BigDecimal idn_system_point_contract;
	private BigDecimal idn_system_point_quality;

	private BigDecimal idn_zone;
	private BigDecimal idn_pipeline_system;

	private String meteringID;
	private String id;
	private String point_code;
	private String newId;
	private String name;
	private String pointType;
	private BigDecimal nominalCapacity;
	private BigDecimal minPressure;
	private BigDecimal maxPressure;
	private String customerType;
	private String nominationPoint;
	private String contractPoint;
	private String qualityPoint;
	private BigDecimal latitude;
	private BigDecimal longitud;

	private String area;
	private String subarea;
	private String conectingPartyName;
	private String conectingPartyPhoneNumber;
	private String conectingPartyEmail;
	private Date startDate;
	private Date endDate;
	private String versionDate;
	
	// CAMPOS DE OTRA TABLA
	private String systemCode;
	private String zone;

	private String user;
	private String lang;
	private Integer errorCode;
	private String errorDesc;
	private String errorDetail;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	// fin

	public MeteredPointBean() {
		super();
	}

	public MeteredPointBean(BigDecimal idn_system_point, BigDecimal idn_system_point_nomination, BigDecimal idn_area,
			BigDecimal idn_subarea, BigDecimal idn_system_point_group, BigDecimal idn_system_point_type,
			BigDecimal idn_customer_type, BigDecimal idn_system_point_param, BigDecimal idn_system_point_contract,
			BigDecimal idn_system_point_quality, BigDecimal idn_zone, BigDecimal idn_pipeline_system, String meteringID,
			String id,String point_code,String newId ,String name, String pointType, BigDecimal nominalCapacity, BigDecimal minPressure,
			BigDecimal maxPressure, String customerType, String nominationPoint, String contractPoint,
			String qualityPoint, BigDecimal latitude, BigDecimal longitud, String area, String subarea,
			String conectingPartyName, String conectingPartyPhoneNumber, String conectingPartyEmail, Date startDate,
			Date endDate, String systemCode, String zone, String versionDate) {
		super();
		this.idn_system_point = idn_system_point;
		this.idn_system_point_nomination = idn_system_point_nomination;
		this.idn_area = idn_area;
		this.idn_subarea = idn_subarea;
		this.idn_system_point_group = idn_system_point_group;
		this.idn_system_point_type = idn_system_point_type;
		this.idn_customer_type = idn_customer_type;
		this.idn_system_point_param = idn_system_point_param;
		this.idn_system_point_contract = idn_system_point_contract;
		this.idn_system_point_quality = idn_system_point_quality;
		this.idn_zone = idn_zone;
		this.idn_pipeline_system = idn_pipeline_system;
		this.meteringID = meteringID;
		this.point_code = point_code;
		this.id = id;
		this.newId = newId;
		this.name = name;
		this.pointType = pointType;
		this.nominalCapacity = nominalCapacity;
		this.minPressure = minPressure;
		this.maxPressure = maxPressure;
		this.customerType = customerType;
		this.nominationPoint = nominationPoint;
		this.contractPoint = contractPoint;
		this.qualityPoint = qualityPoint;
		this.latitude = latitude;
		this.longitud = longitud;
		this.area = area;
		this.subarea = subarea;
		this.conectingPartyName = conectingPartyName;
		this.conectingPartyPhoneNumber = conectingPartyPhoneNumber;
		this.conectingPartyEmail = conectingPartyEmail;
		this.startDate = startDate;
		this.endDate = endDate;
		this.systemCode = systemCode;
		this.zone = zone;
		this.versionDate = versionDate;
	}

	public String getContractPoint() {
		return contractPoint;
	}

	public void setContractPoint(String contractPoint) {
		this.contractPoint = contractPoint;
	}

	public BigDecimal getIdn_system_point_param() {
		return idn_system_point_param;
	}

	public void setIdn_system_point_param(BigDecimal idn_system_point_param) {
		this.idn_system_point_param = idn_system_point_param;
	}

	public BigDecimal getIdn_subarea() {
		return idn_subarea;
	}

	public void setIdn_subarea(BigDecimal idn_subarea) {
		this.idn_subarea = idn_subarea;
	}

	public BigDecimal getIdn_system_point_nomination() {
		return idn_system_point_nomination;
	}

	public void setIdn_system_point_nomination(BigDecimal idn_system_point_nomination) {
		this.idn_system_point_nomination = idn_system_point_nomination;
	}

	public BigDecimal getIdn_customer_type() {
		return idn_customer_type;
	}

	public void setIdn_customer_type(BigDecimal idn_customer_type) {
		this.idn_customer_type = idn_customer_type;
	}

	public String getMeteringID() {
		return meteringID;
	}

	public void setMeteringID(String meteringID) {
		this.meteringID = meteringID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoint_code() {
		return point_code;
	}

	public void setPoint_code(String point_code) {
		this.point_code = point_code;
	}

	public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public BigDecimal getNominalCapacity() {
		return nominalCapacity;
	}

	public void setNominalCapacity(BigDecimal nominalCapacity) {
		this.nominalCapacity = nominalCapacity;
	}

	public BigDecimal getMinPressure() {
		return minPressure;
	}

	public void setMinPressure(BigDecimal minPressure) {
		this.minPressure = minPressure;
	}

	public BigDecimal getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(BigDecimal maxPressure) {
		this.maxPressure = maxPressure;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getNominationPoint() {
		return nominationPoint;
	}

	public void setNominationPoint(String nominationPoint) {
		this.nominationPoint = nominationPoint;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSubarea() {
		return subarea;
	}

	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}

	public String getConectingPartyName() {
		return conectingPartyName;
	}

	public void setConectingPartyName(String conectingPartyName) {
		this.conectingPartyName = conectingPartyName;
	}

	public String getConectingPartyPhoneNumber() {
		return conectingPartyPhoneNumber;
	}

	public void setConectingPartyPhoneNumber(String conectingPartyPhoneNumber) {
		this.conectingPartyPhoneNumber = conectingPartyPhoneNumber;
	}

	public String getConectingPartyEmail() {
		return conectingPartyEmail;
	}

	public void setConectingPartyEmail(String conectingPartyEmail) {
		this.conectingPartyEmail = conectingPartyEmail;
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

	public BigDecimal getIdn_system_point() {
		return idn_system_point;
	}

	public void setIdn_system_point(BigDecimal idn_system_point) {
		this.idn_system_point = idn_system_point;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public BigDecimal getIdn_system_point_group() {
		return idn_system_point_group;
	}

	public void setIdn_system_point_group(BigDecimal idn_system_point_group) {
		this.idn_system_point_group = idn_system_point_group;
	}

	public BigDecimal getIdn_system_point_type() {
		return idn_system_point_type;
	}

	public void setIdn_system_point_type(BigDecimal idn_system_point_type) {
		this.idn_system_point_type = idn_system_point_type;
	}

	public BigDecimal getIdn_zone() {
		return idn_zone;
	}

	public void setIdn_zone(BigDecimal idn_zone) {
		this.idn_zone = idn_zone;
	}

	public BigDecimal getIdn_pipeline_system() {
		return idn_pipeline_system;
	}

	public void setIdn_pipeline_system(BigDecimal idn_pipeline_system) {
		this.idn_pipeline_system = idn_pipeline_system;
	}

	public BigDecimal getIdn_system_point_contract() {
		return idn_system_point_contract;
	}

	public void setIdn_system_point_contract(BigDecimal idn_system_point_contract) {
		this.idn_system_point_contract = idn_system_point_contract;
	}

	public BigDecimal getIdn_system_point_quality() {
		return idn_system_point_quality;
	}

	public void setIdn_system_point_quality(BigDecimal idn_system_point_quality) {
		this.idn_system_point_quality = idn_system_point_quality;
	}

	public String getQualityPoint() {
		return qualityPoint;
	}

	public void setQualityPoint(String qualityPoint) {
		this.qualityPoint = qualityPoint;
	}

	
	public String getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeteredPointBean [idn_system_point=");
		builder.append(idn_system_point);
		builder.append(", idn_system_point_nomination=");
		builder.append(idn_system_point_nomination);
		builder.append(", idn_area=");
		builder.append(idn_area);
		builder.append(", idn_subarea=");
		builder.append(idn_subarea);
		builder.append(", idn_system_point_group=");
		builder.append(idn_system_point_group);
		builder.append(", idn_system_point_type=");
		builder.append(idn_system_point_type);
		builder.append(", idn_customer_type=");
		builder.append(idn_customer_type);
		builder.append(", idn_system_point_param=");
		builder.append(idn_system_point_param);
		builder.append(", idn_system_point_contract=");
		builder.append(idn_system_point_contract);
		builder.append(", idn_system_point_quality=");
		builder.append(idn_system_point_quality);
		builder.append(", idn_zone=");
		builder.append(idn_zone);
		builder.append(", idn_pipeline_system=");
		builder.append(idn_pipeline_system);
		builder.append(", meteringID=");
		builder.append(meteringID);
		builder.append(", id=");
		builder.append(id);
		builder.append(", point_code=");
		builder.append(point_code);
		builder.append(", newId=");
		builder.append(newId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", pointType=");
		builder.append(pointType);
		builder.append(", nominalCapacity=");
		builder.append(nominalCapacity);
		builder.append(", minPressure=");
		builder.append(minPressure);
		builder.append(", maxPressure=");
		builder.append(maxPressure);
		builder.append(", customerType=");
		builder.append(customerType);
		builder.append(", nominationPoint=");
		builder.append(nominationPoint);
		builder.append(", contractPoint=");
		builder.append(contractPoint);
		builder.append(", qualityPoint=");
		builder.append(qualityPoint);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitud=");
		builder.append(longitud);
		builder.append(", area=");
		builder.append(area);
		builder.append(", subarea=");
		builder.append(subarea);
		builder.append(", conectingPartyName=");
		builder.append(conectingPartyName);
		builder.append(", conectingPartyPhoneNumber=");
		builder.append(conectingPartyPhoneNumber);
		builder.append(", conectingPartyEmail=");
		builder.append(conectingPartyEmail);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", versionDate=");
		builder.append(versionDate);
		builder.append(", systemCode=");
		builder.append(systemCode);
		builder.append(", zone=");
		builder.append(zone);
		builder.append(", user=");
		builder.append(user);
		builder.append(", lang=");
		builder.append(lang);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", errorDesc=");
		builder.append(errorDesc);
		builder.append(", errorDetail=");
		builder.append(errorDetail);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 33;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((conectingPartyEmail == null) ? 0 : conectingPartyEmail.hashCode());
		result = prime * result + ((conectingPartyName == null) ? 0 : conectingPartyName.hashCode());
		result = prime * result + ((conectingPartyPhoneNumber == null) ? 0 : conectingPartyPhoneNumber.hashCode());
		result = prime * result + ((contractPoint == null) ? 0 : contractPoint.hashCode());
		result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((point_code == null) ? 0 : point_code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((newId == null) ? 0 : newId.hashCode());
		result = prime * result + ((idn_area == null) ? 0 : idn_area.hashCode());
		result = prime * result + ((idn_customer_type == null) ? 0 : idn_customer_type.hashCode());
		result = prime * result + ((idn_pipeline_system == null) ? 0 : idn_pipeline_system.hashCode());
		result = prime * result + ((idn_subarea == null) ? 0 : idn_subarea.hashCode());
		result = prime * result + ((idn_system_point == null) ? 0 : idn_system_point.hashCode());
		result = prime * result + ((idn_system_point_contract == null) ? 0 : idn_system_point_contract.hashCode());
		result = prime * result + ((idn_system_point_group == null) ? 0 : idn_system_point_group.hashCode());
		result = prime * result + ((idn_system_point_nomination == null) ? 0 : idn_system_point_nomination.hashCode());
		result = prime * result + ((idn_system_point_param == null) ? 0 : idn_system_point_param.hashCode());
		result = prime * result + ((idn_system_point_quality == null) ? 0 : idn_system_point_quality.hashCode());
		result = prime * result + ((idn_system_point_type == null) ? 0 : idn_system_point_type.hashCode());
		result = prime * result + ((idn_zone == null) ? 0 : idn_zone.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitud == null) ? 0 : longitud.hashCode());
		result = prime * result + ((maxPressure == null) ? 0 : maxPressure.hashCode());
		result = prime * result + ((meteringID == null) ? 0 : meteringID.hashCode());
		result = prime * result + ((minPressure == null) ? 0 : minPressure.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nominalCapacity == null) ? 0 : nominalCapacity.hashCode());
		result = prime * result + ((nominationPoint == null) ? 0 : nominationPoint.hashCode());
		result = prime * result + ((pointType == null) ? 0 : pointType.hashCode());
		result = prime * result + ((qualityPoint == null) ? 0 : qualityPoint.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((subarea == null) ? 0 : subarea.hashCode());
		result = prime * result + ((systemCode == null) ? 0 : systemCode.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
		result = prime * result + ((versionDate == null) ? 0 : versionDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeteredPointBean other = (MeteredPointBean) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (conectingPartyEmail == null) {
			if (other.conectingPartyEmail != null)
				return false;
		} else if (!conectingPartyEmail.equals(other.conectingPartyEmail))
			return false;
		if (conectingPartyName == null) {
			if (other.conectingPartyName != null)
				return false;
		} else if (!conectingPartyName.equals(other.conectingPartyName))
			return false;
		if (conectingPartyPhoneNumber == null) {
			if (other.conectingPartyPhoneNumber != null)
				return false;
		} else if (!conectingPartyPhoneNumber.equals(other.conectingPartyPhoneNumber))
			return false;
		if (contractPoint == null) {
			if (other.contractPoint != null)
				return false;
		} else if (!contractPoint.equals(other.contractPoint))
			return false;
		if (customerType == null) {
			if (other.customerType != null)
				return false;
		} else if (!customerType.equals(other.customerType))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!newId.equals(other.newId))
			return false;
		if (idn_area == null) {
			if (other.idn_area != null)
				return false;
		} else if (!idn_area.equals(other.idn_area))
			return false;
		if (idn_customer_type == null) {
			if (other.idn_customer_type != null)
				return false;
		} else if (!idn_customer_type.equals(other.idn_customer_type))
			return false;
		if (idn_pipeline_system == null) {
			if (other.idn_pipeline_system != null)
				return false;
		} else if (!idn_pipeline_system.equals(other.idn_pipeline_system))
			return false;
		if (idn_subarea == null) {
			if (other.idn_subarea != null)
				return false;
		} else if (!idn_subarea.equals(other.idn_subarea))
			return false;
		if (idn_system_point == null) {
			if (other.idn_system_point != null)
				return false;
		} else if (!idn_system_point.equals(other.idn_system_point))
			return false;
		if (idn_system_point_contract == null) {
			if (other.idn_system_point_contract != null)
				return false;
		} else if (!idn_system_point_contract.equals(other.idn_system_point_contract))
			return false;
		if (idn_system_point_group == null) {
			if (other.idn_system_point_group != null)
				return false;
		} else if (!idn_system_point_group.equals(other.idn_system_point_group))
			return false;
		if (idn_system_point_nomination == null) {
			if (other.idn_system_point_nomination != null)
				return false;
		} else if (!idn_system_point_nomination.equals(other.idn_system_point_nomination))
			return false;
		if (idn_system_point_param == null) {
			if (other.idn_system_point_param != null)
				return false;
		} else if (!idn_system_point_param.equals(other.idn_system_point_param))
			return false;
		if (idn_system_point_quality == null) {
			if (other.idn_system_point_quality != null)
				return false;
		} else if (!idn_system_point_quality.equals(other.idn_system_point_quality))
			return false;
		if (idn_system_point_type == null) {
			if (other.idn_system_point_type != null)
				return false;
		} else if (!idn_system_point_type.equals(other.idn_system_point_type))
			return false;
		if (idn_zone == null) {
			if (other.idn_zone != null)
				return false;
		} else if (!idn_zone.equals(other.idn_zone))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitud == null) {
			if (other.longitud != null)
				return false;
		} else if (!longitud.equals(other.longitud))
			return false;
		if (maxPressure == null) {
			if (other.maxPressure != null)
				return false;
		} else if (!maxPressure.equals(other.maxPressure))
			return false;
		if (meteringID == null) {
			if (other.meteringID != null)
				return false;
		} else if (!meteringID.equals(other.meteringID))
			return false;
		if (minPressure == null) {
			if (other.minPressure != null)
				return false;
		} else if (!minPressure.equals(other.minPressure))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nominalCapacity == null) {
			if (other.nominalCapacity != null)
				return false;
		} else if (!nominalCapacity.equals(other.nominalCapacity))
			return false;
		if (nominationPoint == null) {
			if (other.nominationPoint != null)
				return false;
		} else if (!nominationPoint.equals(other.nominationPoint))
			return false;
		if (pointType == null) {
			if (other.pointType != null)
				return false;
		} else if (!pointType.equals(other.pointType))
			return false;
		if (qualityPoint == null) {
			if (other.qualityPoint != null)
				return false;
		} else if (!qualityPoint.equals(other.qualityPoint))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (subarea == null) {
			if (other.subarea != null)
				return false;
		} else if (!subarea.equals(other.subarea))
			return false;
		if (systemCode == null) {
			if (other.systemCode != null)
				return false;
		} else if (!systemCode.equals(other.systemCode))
			return false;
		if (zone == null) {
			if (other.zone != null)
				return false;
		} else if (!zone.equals(other.zone))
			return false;
		if (versionDate == null) {
			if (other.versionDate != null)
				return false;
		} else if (!versionDate.equals(other.versionDate))
			return false;
		return true;
	}

}
