package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.services.JobService;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import com.test.squadra.api.jobs.api.validation.JobValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/jobs")
public class JobController {

    private final JobService jobService;
    private final JobValidation jobValidation;

    @Autowired
    public JobController(JobService jobService, JobValidation jobValidation) {
        this.jobService = jobService;
        this.jobValidation = jobValidation;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Job>listar(@RequestParam(value = "sortByWeight",required= false)Boolean sortByWeight){
        return jobService.getJobs(sortByWeight);
    }

    @PostMapping
    public ResponseEntity<String> addJob( @RequestBody Job job) throws TaskExeception {
        try {
            jobValidation.validJob(job);
            jobService.addJob(job);
            return ResponseEntity.ok().body("created");
        }catch (JobException|TaskExeception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping( value= "/jobs/{jobId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Job getjob(@PathVariable(value = "jobId") String jobId) throws JobException {
        try {
            return jobService.getJobDb(Long.valueOf(jobId));
        }catch (JobException e){
            return null;
        }

    }

    @DeleteMapping( value= "/jobs/{jobId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<String> Deletejob(@PathVariable(value = "jobId") String jobId){
        try {
            jobService.deleteJob(Long.valueOf(jobId));
            return ResponseEntity.ok().body("deleted");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping( value= "/jobs/{jobId}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<String> atulizaJob(@PathVariable(value = "jobId") String jobId,@RequestBody Job job)  {
        try {
            jobValidation.validJob(job);
            jobService.updateJob(job, Long.valueOf(jobId));
            return ResponseEntity.ok().body("update");
        }catch (JobException |TaskExeception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
