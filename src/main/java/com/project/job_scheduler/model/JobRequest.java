package com.project.job_scheduler.model;

public class JobRequest {
        private String name;
        private long delayInMillis;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getDelayInMillis() {
            return delayInMillis;
        }

        public void setDelayInMillis(long delayInMillis) {
            this.delayInMillis = delayInMillis;
        }
    }


