package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.TreeMap;


public class XMLMapSheetBean   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2462893471843678977L;

	private BigDecimal xmlMapSheetId;
	private BigDecimal xmlMapId;
    private int sheetNumber;
    private String sheetName;
	private String sheetTag;
    // En este arbol se guardan los bloques de la sheet que se guardan al cargar 
    // la configuracion.
	private TreeMap<Integer, XMLMapBlockBean> tmConfigBlocks;
    

	public XMLMapSheetBean() {
		super();
		this.xmlMapSheetId = null;
		this.xmlMapId = null;
		this.sheetNumber = 0;
		this.sheetName = null;
		this.sheetTag = null;
		this.tmConfigBlocks = null;
	}

	public BigDecimal getXmlMapSheetId() {
		return xmlMapSheetId;
	}

	public void setXmlMapSheetId(BigDecimal xmlMapSheetId) {
		this.xmlMapSheetId = xmlMapSheetId;
	}

	public BigDecimal getXmlMapId() {
		return xmlMapId;
	}

	public void setXmlMapId(BigDecimal xmlMapId) {
		this.xmlMapId = xmlMapId;
	}

	public int getSheetNumber() {
		return sheetNumber;
	}

	public void setSheetNumber(int sheetNumber) {
		this.sheetNumber = sheetNumber;
	}

    public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetTag() {
		return sheetTag;
	}

	public void setSheetTag(String sheetTag) {
		this.sheetTag = sheetTag;
	}
	
	public TreeMap<Integer, XMLMapBlockBean> getTmConfigBlocks() {
		return tmConfigBlocks;
	}

	public void setTmConfigBlocks(TreeMap<Integer, XMLMapBlockBean> tmConfigBlocks) {
		this.tmConfigBlocks = tmConfigBlocks;
	}
	
	@Override
	public String toString() {
		return "XMLMapSheetBean [xmlMapSheetId=" + xmlMapSheetId + ", xmlMapId=" + xmlMapId + ", sheetNumber="
				+ sheetNumber + ", sheetName=" + sheetName + ", sheetTag=" + sheetTag + ", tmConfigBlocks="
				+ tmConfigBlocks + "]";
	}
    
}
