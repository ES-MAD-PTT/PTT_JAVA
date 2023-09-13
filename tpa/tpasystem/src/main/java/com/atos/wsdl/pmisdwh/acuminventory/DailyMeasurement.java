
package com.atos.wsdl.pmisdwh.acuminventory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for dailyMeasurement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dailyMeasurement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="gasDay" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="east_value" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="west_value" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dailyMeasurement", propOrder = {
    "gasDay",
    "eastValue",
    "westValue"
})
public class DailyMeasurement {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar gasDay;
    @XmlElement(name = "east_value", required = true, type = Double.class, nillable = true)
    protected Double eastValue;
    @XmlElement(name = "west_value", required = true, type = Double.class, nillable = true)
    protected Double westValue;

    /**
     * Gets the value of the gasDay property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGasDay() {
        return gasDay;
    }

    /**
     * Sets the value of the gasDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGasDay(XMLGregorianCalendar value) {
        this.gasDay = value;
    }

    /**
     * Gets the value of the eastValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEastValue() {
        return eastValue;
    }

    /**
     * Sets the value of the eastValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEastValue(Double value) {
        this.eastValue = value;
    }

    /**
     * Gets the value of the westValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWestValue() {
        return westValue;
    }

    /**
     * Sets the value of the westValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWestValue(Double value) {
        this.westValue = value;
    }

}
