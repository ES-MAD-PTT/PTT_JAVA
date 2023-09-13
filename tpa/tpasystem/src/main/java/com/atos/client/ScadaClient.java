package com.atos.client;

import com.atos.wsdl.DoExecution;
import com.atos.wsdl.DoExecutionResponse;

import java.io.Serializable;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class ScadaClient extends WebServiceGatewaySupport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -31960082942282073L;

	public DoExecutionResponse getData() {
        DoExecution request = new DoExecution();
        return (DoExecutionResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
