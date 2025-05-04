package com.project.job_scheduler.service;

import com.project.job_scheduler.model.JobUpdate;
import com.project.job_scheduler.repository.JobUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JobUpdateService {


        @Autowired
        private JobUpdateRepository jobUpdateRepository;

        public void saveJobUpdate(String jobName, String status) {
            JobUpdate update = new JobUpdate();
            update.setJobName(jobName);
            update.setStatus(status);
            update.setUpdatedAt(LocalDateTime.now());
            jobUpdateRepository.save(update);
        }

        public List<JobUpdate> getUpdates(String jobName) {
            return jobUpdateRepository.findByJobName(jobName);
        }
    }

