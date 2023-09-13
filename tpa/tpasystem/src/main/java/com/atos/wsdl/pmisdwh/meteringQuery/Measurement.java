
package com.atos.wsdl.pmisdwh.meteringQuery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for measurement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="measurement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="c1" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="c2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="c2Plus" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="c3" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="c6" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="c7" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="sg" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="co2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="dewPoint" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="energy" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="h2s" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="heatingValue" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="hg" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ic4" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ic5" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="meteringPointId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="moisture" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="n2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="nc4" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="nc5" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="o2" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="pressure" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="registerTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="s" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="wobbeIndex" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="datasource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "measurement", propOrder = {
    "c1",
    "c2",
    "c2Plus",
    "c3",
    "c6",
    "c7",
    "sg",
    "co2",
    "dewPoint",
    "energy",
    "h2S",
    "heatingValue",
    "hg",
    "ic4",
    "ic5",
    "meteringPointId",
    "moisture",
    "n2",
    "nc4",
    "nc5",
    "o2",
    "pressure",
    "registerTimestamp",
    "s",
    "volume",
    "wobbeIndex",
    "datasource"
})
public class Measurement {

    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double c1;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double c2;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double c2Plus;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double c3;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double c6;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double c7;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double sg;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double co2;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double dewPoint;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double energy;
    @XmlElement(name = "h2s", required = true, type = Double.class, nillable = true)
    protected Double h2S;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double heatingValue;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double hg;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double ic4;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double ic5;
    protected String meteringPointId;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double moisture;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double n2;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double nc4;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double nc5;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double o2;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double pressure;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar registerTimestamp;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double s;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double volume;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double wobbeIndex;
    protected String datasource;

    /**
     * Gets the value of the c1 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getC1() {
        return c1;
    }

    /**
     * Sets the value of the c1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setC1(Double value) {
        this.c1 = value;
    }

    /**
     * Gets the value of the c2 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getC2() {
        return c2;
    }

    /**
     * Sets the value of the c2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setC2(Double value) {
        this.c2 = value;
    }

    /**
     * Gets the value of the c2Plus property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getC2Plus() {
        return c2Plus;
    }

    /**
     * Sets the value of the c2Plus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setC2Plus(Double value) {
        this.c2Plus = value;
    }

    /**
     * Gets the value of the c3 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getC3() {
        return c3;
    }

    /**
     * Sets the value of the c3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setC3(Double value) {
        this.c3 = value;
    }

    /**
     * Gets the value of the c6 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getC6() {
        return c6;
    }

    /**
     * Sets the value of the c6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setC6(Double value) {
        this.c6 = value;
    }

    /**
     * Gets the value of the c7 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getC7() {
        return c7;
    }

    /**
     * Sets the value of the c7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setC7(Double value) {
        this.c7 = value;
    }

    /**
     * Gets the value of the sg property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSg() {
        return sg;
    }

    /**
     * Sets the value of the sg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSg(Double value) {
        this.sg = value;
    }

    /**
     * Gets the value of the co2 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCo2() {
        return co2;
    }

    /**
     * Sets the value of the co2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCo2(Double value) {
        this.co2 = value;
    }

    /**
     * Gets the value of the dewPoint property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDewPoint() {
        return dewPoint;
    }

    /**
     * Sets the value of the dewPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDewPoint(Double value) {
        this.dewPoint = value;
    }

    /**
     * Gets the value of the energy property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEnergy() {
        return energy;
    }

    /**
     * Sets the value of the energy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEnergy(Double value) {
        this.energy = value;
    }

    /**
     * Gets the value of the h2S property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getH2S() {
        return h2S;
    }

    /**
     * Sets the value of the h2S property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setH2S(Double value) {
        this.h2S = value;
    }

    /**
     * Gets the value of the heatingValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHeatingValue() {
        return heatingValue;
    }

    /**
     * Sets the value of the heatingValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHeatingValue(Double value) {
        this.heatingValue = value;
    }

    /**
     * Gets the value of the hg property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getHg() {
        return hg;
    }

    /**
     * Sets the value of the hg property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setHg(Double value) {
        this.hg = value;
    }

    /**
     * Gets the value of the ic4 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getIc4() {
        return ic4;
    }

    /**
     * Sets the value of the ic4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setIc4(Double value) {
        this.ic4 = value;
    }

    /**
     * Gets the value of the ic5 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getIc5() {
        return ic5;
    }

    /**
     * Sets the value of the ic5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setIc5(Double value) {
        this.ic5 = value;
    }

    /**
     * Gets the value of the meteringPointId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeteringPointId() {
        return meteringPointId;
    }

    /**
     * Sets the value of the meteringPointId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeteringPointId(String value) {
        this.meteringPointId = value;
    }

    /**
     * Gets the value of the moisture property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMoisture() {
        return moisture;
    }

    /**
     * Sets the value of the moisture property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMoisture(Double value) {
        this.moisture = value;
    }

    /**
     * Gets the value of the n2 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getN2() {
        return n2;
    }

    /**
     * Sets the value of the n2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setN2(Double value) {
        this.n2 = value;
    }

    /**
     * Gets the value of the nc4 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNc4() {
        return nc4;
    }

    /**
     * Sets the value of the nc4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNc4(Double value) {
        this.nc4 = value;
    }

    /**
     * Gets the value of the nc5 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNc5() {
        return nc5;
    }

    /**
     * Sets the value of the nc5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNc5(Double value) {
        this.nc5 = value;
    }

    /**
     * Gets the value of the o2 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getO2() {
        return o2;
    }

    /**
     * Sets the value of the o2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setO2(Double value) {
        this.o2 = value;
    }

    /**
     * Gets the value of the pressure property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPressure() {
        return pressure;
    }

    /**
     * Sets the value of the pressure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPressure(Double value) {
        this.pressure = value;
    }

    /**
     * Gets the value of the registerTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRegisterTimestamp() {
        return registerTimestamp;
    }

    /**
     * Sets the value of the registerTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRegisterTimestamp(XMLGregorianCalendar value) {
        this.registerTimestamp = value;
    }

    /**
     * Gets the value of the s property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getS() {
        return s;
    }

    /**
     * Sets the value of the s property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setS(Double value) {
        this.s = value;
    }

    /**
     * Gets the value of the volume property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setVolume(Double value) {
        this.volume = value;
    }

    /**
     * Gets the value of the wobbeIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWobbeIndex() {
        return wobbeIndex;
    }

    /**
     * Sets the value of the wobbeIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWobbeIndex(Double value) {
        this.wobbeIndex = value;
    }

    /**
     * Gets the value of the datasource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasource(String value) {
        this.datasource = value;
    }

}
