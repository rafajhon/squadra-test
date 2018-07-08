package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.services.JobService;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.jar.JarException;

@RestController("/jobs")
public class JobController {

    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Job>listar(@RequestParam(value = "sortByWeight",required= false)
                                       Boolean sortByWeight){

        return jobService.getJobs(sortByWeight);
    }

    @PostMapping
    public ResponseEntity<String> addJob( @RequestBody Job job){
        try {
            jobService.validJob(job);
            jobService.addJob(job);
            return ResponseEntity.ok().body("created");
        }catch (JobException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
