package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.UtilsTest;
import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskControllerTest extends ApplicationTests {
    private MockMvc mockMvc;
    @Autowired
    private TaskService taskService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    UtilsTest utilsTest;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testListarTask() throws Exception {
        mockMvc.perform(get("/tasks")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect
                (content().json("[]"));
    }
    @Test
    public void testListarTaskjson() throws Exception {
        Task task =  utilsTest.createTaskPersist(2);
        mockMvc.perform(get("/tasks")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect
                (content().json("["+utilsTest.getJsonTaksCompare(task)+"]"));
    }
    @Test
    public void testListarTaskjsonByCreateAt() throws Exception {
        Task task =  utilsTest.createTaskPersist(1);
        Task taskPersist = utilsTest.createTaskPersist(2);
        taskPersist.createdAt = LocalDate.now().plusDays(2);
        taskService.saveTask(taskPersist);
        mockMvc.perform(get("/tasks").param("createdAt", LocalDate.now().toString())).andExpect(status()
                .isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect
                (content().json("["+utilsTest.getJsonTaksCompare(task)+"]"));

    }
    @Test
    public void testAddTask() throws Exception {
        Task task = utilsTest.createNewtask(1);
        mockMvc.perform(post("/tasks").header("Content-Type",
                "application/json").content(utilsTest.getJsonTaksCompare(task))).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string("created"));
    }
    @Test
    public void testAddTaskFall() throws Exception {
        Task task = utilsTest.createTaskPersist(1);
        mockMvc.perform(post("/tasks").header("Content-Type",
                "application/json").content(utilsTest.getJsonTaksCompare(task))).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string("task exist"));
    }

    @Test
    public void testGetTask() throws Exception {
        Task task = utilsTest.createTaskPersist( 1);
        mockMvc.perform(get("/tasks/"+task.id))
                .andExpect(status().isOk()).andDo(print()).andExpect(content().json(utilsTest
                .getJsonTaksCompare(task)));
    }

    @Test
    public void testGetTaskNull() throws Exception {
        mockMvc.perform(get("/tasks/"+1))
                .andExpect(status().isOk()).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testDeletTask() throws Exception {
        Job job = utilsTest.createJobPersist("job 1",1);
        assertEquals(1,taskService.countTasks());
        mockMvc.perform(delete("/tasks/"+1))
                .andExpect(status().isOk()).andDo(print()).andExpect(status().isOk()).andExpect
                (content().string("deleted"));
        assertEquals(0,taskService.countTasks());

    }

    @Test
    public void testPutTask() throws Exception {
        Task task = utilsTest.createTaskPersist(1);
        task.name = "task altered";
        mockMvc.perform(put("/tasks/"+1).header("Content-Type",
                "application/json").content(utilsTest.getJsonTaksCompare(task))).andDo
                (print()).andExpect(status().isOk()).andExpect(content().string("update"));
        task = taskService.getTaskById(Long.valueOf(1));
        assertEquals(task.name,"task altered");


    }

}
