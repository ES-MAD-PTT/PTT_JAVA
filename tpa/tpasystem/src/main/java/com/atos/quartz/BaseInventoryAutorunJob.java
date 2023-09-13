package com.atos.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class BaseInventoryAutorunJob implements Job {

	@Autowired
    private BaseInventoryAutorunClient service;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			service.callBaseInventoryClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
