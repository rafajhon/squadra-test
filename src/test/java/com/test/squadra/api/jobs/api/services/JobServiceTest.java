package com.test.squadra.api.jobs.api.services;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.UtilsTest;
import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class JobServiceTest extends ApplicationTests {

    public final JobService jobService;
    public final UtilsTest utilsTest;
    public final TaskService taskService;

    @Autowired
    JobServiceTest(JobService jobService, UtilsTest utilsTest, TaskService taskService) {
        this.jobService = jobService;
        this.utilsTest = utilsTest;
        this.taskService = taskService;
    }


    @Test
    public void listAllJobs() {
        Job job = new Job();
        job.active = true;
        job.name = "teste";
        job.id = Long.valueOf(1);
        jobService.saveJob(job);
        assertEquals(1,jobService.listAllJobs().size());
    }

    @Test
    public void testDeleteTaksOnJob(){
        Job job = utilsTest.createJobPersist("job 1",1);
        job.tasks.add(utilsTest.createTaskPersist(3));
        job = jobService.saveJob(job);
        assertEquals(1,jobService.countJobs());
        assertEquals(2,taskService.countTasks());
        jobService.clearTasks(job);
        assertEquals(1,jobService.countJobs());
        assertEquals(0,taskService.countTasks());

    }

    @Test
    public void testAddParentJob() throws JobException {
        Job job = new Job();
        job.id = Long.valueOf(2);
        job.parentJob = utilsTest.createJobPersist("job Parent",1);
        jobService.addParentJob(job);
        assertNotNull(job.parentJob);
    }

    @Test
    public void testAddParentJobNotExist() {
        Job job = new Job();
        job.id = Long.valueOf(1);
        job.parentJob = new Job();
        job.parentJob.id = Long.valueOf(2);

        JobException jobException = assertThrows(JobException.class,()->{
            jobService.addParentJob(job);
        });
        assertEquals("parentJob not found", jobException.getMessage());
    }
    @Test
    public void testAddParentJobReferenceItself() {
        Job job = new Job();
        job.id = Long.valueOf(1);
        job.parentJob = new Job();
        job.parentJob.id = Long.valueOf(1);
        JobException jobException = assertThrows(JobException.class,()->{
            jobService.addParentJob(job);
        });
        assertEquals("can not reference itself", jobException.getMessage());
    }
    @Test
    public void testUpdadeJob() throws JobException, TaskExeception {
        int numAux = 1;
        Job job = utilsTest.createJobPersist("job 1", numAux);
        assertNotNull(jobService.getJobByName(job.name));
        job.name = "job altere";
        assertNull(jobService.getJobByName(job.name));
        jobService.updateJob(job, Long.valueOf(numAux));
        assertNotNull(jobService.getJobByName(job.name));

    }
    @Test
    public void deleteRefenceTaksOnjob() throws JobException {
        Job jobPersist = utilsTest.createJobPersist("job 1", 1);
        jobService.deleteRefenceTaksOnjob(jobPersist.tasks.get(0));
        jobPersist = jobService.getJobDb(jobPersist.id);
        assertEquals(0,jobPersist.tasks.size());
    }

    @Test
    void deleteJobChildren() {
        Job jobParent = utilsTest.createJobPersist("job parent",1);
        Job jobChildren = utilsTest.createJobPersist("job children",2);
        jobChildren.parentJob = jobParent;
        jobService.saveJob(jobParent);
        assertEquals(2, jobService.countJobs());
        jobService.deleteJob(jobChildren);
        assertEquals(1, jobService.countJobs());

    } @Test
    void deleteJobParent() {
        Job jobParent = utilsTest.createJobPersist("job parent",1);
        Job jobChildren = utilsTest.createJobPersist("job children",2);
        jobChildren.parentJob = jobParent;
        jobService.saveJob(jobChildren);
        assertEquals(2, jobService.countJobs());
        jobService.deleteJob(jobParent);
        assertEquals(0, jobService.countJobs());

    }
}