package com.atos.client.pmisdwh.meteringQuery;

import javax.xml.bind.JAXBIntrospector;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.atos.wsdl.pmisdwh.meteringQuery.ProcessMessage;
import com.atos.wsdl.pmisdwh.meteringQuery.ProcessMessageResponse;

public class MetQueryWsClient extends WebServiceGatewaySupport {
	public ProcessMessageResponse getData(ProcessMessage request) {
		System.out.println(this.getDefaultUri());
		return (ProcessMessageResponse) JAXBIntrospector.getValue(getWebServiceTemplate().marshalSendAndReceive(request,new SoapActionCallback("http://message.ws.atos.com/MeteringQueryWsImplService/processMessage")));
    }
}
