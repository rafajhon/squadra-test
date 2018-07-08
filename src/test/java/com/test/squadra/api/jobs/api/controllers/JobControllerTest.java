package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.ApplicationTests;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JobControllerTest extends ApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JobService jobService;

    @Autowired
    TaskRepository taskRepository;

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
        Job firstJob = criaJob("job 1", 1);
        Job secondJob = criaJob("job 2", 2);
        Job threeJob = criaJob("job 3", 3);
        mockMvc.perform(get("/jobs").param("sortByWeight", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
                .andExpect(content().string("["+getJsonJobCompare(threeJob)+","+getJsonJobCompare
                        (secondJob)+","+getJsonJobCompare(firstJob)+"]"));
    }

    private String getJsonJobCompare(Job job) {
        return "{" +
                    "\"id\":"+job.id+"," +
                    "\"name\":\""+job.name+"\"," +
                    "\"active\":"+job.active+"," +
                     getJsonParentJobCompare(job.parentJob) +
                    "\"tasks\":["+ getJsonTaksCompare(job.tasks.get(0))+"]" +
                "}";
    }

    private  String getJsonParentJobCompare(Job parentJob){
        if(parentJob != null){
            return "\"parentJob\":{" +
                    "\"id\":"+parentJob.id+"," +
                    "\"name\":\""+parentJob.name+"\"," +
                    "\"active\":"+parentJob.active+"" +
                    "},";
        }
        return "";
    }

    private String getJsonTaksCompare(Task task){
        return "{" +
                    "\"id\":"+task.id+"," +
                    "\"name\":\""+task.name+"\"," +
                    "\"weight\":"+task.weight+"," +
                    "\"completed\":"+task.completed+"," +
                    "\"createdAt\":\""+task.createdAt+"\"" +
                "}";
    }


    private Job criaJob(String name, int weightTash) {
        Job job = new Job();
        job.name = name;
        job.active = true;
        job.tasks.add(criaTask(weightTash));
        jobService.saveJob(job);
        return job;

    }

    private Task criaTask(int weightTash) {
        Task task = new Task();
        task.name = "First task";
        task.weight = weightTash;
        task.completed = true;
        task.createdAt = LocalDate.now();
        taskRepository.save(task);
        return task;
    }
}