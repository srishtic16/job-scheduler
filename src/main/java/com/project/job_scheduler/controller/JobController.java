package com.project.job_scheduler.controller;

import com.project.job_scheduler.model.JobRequest;
import com.project.job_scheduler.model.ScheduledJob;
import com.project.job_scheduler.service.JobSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/scheduler")
public class JobController {

    @Autowired
    private JobSchedulerService jobSchedulerService;

    @PostMapping
    public String scheduleJob(@RequestBody JobRequest jobRequest){
        UUID uuid = jobSchedulerService.scheduleJob(jobRequest);
        return "Job scheduled: " + jobRequest.getName()+ uuid;
    }
    @GetMapping("/{id}")
    public ScheduledJob getJobStatus(@PathVariable UUID id) {
        ScheduledJob job = jobSchedulerService.getJob(id);
        if (job == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        return job;
    }

    @DeleteMapping("/{id}")
    public String cancelJob(@PathVariable UUID id){
        boolean cancelled = jobSchedulerService.cancelJob(id);

        if (cancelled) {
            return "Job with ID " + id + " was cancelled successfully.";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Unable to cancel job. It may not exist or is already running/completed.");
        }
    }

    @PostMapping("/{id}/trigger")
    public String triggerJobNow(@PathVariable UUID id) {
        boolean triggered = jobSchedulerService.triggerJobNow(id);
        if (triggered) {
            return "Job triggered immediately: " + id;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found or cannot be triggered.");
        }
    }


}
