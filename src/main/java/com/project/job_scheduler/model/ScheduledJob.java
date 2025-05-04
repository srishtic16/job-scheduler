package com.project.job_scheduler.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScheduledJob implements Comparable<ScheduledJob>, Runnable {

    public enum JobStatus {
        PENDING,
        RUNNING,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    private final UUID id;
    private final String name;
    private final long executeAt;
    private JobStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;
    private Runnable task;

    public ScheduledJob(String name, long executeAt, Runnable task) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.executeAt = executeAt;
        this.task = task;
        this.status = JobStatus.PENDING;
    }
    public ScheduledJob(String name, long executeAt) {
        this.name = name;
        this.executeAt = executeAt;
    }


    @Override
    public void run() {
        this.status = JobStatus.RUNNING;
        this.startTime = LocalDateTime.now();

        try {
            if (task != null) {
                task.run(); // Custom task
            } else {
                // fallback/default task
                Thread.sleep(1000);
            }
            this.status = JobStatus.COMPLETED;
        } catch (Exception e) {
            this.status = JobStatus.FAILED;
            this.errorMessage = e.getMessage();
        }

        this.endTime = LocalDateTime.now();
    }

    @Override
    public int compareTo(ScheduledJob other) {
        return Long.compare(this.executeAt, other.executeAt);
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public long getExecuteAt() { return executeAt; }
    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public Runnable getTask() { return task; }
    public void setTask(Runnable task) { this.task = task; }
}
