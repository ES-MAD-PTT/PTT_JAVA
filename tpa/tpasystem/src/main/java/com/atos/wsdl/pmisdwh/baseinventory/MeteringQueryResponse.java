
package com.atos.wsdl.pmisdwh.baseinventory;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for meteringQueryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="meteringQueryResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dailyMeasurement" type="{http://message.ws.atos.com/}_dailyMeasurement" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="error" type="{http://message.ws.atos.com/}cdmError"/&gt;
 *         &lt;element name="header" type="{http://message.ws.atos.com/}cdmResponseHeader"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "meteringQueryResponse", propOrder = {
    "dailyMeasurement",
    "error",
    "header"
})
public class MeteringQueryResponse {

    protected List<DailyMeasurement> dailyMeasurement;
    @XmlElement(required = true, nillable = true)
    protected CdmError error;
    @XmlElement(required = true, nillable = true)
    protected CdmResponseHeader header;

    /**
     * Gets the value of the dailyMeasurement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dailyMeasurement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDailyMeasurement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DailyMeasurement }
     * 
     * 
     */
    public List<DailyMeasurement> getDailyMeasurement() {
        if (dailyMeasurement == null) {
            dailyMeasurement = new ArrayList<DailyMeasurement>();
        }
        return this.dailyMeasurement;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link CdmError }
     *     
     */
    public CdmError getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link CdmError }
     *     
     */
    public void setError(CdmError value) {
        this.error = value;
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link CdmResponseHeader }
     *     
     */
    public CdmResponseHeader getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link CdmResponseHeader }
     *     
     */
    public void setHeader(CdmResponseHeader value) {
        this.header = value;
    }

}
