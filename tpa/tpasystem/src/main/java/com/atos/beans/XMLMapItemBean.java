package com.atos.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class XMLMapItemBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1174447151166438807L;

	private BigDecimal xmlMapItemId;
	private BigDecimal xmlMapBlockId;
	private int columnId;
    private String groupTag;
    private int groupTagCount;		// Indica los items que comparten un mismo groupTag en un bloque.
	private String itemTag;
    private int itemOrder;
    private String headerTag;
	private String labelTag;
    private String labelValue;
    
	public XMLMapItemBean() {
		super();
		this.xmlMapItemId = null;
		this.xmlMapBlockId = null;
		this.columnId = 0;
		this.groupTag = null;
		this.groupTagCount = 0;
		this.itemTag = null;
	    this.itemOrder = 0;
	    this.headerTag = null;
	    this.labelTag = null;	    
	    this.labelValue = null;
	}

	public BigDecimal getXmlMapItemId() {
		return xmlMapItemId;
	}

	public void setXmlMapItemId(BigDecimal xmlMapItemId) {
		this.xmlMapItemId = xmlMapItemId;
	}

	public BigDecimal getXmlMapBlockId() {
		return xmlMapBlockId;
	}

	public void setXmlMapBlockId(BigDecimal xmlMapBlockId) {
		this.xmlMapBlockId = xmlMapBlockId;
	}

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public String getGroupTag() {
		return groupTag;
	}

	public void setGroupTag(String groupTag) {
		this.groupTag = groupTag;
	}

    public int getGroupTagCount() {
		return groupTagCount;
	}

	public void setGroupTagCount(int groupTagCount) {
		this.groupTagCount = groupTagCount;
	}

	public String getItemTag() {
		return itemTag;
	}

	public void setItemTag(String itemTag) {
		this.itemTag = itemTag;
	}

	public int getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(int itemOrder) {
		this.itemOrder = itemOrder;
	}

	public String getHeaderTag() {
		return headerTag;
	}

	public void setHeaderTag(String headerTag) {
		this.headerTag = headerTag;
	}

    public String getLabelTag() {
		return labelTag;
	}

	public void setLabelTag(String labelTag) {
		this.labelTag = labelTag;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}
	
	@Override
	public String toString() {
		return "XMLMapItemBean [xmlMapItemId=" + xmlMapItemId + ", xmlMapBlockId=" + xmlMapBlockId + ", columnId="
				+ columnId + ", groupTag=" + groupTag + ", groupTagCount=" + groupTagCount + ", itemTag=" + itemTag
				+ ", itemOrder=" + itemOrder + ", headerTag=" + headerTag + ", labelTag=" + labelTag + ", labelValue="
				+ labelValue + "]";
	}
    
}
