package com.test.squadra.api.jobs.api.services;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.UtilsTest;
import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TaskServiceTest extends ApplicationTests {

    public final TaskService taskService;

    public final UtilsTest utilsTest;

    public final JobService jobService;


    @Autowired
    TaskServiceTest(TaskService taskService, UtilsTest utilsTest, JobService jobService) {
        this.taskService = taskService;
        this.utilsTest = utilsTest;
        this.jobService = jobService;
    }

    @Test
    public void testSaveTaskOnJob() {
        Job job = utilsTest.createJobNew("Job 1",1);
        List<Task> tasks = new ArrayList<>();
        tasks.add(utilsTest.createTaskPersist(1));
        tasks.add(utilsTest.createNewtask(2));
        tasks.add(utilsTest.createNewtask(1));
        assertEquals(1,taskService.countTasks());
        TaskExeception exception = assertThrows(TaskExeception.class, () -> {
            taskService.saveTaskOnJob(job,tasks);
        });
        assertEquals("task exists", exception.getMessage());
        assertEquals(1,taskService.countTasks());

    }

    @Test
    public void testDeleteListTaks() {
        List<Task>tasks = new ArrayList<>();
        tasks.add(utilsTest.createTaskPersist(1));
        tasks.add(utilsTest.createTaskPersist(2));
        assertEquals(2,taskService.countTasks());
        taskService.deleteListTasks(tasks);
        assertEquals(0,taskService.countTasks());
        assertEquals(0,tasks.size());
    }

    @Test
    public void getjobDb() throws JobException {
        utilsTest.createJobPersist("job test", 1);
        Job  jobB = jobService.getJobDb(Long.valueOf(1));
        assertNotNull(jobB);
    }

    @Test
    public void getjobDbNotExist(){
        JobException jobException = assertThrows(JobException.class,()->{
            jobService.getJobDb(Long.valueOf(1));
        });
        assertEquals("Job not found",jobException.getMessage());
    }
}