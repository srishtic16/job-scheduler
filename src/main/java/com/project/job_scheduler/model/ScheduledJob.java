package com.project.job_scheduler.model;

import java.util.UUID;

public class ScheduledJob implements Runnable, Comparable<ScheduledJob> {

        private final UUID id;
        private final String name;
        private final long executeAt;
        private volatile JobStatus status = JobStatus.PENDING;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public enum JobStatus {
            PENDING, RUNNING, COMPLETED, CANCELLED
        }



    public ScheduledJob(String name, long executeAt) {
            this.id = UUID.randomUUID();
            this.name = name;
            this.executeAt = executeAt;
        }
    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public JobStatus getStatus(){
        return status;
    }



    public long getExecuteAt() {
            return executeAt;
        }



        @Override
        public void run() {
            status = JobStatus.RUNNING;
            System.out.println("Executing job: " + name + " (ID: " + id + ")");
            try {
                // Simulate work
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            status = JobStatus.COMPLETED;
        }

        @Override
        public int compareTo(ScheduledJob other) {
            return Long.compare(this.executeAt, other.executeAt);
        }
    }

