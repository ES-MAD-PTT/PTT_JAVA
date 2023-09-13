
package com.atos.wsdl.pmisdwh.acuminventory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processMessageCls complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processMessageCls"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="meteringQueryRequest" type="{http://message.ws.atos.com/}meteringQueryRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processMessageCls", propOrder = {
    "meteringQueryRequest"
})
public class ProcessMessageCls {

    protected MeteringQueryRequest meteringQueryRequest;

    /**
     * Gets the value of the meteringQueryRequest property.
     * 
     * @return
     *     possible object is
     *     {@link MeteringQueryRequest }
     *     
     */
    public MeteringQueryRequest getMeteringQueryRequest() {
        return meteringQueryRequest;
    }

    /**
     * Sets the value of the meteringQueryRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeteringQueryRequest }
     *     
     */
    public void setMeteringQueryRequest(MeteringQueryRequest value) {
        this.meteringQueryRequest = value;
    }

}
