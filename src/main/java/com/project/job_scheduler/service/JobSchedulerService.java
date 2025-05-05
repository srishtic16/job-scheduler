package com.project.job_scheduler.service;

import com.project.job_scheduler.model.JobRequest;
import com.project.job_scheduler.model.ScheduledJob;
import com.project.job_scheduler.model.ScheduledJobEntity;
import com.project.job_scheduler.repository.JobUpdateRepository;
import com.project.job_scheduler.repository.ScheduledJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.UUID;

@Service
public class JobSchedulerService {

        private final PriorityBlockingQueue<ScheduledJob> jobQueue;
        private final ExecutorService executor;
        private final Thread schedulerThread;
        private final Map<UUID, ScheduledJob> jobMap = new ConcurrentHashMap<>();

        @Autowired
        private ScheduledJobRepository scheduledJobRepository;

        public JobSchedulerService() {
            jobQueue = new PriorityBlockingQueue<>();
            executor = Executors.newFixedThreadPool(10); // Thread pool of 10 workers

            // Start the background scheduler thread
            schedulerThread = new Thread(this::startScheduler);
            schedulerThread.setDaemon(true); // Keep running in the background
            schedulerThread.start();
        }

    public UUID scheduleJob(JobRequest jobRequest) {
        long executeAt = System.currentTimeMillis() + jobRequest.getDelayInMillis();
        LocalDateTime scheduledTime = LocalDateTime.ofInstant(
                Instant.now().plusMillis(jobRequest.getDelayInMillis()),
                ZoneId.systemDefault());


        ScheduledJobEntity entity = new ScheduledJobEntity(
                jobRequest.getName(),
                scheduledTime,
                ScheduledJob.JobStatus.PENDING
        );

         entity = scheduledJobRepository.save(entity);
        Runnable task = () -> {
            System.out.println("Executing job: " + jobRequest.getName());
            // Simulated business logic
            try {
                Thread.sleep(2000);
                System.out.println("Completed job: " + jobRequest.getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        ScheduledJob job = new ScheduledJob(jobRequest.getName(), executeAt );

        jobQueue.put(job); // Add to queue
        jobMap.put(job.getId(), job); // Add to job map

        System.out.println("Job scheduled: " + jobRequest.getName() + " (ID: " + job.getId() + ")");
        return job.getId();
    }
    public ScheduledJob getJob(UUID id) {
        return jobMap.get(id);
    }

    private void startScheduler() {
            while (true) {
                try {
                    ScheduledJob job = jobQueue.peek();
                    if (job != null && job.getExecuteAt() <= System.currentTimeMillis()) {
                        jobQueue.poll(); // Remove the job from the queue
                        executor.submit(job); // Submit the job to the executor for execution
                    } else {
                        Thread.sleep(100); // Sleep for a while and check again
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    public boolean cancelJob(UUID id) {

            ScheduledJob job = jobMap.get(id);
        if (job != null && job.getStatus() == ScheduledJob.JobStatus.PENDING) {
            // Remove from the queue
            boolean removed = jobQueue.remove(job);

            if (removed) {
                job.setStatus(ScheduledJob.JobStatus.CANCELLED);
                System.out.println("Cancelled job: " + job.getName() + " (ID: " + job.getId() + ")");
                return true;
            }
        }

        return false;
    }

    public boolean triggerJobNow(UUID id) {
        ScheduledJob job = jobMap.get(id);
        if (job == null) return false;

        if (job.getStatus() == ScheduledJob.JobStatus.PENDING) {
            jobQueue.remove(job);
        }

        executor.submit(job); // Run immediately
        return true;
    }

}


