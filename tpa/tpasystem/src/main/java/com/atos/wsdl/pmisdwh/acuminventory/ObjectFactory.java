
package com.atos.wsdl.pmisdwh.acuminventory;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.atos.wsdl.pmisdwh.acuminventory package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.atos.wsdl.pmisdwh.acuminventory
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProcessMessage }
     * 
     */
    public ProcessMessage createProcessMessage() {
        return new ProcessMessage();
    }

    /**
     * Create an instance of {@link ProcessMessageCls }
     * 
     */
    public ProcessMessageCls createProcessMessageCls() {
        return new ProcessMessageCls();
    }

    /**
     * Create an instance of {@link ProcessMessageResponse }
     * 
     */
    public ProcessMessageResponse createProcessMessageResponse() {
        return new ProcessMessageResponse();
    }

    /**
     * Create an instance of {@link MeteringQueryResponse }
     * 
     */
    public MeteringQueryResponse createMeteringQueryResponse() {
        return new MeteringQueryResponse();
    }

    /**
     * Create an instance of {@link MeteringQueryRequest }
     * 
     */
    public MeteringQueryRequest createMeteringQueryRequest() {
        return new MeteringQueryRequest();
    }

    /**
     * Create an instance of {@link DateRange }
     * 
     */
    public DateRange createDateRange() {
        return new DateRange();
    }

    /**
     * Create an instance of {@link CdmRequestHeader }
     * 
     */
    public CdmRequestHeader createCdmRequestHeader() {
        return new CdmRequestHeader();
    }

    /**
     * Create an instance of {@link DailyMeasurement }
     * 
     */
    public DailyMeasurement createDailyMeasurement() {
        return new DailyMeasurement();
    }

    /**
     * Create an instance of {@link CdmError }
     * 
     */
    public CdmError createCdmError() {
        return new CdmError();
    }

    /**
     * Create an instance of {@link CdmResponseHeader }
     * 
     */
    public CdmResponseHeader createCdmResponseHeader() {
        return new CdmResponseHeader();
    }

}
