package com.atos.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CSVWeeklyGenerationJob implements Job {

	@Autowired
	private CSVWeeklyGeneration service;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		service.generateCSV();
		
	}

}
