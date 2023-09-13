package com.atos.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CSVMonthGenerationJob implements Job {

	@Autowired
	private CSVMonthGeneration service;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		service.generateCSV();
		
	}

}
