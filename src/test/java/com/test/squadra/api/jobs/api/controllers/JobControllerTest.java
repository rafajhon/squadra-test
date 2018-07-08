package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.UtilsTest;
import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.repositorys.JobRepository;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;
import com.test.squadra.api.jobs.api.services.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static com.test.squadra.api.jobs.api.utils.Date.LocalDateUtils.stringToLocalDate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JobControllerTest extends ApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    UtilsTest utilsTest;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testListarJob() throws Exception {
     mockMvc.perform(get("/jobs")).andExpect(status().isOk())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect
             (content().json("[]"));
    }
    @Test
    public void testListarJobBysortByWeight() throws Exception {
        Job firstJob = utilsTest.criaJob("job 1", 1);
        Job secondJob = utilsTest.criaJob("job 2", 2);
        Job threeJob = utilsTest.criaJob("job 3", 3);
        mockMvc.perform(get("/jobs").param("sortByWeight", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
                .andExpect(content().string("["+utilsTest.getJsonJobCompare(threeJob)+"," +
                        ""+utilsTest.getJsonJobCompare
                        (secondJob)+","+utilsTest.getJsonJobCompare(firstJob)+"]"));
    }

    @Test
    public void testAddJob() throws Exception {
        Job job = new Job();
        job.id = Long.valueOf(1);
        job.name = "Job1";
        job.active = true;
        Task task = new Task();
        task.id = Long.valueOf(1);
        task.name = "First task";
        task.weight = 5;
        task.completed = true;
        task.createdAt = stringToLocalDate("2018-07-07");
        job.tasks.add(task);
        Job parentJob = new Job();
        parentJob.id = Long.valueOf(2);
        parentJob.name = "Second job";
        parentJob.active = false;
        job.parentJob = parentJob;
        mockMvc.perform(post("/jobs").header("Content-Type",
                "application/json").content(utilsTest.getJsonJobCompare(job)))
                .andExpect(status().isOk()).andExpect(content().string("created"));
    }

    @Test
    public void testGetJob() throws Exception {
        Job job = utilsTest.criaJob("job 1", 1);
        mockMvc.perform(get("/jobs/"+job.id))
                .andExpect(status().isOk()).andDo(print()).andExpect(content().json(utilsTest
                .getJsonJobCompare
                (job)));
    }
    @Test
    public void testGetJobIdNoneExist() throws Exception {
        mockMvc.perform(get("/jobs/"+1))
                .andExpect(status().isOk()).andDo(print()).andExpect(content().string(""));
    }

    @Test
    public void testDeletedJob() throws Exception {
        Job job = utilsTest.criaJob("job 1", 1);
        assertNotNull(utilsTest.jobService.findJobByName("job 1"));
        mockMvc.perform(delete("/jobs/"+job.id))
                .andExpect(status().isOk()).andDo(print()).andExpect(content().string("deleted"));
        assertNull(utilsTest.jobService.findJobByName("job 1"));
    }
    @Test
    public void testDeletedJobIdNoneExist() throws Exception {
        mockMvc.perform(delete("/jobs/"+1))
                .andExpect(status().isBadRequest()).andDo(print()).andExpect(content().string("No class com.test.squadra.api.jobs.api.models.Job entity with id 1 exists!"));
    }


}