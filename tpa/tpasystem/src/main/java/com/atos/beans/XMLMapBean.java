package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class XMLMapBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5665474452366365129L;

	private BigDecimal xmlMapId;
    private String mapDesc;
    private String rootTag;
    
	public XMLMapBean() {
		super();
		this.xmlMapId = null;
		this.mapDesc = null;
		this.rootTag = null;
	}

	public BigDecimal getXmlMapId() {
		return xmlMapId;
	}

	public void setXmlMapId(BigDecimal xmlMapId) {
		this.xmlMapId = xmlMapId;
	}

	public String getMapDesc() {
		return mapDesc;
	}

	public void setMapDesc(String mapDesc) {
		this.mapDesc = mapDesc;
	}

	public String getRootTag() {
		return rootTag;
	}

	public void setRootTag(String rootTag) {
		this.rootTag = rootTag;
	}

	@Override
	public String toString() {
		return "XMLMapBean [xmlMapId=" + xmlMapId + ", mapDesc=" + mapDesc + ", rootTag=" + rootTag + "]";
	}
    
}
