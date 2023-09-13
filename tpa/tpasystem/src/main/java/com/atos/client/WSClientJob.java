package com.atos.client;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WSClientJob implements Job {

	@Autowired
    private WSClient wsClient;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		wsClient.callWSClient();
		
	}


}
