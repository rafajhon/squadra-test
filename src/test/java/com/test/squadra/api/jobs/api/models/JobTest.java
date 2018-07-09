package com.test.squadra.api.jobs.api.models;


import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;
import com.test.squadra.api.jobs.api.services.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JobTest extends ApplicationTests {
    @Autowired
    JobService jobService;

    @Autowired
    TaskRepository taskRepository;


    @Test
    public void testModelJob(){
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        job.id = Long.valueOf(1);
        jobService.saveJob(job);
        assertNotNull(jobService.getJobByName("teste"));
    }

    @Test
    public void testRelationJobAndTask(){
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        job.id = Long.valueOf(1);
        jobService.saveJob(job);
        Task task = new Task();
        task.id = Long.valueOf(1);
        task.name = "tesk teste";
        task.completed = true;
        task.weight = 2;
        task.createdAt = LocalDate.now();
        taskRepository.save(task);
        job.tasks.add(task);
        jobService.saveJob(job);
        job = null;
        job = jobService.getJobByName("teste");
        assertNotNull(job.tasks);

    }


    @Test
    public void TestRalationJobAndParentJob(){
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        job.id = Long.valueOf(1);
        jobService.saveJob(job);
        Job parentjob = new Job();
        parentjob.active = true;
        parentjob.name = "patentJob teste";
        parentjob.id = Long.valueOf(2);
        jobService.saveJob(parentjob);
        job.parentJob = parentjob;
        jobService.saveJob(job);
        job = null;
        job = jobService.getJobByName("teste");
        assertNotNull(job.tasks);
        assertEquals(job.parentJob.id, parentjob.id);
        assertEquals(job.parentJob.name, parentjob.name);
    }
}