package com.project.job_scheduler.repository;
import com.project.job_scheduler.model.JobUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobUpdateRepository extends JpaRepository<JobUpdate, Long> {
        List<JobUpdate> findByJobName(String jobName);


    }

