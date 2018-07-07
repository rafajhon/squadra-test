package com.test.squadra.api.jobs.api.models;


import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.repositorys.JobRepository;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JobTest extends ApplicationTests {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    TaskRepository taskRepository;


    @Test
    @Transactional
    public void testModelJob(){
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        jobRepository.save(job);
        assertNotNull(jobRepository.findByName("teste"));
        jobRepository.delete(job);
    }

    @Test
    public void testRelationJobAndTask(){
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        jobRepository.save(job);
        Task task = new Task();
        task.name = "tesk teste";
        task.completed = true;
        taskRepository.save(task);
        job.tasks.add(task);
        jobRepository.save(job);
        job = null;
        job = jobRepository.findByName("teste");
        assertNotNull(job.tasks);
        jobRepository.delete(job);

    }


    @Test
    public void TestRalationJobAndParentJob(){
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        jobRepository.save(job);
        Job parentjob = new Job();
        parentjob.active = true;
        parentjob.name = "patentJob teste";
        jobRepository.save(parentjob);
        job.parentJob = parentjob;
        jobRepository.save(job);
        job = null;
        job = jobRepository.findByName("teste");
        assertNotNull(job.tasks);
        assertEquals(job.parentJob.id, parentjob.id);
        assertEquals(job.parentJob.name, parentjob.name);
        jobRepository.delete(job);
        jobRepository.delete(parentjob);
    }
}