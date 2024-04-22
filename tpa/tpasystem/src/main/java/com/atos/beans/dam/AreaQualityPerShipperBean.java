package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.atos.beans.UserAudBean;

@XmlRootElement(name = "AreaQualityPerShipperBean")
public class AreaQualityPerShipperBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_area;
	private String area_code;
	private String area_desc; 
	private BigDecimal idn_area_gas_quality;
	private Date start_date_gas_Quality;
	private BigDecimal idn_user_group;
	private String user_group_id;
	private String user_group_name;
	private String shortName;
	private String parameterCode1;
	private String parameterCode2;
	private BigDecimal wi_min;
	private BigDecimal wi_max;
	private BigDecimal hv_min;
	private BigDecimal hv_max;
	private List<AreaQualityPerShipperDetailsBean> details;
	private List<AreaQualityPerShipperDetailsBean> details2;

	public AreaQualityPerShipperBean() {
		super();
	}

	public AreaQualityPerShipperBean(BigDecimal idn_area, String area_code, String area_desc,
			BigDecimal idn_area_gas_quality, Date start_date_gas_Quality, BigDecimal idn_user_group,
			String user_group_id, String user_group_name, String shortName, String parameterCode1,
			String parameterCode2, BigDecimal wi_min, BigDecimal wi_max, BigDecimal hv_min, BigDecimal hv_max,
			List<AreaQualityPerShipperDetailsBean> details, List<AreaQualityPerShipperDetailsBean> details2) {
		super();
		this.idn_area = idn_area;
		this.area_code = area_code;
		this.area_desc = area_desc;
		this.idn_area_gas_quality = idn_area_gas_quality;
		this.start_date_gas_Quality = start_date_gas_Quality;
		this.idn_user_group = idn_user_group;
		this.user_group_id = user_group_id;
		this.user_group_name = user_group_name;
		this.shortName = shortName;
		this.parameterCode1 = parameterCode1;
		this.parameterCode2 = parameterCode2;
		this.wi_min = wi_min;
		this.wi_max = wi_max;
		this.hv_min = hv_min;
		this.hv_max = hv_max;
		this.details = details;
		this.details2 = details2;
	}



	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getArea_desc() {
		return area_desc;
	}

	public void setArea_desc(String area_desc) {
		this.area_desc = area_desc;
	}

	public BigDecimal getIdn_area_gas_quality() {
		return idn_area_gas_quality;
	}

	public void setIdn_area_gas_quality(BigDecimal idn_area_gas_quality) {
		this.idn_area_gas_quality = idn_area_gas_quality;
	}

	public Date getStart_date_gas_Quality() {
		return start_date_gas_Quality;
	}

	public void setStart_date_gas_Quality(Date start_date_gas_Quality) {
		this.start_date_gas_Quality = start_date_gas_Quality;
	}

	public BigDecimal getIdn_user_group() {
		return idn_user_group;
	}

	public void setIdn_user_group(BigDecimal idn_user_group) {
		this.idn_user_group = idn_user_group;
	}

	public String getUser_group_id() {
		return user_group_id;
	}

	public void setUser_group_id(String user_group_id) {
		this.user_group_id = user_group_id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getUser_group_name() {
		return user_group_name;
	}

	public void setUser_group_name(String user_group_name) {
		this.user_group_name = user_group_name;
	}
	
	public BigDecimal getWi_min() {
		return wi_min;
	}

	public void setWi_min(BigDecimal wi_min) {
		this.wi_min = wi_min;
	}

	public BigDecimal getWi_max() {
		return wi_max;
	}

	public void setWi_max(BigDecimal wi_max) {
		this.wi_max = wi_max;
	}

	public BigDecimal getHv_min() {
		return hv_min;
	}

	public void setHv_min(BigDecimal hv_min) {
		this.hv_min = hv_min;
	}

	public BigDecimal getHv_max() {
		return hv_max;
	}

	public void setHv_max(BigDecimal hv_max) {
		this.hv_max = hv_max;
	}

	public String getParameterCode1() {
		return parameterCode1;
	}

	public void setParameterCode1(String parameterCode1) {
		this.parameterCode1 = parameterCode1;
	}

	public String getParameterCode2() {
		return parameterCode2;
	}

	public void setParameterCode2(String parameterCode2) {
		this.parameterCode2 = parameterCode2;
	}

	public List<AreaQualityPerShipperDetailsBean> getDetails() {
		return details;
	}

	public void setDetails(List<AreaQualityPerShipperDetailsBean> details) {
		this.details = details;
		if(this.details.size()==1) {
			this.wi_min = this.details.get(0).getMin_value();
			this.wi_max = this.details.get(0).getMax_value();
			
			
		} else {
			
		}
	}
	
	public List<AreaQualityPerShipperDetailsBean> getDetails2() {
		return details2;
	}
	
	public void setDetails2(List<AreaQualityPerShipperDetailsBean> details) {
		this.details2 = details;
		if(this.details2.size()==1) {
			this.hv_min = this.details2.get(0).getMin_value();
			this.hv_max = this.details2.get(0).getMax_value();
			
			
		} else {
			
		}
	}


}
