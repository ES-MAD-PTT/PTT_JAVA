package com.atos.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllocationAutorunIntradayJob implements Job {

	@Autowired
    private AllocationAutorunIntradayClient service;

	@Autowired
	private AcumInventoryAutorunClient acumWS;
	
	@Autowired
	private BaseInventoryAutorunClient baseWS;
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			acumWS.callAcumInventoryClient();
			baseWS.callBaseInventoryClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		service.callAllocationIntradayRequestClient();
		
	}


}
