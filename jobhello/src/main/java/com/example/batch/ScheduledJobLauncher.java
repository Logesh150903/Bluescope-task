package com.example.batch;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;


public class ScheduledJobLauncher {

    private static JobLauncher jobLauncher;
    private static Job job;
    private static boolean isJobRunning = false; 
    
    

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public void setJob(Job job) {
        this.job = job;
    }
  //  @Scheduled(fixedRate = 5000)
    public static synchronized void runJob() {
        if (isJobRunning) {
            System.out.println("Job is already running, skipping execution.");
            return;
        }
        
        try {
            isJobRunning = true;  

            JobParameters params = new JobParametersBuilder()
                    .addDate("timestamp", new Date()) 
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, params);
            System.out.println("Job Status: " + execution.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isJobRunning = false;  
        }
    }

    
    public String startthejob() {
    	ApplicationContext context = new ClassPathXmlApplicationContext("batch-config.xml");
    	return "Start...";
	}
    public static void main(String[] args) {
    	
    	ScheduledJobLauncher ScheduledJobLauncher=new ScheduledJobLauncher();
    	ScheduledJobLauncher.startthejob();
    	
        System.out.println("'Hello world'print for every 5 seconds.");
    }
}
