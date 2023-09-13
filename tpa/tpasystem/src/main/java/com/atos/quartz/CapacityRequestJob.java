package com.atos.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CapacityRequestJob implements Job {

	@Autowired
    private CapacityRequestClient service;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		service.callCapacityRequestClient();
		
	}


}
