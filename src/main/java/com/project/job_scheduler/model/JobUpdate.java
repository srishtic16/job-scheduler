package com.project.job_scheduler.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_info")
public class JobUpdate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long job_uuid;

        private String jobName;
        private String status;
        private LocalDateTime updatedAt;



        // Getters & setters

        public Long getUUId() {
                return job_uuid;
        }

        public void setUUId(Long uuid) {
                this.job_uuid = job_uuid;
        }

        public String getJobName() {
                return jobName;
        }

        public void setJobName(String jobName) {
                this.jobName = jobName;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }
}


