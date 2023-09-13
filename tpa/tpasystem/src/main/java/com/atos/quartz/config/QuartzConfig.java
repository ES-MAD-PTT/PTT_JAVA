package com.atos.quartz.config;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.atos.client.WSClientJob;
import com.atos.quartz.AllocationAutorunIntradayJob;
import com.atos.quartz.AllocationAutorunJob;
import com.atos.quartz.CSVDailyGenerationJob;
import com.atos.quartz.CSVMonthGenerationJob;
import com.atos.quartz.CSVWeeklyGenerationJob;
import com.atos.quartz.CapacityRequestJob;
import com.atos.quartz.MailLongGenerationJob;
import com.atos.quartz.MailMediumGenerationJob;
import com.atos.quartz.MailShortGenerationJob;

@Configuration
public class QuartzConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void init() {
		log.debug("QuartzConfig initialized.");
	}

	@Bean
	public SchedulerFactoryBean quartzScheduler() {
		SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

		quartzScheduler.setDataSource(dataSource);
		quartzScheduler.setTransactionManager(transactionManager);
		quartzScheduler.setOverwriteExistingJobs(true);
		quartzScheduler.setSchedulerName("quartz-scheduler");

		// custom job factory of spring with DI support for @Autowired!
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		quartzScheduler.setJobFactory(jobFactory);

		quartzScheduler.setQuartzProperties(quartzProperties());

		Trigger[] triggers = { scadaJobTrigger().getObject(),
							   capacityRequestTrigger().getObject(), 
							   allocationCommercialRequestTrigger().getObject(), 
							   allocationIntradayRequestTrigger().getObject() , 
							   generateCSVDailyTrigger().getObject(), 
							   generateCSVMonthTrigger().getObject(),
							   generateCSVWeeklyTrigger().getObject(), 
							   generateShortMailTrigger().getObject(),
							   generateMediumMailTrigger().getObject(),
							   generateLongMailTrigger().getObject()
							   };
		quartzScheduler.setTriggers(triggers);

		return quartzScheduler;
	}

	@Bean
	public JobDetailFactoryBean scadaJobBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(WSClientJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	public SimpleTriggerFactoryBean scadaJobTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(scadaJobBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(10000);
		return trigger;
	}

	@Bean
	public JobDetailFactoryBean capacityRequestBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(CapacityRequestJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	public CronTriggerFactoryBean capacityRequestTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(capacityRequestBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 00 10 1/1 * ? *");
		return cronTriggerFactoryBean;
	}

	

	@Bean
	public JobDetailFactoryBean allocationCommercialRequestBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(AllocationAutorunJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean allocationCommercialRequestTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(allocationCommercialRequestBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 30 0,4,7 ? * * *");
		return cronTriggerFactoryBean;
	}
	

	@Bean
	public JobDetailFactoryBean allocationIntradayRequestBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(AllocationAutorunIntradayJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean allocationIntradayRequestTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(allocationIntradayRequestBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 15 0,3,6,9,12,15,18,21 ? * * *");
		return cronTriggerFactoryBean;
	}
/*	@Bean
	public SimpleTriggerFactoryBean allocationIntradayRequestTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(allocationIntradayRequestBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(45000);
		return trigger;
	}*/

	@Bean
	public JobDetailFactoryBean generateCSVDailyBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(CSVDailyGenerationJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	
	@Bean
	public CronTriggerFactoryBean generateCSVDailyTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(generateCSVDailyBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 0 7 ? * * *");
		return cronTriggerFactoryBean;
	}
/*	@Bean
	public SimpleTriggerFactoryBean generateCSVDailyTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(generateCSVDailyBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(15000);
		return trigger;
	}*/

	@Bean
	public JobDetailFactoryBean generateCSVMonthBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(CSVMonthGenerationJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean generateCSVMonthTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(generateCSVMonthBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 0 5 1 * ? *");
//		cronTriggerFactoryBean.setCronExpression("0 20 7 ? * * *");
		return cronTriggerFactoryBean;
	}

/*	@Bean
	public SimpleTriggerFactoryBean generateCSVMonthTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(generateCSVMonthBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(15000);
		return trigger;
	}*/
	
	
	@Bean
	public JobDetailFactoryBean generateCSVWeeklyBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(CSVWeeklyGenerationJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
	
	
	@Bean
	public CronTriggerFactoryBean generateCSVWeeklyTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(generateCSVWeeklyBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 0 6 ? * MON *");
//		cronTriggerFactoryBean.setCronExpression("0 10 7 ? * * *");
		return cronTriggerFactoryBean;
	}
	
/*	@Bean
	public SimpleTriggerFactoryBean generateCSVWeeklyTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(generateCSVWeeklyBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(15000);
		return trigger;
	}*/

	@Bean
	public JobDetailFactoryBean generateShortMailBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(MailShortGenerationJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
	
	
	@Bean
	public CronTriggerFactoryBean generateShortMailTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(generateShortMailBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 30 7 10 * ? *");
		return cronTriggerFactoryBean;
	}

/*	@Bean
	public SimpleTriggerFactoryBean generateShortMailTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(generateShortMailBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(15000);
		return trigger;
	}*/

	@Bean
	public JobDetailFactoryBean generateMediumMailBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(MailMediumGenerationJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean generateMediumMailTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(generateMediumMailBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 30 7 1 1,4,7,10 ? *");
		return cronTriggerFactoryBean;
	}

/*	@Bean
	public SimpleTriggerFactoryBean generateMediumMailTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(generateMediumMailBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(30000);
		return trigger;
	}*/

	@Bean
	public JobDetailFactoryBean generateLongMailBean() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(MailLongGenerationJob.class);
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}
	
	@Bean
	public CronTriggerFactoryBean generateLongMailTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(generateLongMailBean().getObject());
		cronTriggerFactoryBean.setCronExpression("0 30 7 1,15 12 ? *");
		return cronTriggerFactoryBean;
	}

/*	@Bean
	public SimpleTriggerFactoryBean generateLongMailTrigger() {
		SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
		trigger.setJobDetail(generateLongMailBean().getObject());
		trigger.setRepeatInterval(300000);
		trigger.setStartDelay(45000);
		return trigger;
	}*/

	@Bean
	public Properties quartzProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		Properties properties = null;
		try {
			propertiesFactoryBean.afterPropertiesSet();
			properties = propertiesFactoryBean.getObject();

		} catch (IOException e) {
			log.warn("Cannot load quartz.properties.");
		}

		return properties;
	}
}