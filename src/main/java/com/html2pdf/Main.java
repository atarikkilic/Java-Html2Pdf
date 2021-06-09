package com.html2pdf;

import java.io.IOException;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.html2pdf.scheduler.Run;
import com.realobjects.pdfreactor.webservice.client.PDFreactorWebserviceException;



public class Main {

	public static final int FREQUENCY=86400;
	
	public static void main(String[] args) throws PDFreactorWebserviceException, IOException {
		 try {
			 
	         // specify the job' s details..
	         JobDetail job = JobBuilder.newJob(Run.class)
	                                   .withIdentity("testJob")
	                                   .build();
	 
	         // specify the running period of the job
	         Trigger trigger = TriggerBuilder.newTrigger()
	                                         .withSchedule(SimpleScheduleBuilder.simpleSchedule()
	                                                                            .withIntervalInSeconds(FREQUENCY)
	                                                                            .repeatForever())
	                                          .build();
	 
	         //schedule the job
	         SchedulerFactory schFactory = new StdSchedulerFactory();
	         Scheduler sch = schFactory.getScheduler();
	         sch.start();
	         sch.scheduleJob(job, trigger);
	 
	      } catch (SchedulerException e) {
	         e.printStackTrace();
	      }
	   }
	}
