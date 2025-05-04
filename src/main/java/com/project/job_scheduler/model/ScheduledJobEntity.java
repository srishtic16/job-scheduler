package com.project.job_scheduler.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

    @Entity
    @Table(name = "scheduled_jobs")
    public class ScheduledJobEntity {

        @Id
        @GeneratedValue
        private UUID id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private LocalDateTime scheduledTime;

        @Enumerated(EnumType.STRING)
        private JobStatus status;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private String errorMessage;

        public ScheduledJobEntity(String name, LocalDateTime executeAt, ScheduledJob.JobStatus jobStatus) {
            this.name=name;
            this.startTime = executeAt;

        }

        public enum JobStatus {
            PENDING,
            RUNNING,
            COMPLETED,
            FAILED,
            CANCELLED
        }

        // Constructors


        public ScheduledJobEntity(String name, LocalDateTime scheduledTime, JobStatus status) {
            this.name = name;
            this.scheduledTime = scheduledTime;
            this.status = status;
        }

        // Getters and Setters
        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getScheduledTime() {
            return scheduledTime;
        }

        public void setScheduledTime(LocalDateTime scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        public JobStatus getStatus() {
            return status;
        }

        public void setStatus(JobStatus status) {
            this.status = status;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }


}
