package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.repositorys.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JobControllerTest extends ApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JobRepository jobRepository;

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
}