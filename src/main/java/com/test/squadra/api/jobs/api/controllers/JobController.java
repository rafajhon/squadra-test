package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(value = "/jobs",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Job>listar(){
        jobService.listAllJobs();
        return jobService.listAllJobs();
    }
}
