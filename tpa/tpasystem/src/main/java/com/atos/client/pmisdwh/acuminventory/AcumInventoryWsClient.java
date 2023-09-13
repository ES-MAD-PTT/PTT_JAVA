package com.atos.client.pmisdwh.acuminventory;

import javax.xml.bind.JAXBIntrospector;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.atos.wsdl.pmisdwh.acuminventory.ProcessMessage;
import com.atos.wsdl.pmisdwh.acuminventory.ProcessMessageResponse;

public class AcumInventoryWsClient extends WebServiceGatewaySupport {
	public ProcessMessageResponse getData(ProcessMessage request) {
		System.out.println(this.getDefaultUri());
		return (ProcessMessageResponse) JAXBIntrospector.getValue(getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback("http://message.ws.atos.com/AccumInventoryImplService/processMessage")));
    }
}
