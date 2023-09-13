
package com.atos.wsdl.pmisdwh.baseinventory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for _dailyMeasurement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="_dailyMeasurement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="gasDay" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="zone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hv" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="base_inventory" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="high_threshold_red" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="high_threshold_orange" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="high_trheshold_alert" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="low_threshold_alert" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="low_threshold_orange" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="low_threshold_red" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "_dailyMeasurement", propOrder = {
    "gasDay",
    "zone",
    "mode",
    "hv",
    "baseInventory",
    "highThresholdRed",
    "highThresholdOrange",
    "highTrhesholdAlert",
    "lowThresholdAlert",
    "lowThresholdOrange",
    "lowThresholdRed"
})
public class DailyMeasurement {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar gasDay;
    protected String zone;
    protected String mode;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double hv;
    @XmlElement(name = "base_inventory", required = true, type = Double.class, nillable = true)
    protected Double baseInventory;
    @XmlElement(name = "high_threshold_red", required = true, type = Double.class, nillable = true)
    protected Double highThresholdRed;
    @XmlElement(name = "high_threshold_orange", required = true, type = Double.class, nillable = true)
    protected Double highThresholdOrange;
    @XmlElement(name = "high_trheshold_alert", required = true, type = Double.class, nillable = true)
    protected Double highTrhesholdAlert;
    @XmlElement(name = "low_threshold_alert", required = true, type = Double.class, nillable = true)
    protected Double lowThresholdAlert;
    @XmlElement(name = "low_threshold_orange", required = true, type = Double.class, nillable = true)
    protected Double lowThresholdOrange;
    @XmlElement(name = "low_threshold_red", required = true, type = Double.class, nillable = true)
    protected Double lowThresholdRed;

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
     * Gets the value of the zone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZone() {
        return zone;
    }

    /**
     * Sets the value of the zone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZone(String value) {
        this.zone = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

    /**
     * Gets the value of the hv property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHv() {
        return hv;
    }

    /**
     * Sets the value of the hv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHv(Double value) {
        this.hv = value;
    }

    /**
     * Gets the value of the baseInventory property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBaseInventory() {
        return baseInventory;
    }

    /**
     * Sets the value of the baseInventory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBaseInventory(Double value) {
        this.baseInventory = value;
    }

    /**
     * Gets the value of the highThresholdRed property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHighThresholdRed() {
        return highThresholdRed;
    }

    /**
     * Sets the value of the highThresholdRed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHighThresholdRed(Double value) {
        this.highThresholdRed = value;
    }

    /**
     * Gets the value of the highThresholdOrange property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHighThresholdOrange() {
        return highThresholdOrange;
    }

    /**
     * Sets the value of the highThresholdOrange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHighThresholdOrange(Double value) {
        this.highThresholdOrange = value;
    }

    /**
     * Gets the value of the highTrhesholdAlert property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHighTrhesholdAlert() {
        return highTrhesholdAlert;
    }

    /**
     * Sets the value of the highTrhesholdAlert property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHighTrhesholdAlert(Double value) {
        this.highTrhesholdAlert = value;
    }

    /**
     * Gets the value of the lowThresholdAlert property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLowThresholdAlert() {
        return lowThresholdAlert;
    }

    /**
     * Sets the value of the lowThresholdAlert property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLowThresholdAlert(Double value) {
        this.lowThresholdAlert = value;
    }

    /**
     * Gets the value of the lowThresholdOrange property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLowThresholdOrange() {
        return lowThresholdOrange;
    }

    /**
     * Sets the value of the lowThresholdOrange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLowThresholdOrange(Double value) {
        this.lowThresholdOrange = value;
    }

    /**
     * Gets the value of the lowThresholdRed property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLowThresholdRed() {
        return lowThresholdRed;
    }

    /**
     * Sets the value of the lowThresholdRed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLowThresholdRed(Double value) {
        this.lowThresholdRed = value;
    }

}
