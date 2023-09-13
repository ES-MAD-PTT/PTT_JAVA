package com.atos.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AcumInventoryAutorunJob implements Job {

	@Autowired
    private AcumInventoryAutorunClient service;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			service.callAcumInventoryClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
