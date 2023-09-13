
package com.atos.wsdl.pmisdwh.meteringQuery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meteringQueryRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meteringQueryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="gasDayRange" type="{http://message.ws.atos.com/}dateRange" minOccurs="0"/&gt;
 *         &lt;element name="header" type="{http://message.ws.atos.com/}cdmRequestHeader" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meteringQueryRequest", propOrder = {
    "gasDayRange",
    "header"
})
public class MeteringQueryRequest {

    protected DateRange gasDayRange;
    protected CdmRequestHeader header;

    /**
     * Gets the value of the gasDayRange property.
     * 
     * @return
     *     possible object is
     *     {@link DateRange }
     *     
     */
    public DateRange getGasDayRange() {
        return gasDayRange;
    }

    /**
     * Sets the value of the gasDayRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateRange }
     *     
     */
    public void setGasDayRange(DateRange value) {
        this.gasDayRange = value;
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link CdmRequestHeader }
     *     
     */
    public CdmRequestHeader getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link CdmRequestHeader }
     *     
     */
    public void setHeader(CdmRequestHeader value) {
        this.header = value;
    }

}
