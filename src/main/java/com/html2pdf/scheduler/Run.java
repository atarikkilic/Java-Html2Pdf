package com.html2pdf.scheduler;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.realobjects.pdfreactor.webservice.client.Configuration;
import com.realobjects.pdfreactor.webservice.client.PDFreactor;
import com.realobjects.pdfreactor.webservice.client.PDFreactorWebserviceException;
import com.realobjects.pdfreactor.webservice.client.Result;

public class Run implements Job {
	
	public static final String URL="https://tcmb.gov.tr/";
	
	public static final String FileNamePrefix="tcmbgovtr_";
	
	JobDetail job = JobBuilder.newJob(Run.class)
		       .withIdentity("testJob")
		       .build();
	
	

   private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

 
   public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {    
   // Create new PDFreactor instance
      PDFreactor pdfReactor = new PDFreactor("https://cloud.pdfreactor.com/service/rest");
      // Create a new configuration object
      Configuration config = new Configuration();
      //Specify the input document for Windows systems
      config.setDocument(URL);
    //Date object
		 Date date= new Date();
	         //getTime() returns current time in milliseconds
		 long time = date.getTime();
	         //Passed the milliseconds to constructor of Timestamp class 
		 Timestamp ts = new Timestamp(time);
      try {
    	  
          // Render document and save result to result
          Result result = pdfReactor.convert(config);
          if (result != null) {
              byte[] pdf = result.getDocument();
              //Save the pdf at the desired location
              System.out.println(sdf1.format(ts));
              FileOutputStream fos = new FileOutputStream(FileNamePrefix+sdf1.format(ts)+".pdf");
              fos.write(pdf);
              fos.close();
          }
      } catch (PDFreactorWebserviceException exception) {
          Result result = exception.getResult();
          System.err.println(result.getError());
      } catch (Exception e) {
      } 
      

   }
 
}