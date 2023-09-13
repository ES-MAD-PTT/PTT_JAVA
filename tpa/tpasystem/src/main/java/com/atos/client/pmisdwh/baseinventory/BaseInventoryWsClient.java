package com.atos.client.pmisdwh.baseinventory;

import javax.xml.bind.JAXBIntrospector;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.atos.wsdl.pmisdwh.baseinventory.ProcessMessage;
import com.atos.wsdl.pmisdwh.baseinventory.ProcessMessageResponse;

public class BaseInventoryWsClient extends WebServiceGatewaySupport {
	public ProcessMessageResponse getData(ProcessMessage request) {
		System.out.println(this.getDefaultUri());
		return (ProcessMessageResponse) JAXBIntrospector.getValue(getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback("http://message.ws.atos.com/BaseInventoryImplService/processMessage")));
    }
}
