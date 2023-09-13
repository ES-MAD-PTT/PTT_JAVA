package com.atos.beans.dam;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import com.atos.beans.UserAudBean;

@XmlRootElement(name = "AreaQualityPerShipperDetailsBean")
public class AreaQualityPerShipperDetailsBean extends UserAudBean implements Serializable {

	private static final long serialVersionUID = -5255211382691787452L;

	private BigDecimal idn_area;
	private BigDecimal min_value;
	private BigDecimal max_value;
	
	public AreaQualityPerShipperDetailsBean(BigDecimal idn_area, BigDecimal min_value, BigDecimal max_value) {
		super();
		this.idn_area = idn_area;
		this.min_value = min_value;
		this.max_value = max_value;
	}

	public AreaQualityPerShipperDetailsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}

	public BigDecimal getMin_value() {
		return min_value;
	}

	public void setMin_value(BigDecimal min_value) {
		this.min_value = min_value;
	}

	public BigDecimal getMax_value() {
		return max_value;
	}

	public void setMax_value(BigDecimal max_value) {
		this.max_value = max_value;
	}	
	
}
