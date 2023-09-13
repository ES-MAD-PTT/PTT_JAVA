
package com.atos.wsdl.pmisdwh.acuminventory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="processMessageResult" type="{http://message.ws.atos.com/}meteringQueryResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "processMessageResult"
})
@XmlRootElement(name = "processMessageResponse")
public class ProcessMessageResponse {

    protected MeteringQueryResponse processMessageResult;

    /**
     * Gets the value of the processMessageResult property.
     * 
     * @return
     *     possible object is
     *     {@link MeteringQueryResponse }
     *     
     */
    public MeteringQueryResponse getProcessMessageResult() {
        return processMessageResult;
    }

    /**
     * Sets the value of the processMessageResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeteringQueryResponse }
     *     
     */
    public void setProcessMessageResult(MeteringQueryResponse value) {
        this.processMessageResult = value;
    }

}
