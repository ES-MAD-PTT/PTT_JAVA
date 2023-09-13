package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class WsTemplateBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6633513697705779837L;

	private BigDecimal idn_webservice_template;
    private String filename;
    private BigDecimal xmlMapId;
	
	public WsTemplateBean() {
		super();
		this.idn_webservice_template = null;
		this.filename = null;
		this.xmlMapId = null;
	}

	public BigDecimal getIdn_webservice_template() {
		return idn_webservice_template;
	}

	public void setIdn_webservice_template(BigDecimal idn_webservice_template) {
		this.idn_webservice_template = idn_webservice_template;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public BigDecimal getXmlMapId() {
		return xmlMapId;
	}

	public void setXmlMapId(BigDecimal idn_xml_map) {
		this.xmlMapId = idn_xml_map;
	}

	@Override
	public String toString() {
		return "WsTemplateBean [idn_webservice_template=" + idn_webservice_template + ", filename=" + filename
				+ ", xmlMapId=" + xmlMapId + "]";
	}
	
}
