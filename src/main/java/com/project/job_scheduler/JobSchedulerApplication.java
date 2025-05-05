package com.project.job_scheduler;

import com.project.job_scheduler.model.JobUpdate;
import com.project.job_scheduler.repository.JobUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class JobSchedulerApplication implements CommandLineRunner {

	@Autowired
	private JobUpdateRepository jobUpdateRepository;
	public static void main(String[] args) {
		SpringApplication.run(JobSchedulerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// Sample JobUpdate to be saved on startup
		JobUpdate jobUpdate = new JobUpdate();
		jobUpdate.setUUId(3L);
		jobUpdate.setStatus("Scheduled");
		jobUpdate.setJobName("startup-job");

		jobUpdateRepository.save(jobUpdate);

		System.out.println("Sample job saved at startup.");
	}
}



