package com.atos.filters.dam;

import java.io.Serializable;
import java.math.BigDecimal;

public class AreaQualityPerShipperFilter implements Serializable{

	
	private static final long serialVersionUID = -5384739501695766356L;
	
	private BigDecimal idn_area;
	private BigDecimal idn_shipper;
	private BigDecimal idn_system;
	private String parameterCode1;
	private String parameterCode2;

	public AreaQualityPerShipperFilter() {		
		this.idn_area=null;
		this.idn_shipper=null;
		this.idn_system=null;
		this.parameterCode1=null;
		this.parameterCode2=null;
	}


	public AreaQualityPerShipperFilter(BigDecimal idn_area, BigDecimal idn_shipper, BigDecimal idn_system,
			String parameterCode1, String parameterCode2) {
		super();
		this.idn_area = idn_area;
		this.idn_shipper = idn_shipper;
		this.idn_system = idn_system;
		this.parameterCode1 = parameterCode1;
		this.parameterCode2 = parameterCode2;
	}

	public BigDecimal getIdn_area() {
		return idn_area;
	}

	public void setIdn_area(BigDecimal idn_area) {
		this.idn_area = idn_area;
	}


	public BigDecimal getIdn_shipper() {
		return idn_shipper;
	}


	public void setIdn_shipper(BigDecimal idn_shipper) {
		this.idn_shipper = idn_shipper;
	}


	public BigDecimal getIdn_system() {
		return idn_system;
	}


	public void setIdn_system(BigDecimal idn_system) {
		this.idn_system = idn_system;
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


	@Override
	public String toString() {
		return "AreaQualityPerShipperFilter [idn_area=" + idn_area + ", idn_shipper=" + idn_shipper + ", idn_system="
				+ idn_system + ", parameterCode1=" + parameterCode1 + ", parameterCode2=" + parameterCode2 + "]";
	}
		
	
	
}
