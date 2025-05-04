package com.project.job_scheduler.repository;

import com.project.job_scheduler.model.ScheduledJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduledJobRepository extends JpaRepository<ScheduledJobEntity, UUID> {

}
