package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TreeMap;

public class XMLMapBlockBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2201234189320075151L;

	private BigDecimal xmlMapBlockId;
	private BigDecimal xmlMapSheetId;
    private int blockOrder;
    private String blockTag;
    private String startLimit;
    private String endLimit;
    // En este arbol se guardan los items del bloque que se guardan al cargar 
    // la configuracion.
	private TreeMap<Integer, XMLMapItemBean> tmConfigItems;
    
	public XMLMapBlockBean() {
		super();
		this.xmlMapBlockId = null;
		this.xmlMapSheetId = null;
		this.blockOrder = 0;
		this.blockTag = null;
		this.startLimit = null;
		this.endLimit = null;
		this.tmConfigItems = null;
	}

	public BigDecimal getXmlMapBlockId() {
		return xmlMapBlockId;
	}

	public void setXmlMapBlockId(BigDecimal xmlMapBlockId) {
		this.xmlMapBlockId = xmlMapBlockId;
	}

	public BigDecimal getXmlMapSheetId() {
		return xmlMapSheetId;
	}

	public void setXmlMapSheetId(BigDecimal xmlMapSheetId) {
		this.xmlMapSheetId = xmlMapSheetId;
	}

	public int getBlockOrder() {
		return blockOrder;
	}

	public void setBlockOrder(int blockOrder) {
		this.blockOrder = blockOrder;
	}

	public String getBlockTag() {
		return blockTag;
	}

	public void setBlockTag(String blockTag) {
		this.blockTag = blockTag;
	}

	public String getStartLimit() {
		return startLimit;
	}

	public void setStartLimit(String startLimit) {
		this.startLimit = startLimit;
	}

	public String getEndLimit() {
		return endLimit;
	}

	public void setEndLimit(String endLimit) {
		this.endLimit = endLimit;
	}

	public TreeMap<Integer, XMLMapItemBean> getTmConfigItems() {
		return tmConfigItems;
	}

	public void setTmConfigItems(TreeMap<Integer, XMLMapItemBean> tmConfigItems) {
		this.tmConfigItems = tmConfigItems;
	}
	
	@Override
	public String toString() {
		return "XMLMapBlockBean [xmlMapBlockId=" + xmlMapBlockId + ", xmlMapSheetId=" + xmlMapSheetId + ", blockOrder="
				+ blockOrder + ", blockTag=" + blockTag + ", startLimit=" + startLimit + ", endLimit=" + endLimit
				+ ", tmConfigItems=" + tmConfigItems + "]";
	}
}
