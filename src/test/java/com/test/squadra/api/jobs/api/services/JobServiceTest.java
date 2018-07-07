package com.test.squadra.api.jobs.api.services;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.models.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class JobServiceTest extends ApplicationTests {

    @Autowired
    JobService jobService;


    @Test
    void listAllJobs() {
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        jobService.jobRepository.save(job);
        assertEquals(1,jobService.listAllJobs().size());
    }
}