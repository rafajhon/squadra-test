package com.test.squadra.api.jobs.api.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import org.junit.jupiter.api.Test;

import static com.test.squadra.api.jobs.api.utils.Date.LocalDateUtils.stringToLocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JobSerializeTest extends ApplicationTests {

    @Test
    void serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonModuleAdd());
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
        String json = objectMapper.writeValueAsString(job);
        assertEquals(
                "{" +
                        "\"id\":1," +
                        "\"name\":\"Job1\"," +
                        "\"active\":true," +
                        "\"parentJob\":{" +
                        "\"id\":2," +
                        "\"name\":\"Second job\"," +
                        "\"active\":false" +
                        "}," +
                        "\"tasks\":[" +
                        "{" +
                        "\"id\":1," +
                        "\"name\":\"First task\"," +
                        "\"weight\":5," +
                        "\"completed\":true," +
                        "\"createdAt\":\"2018-07-07\"" +
                        "}" +
                        "]" +
                        "}",
                json);

    }
    @Test
    void serializeParentJobNull() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonModuleAdd());
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
        String json = objectMapper.writeValueAsString(job);
        assertEquals(
                "{" +
                        "\"id\":1," +
                        "\"name\":\"Job1\"," +
                        "\"active\":true," +
                        "\"tasks\":[" +
                        "{" +
                        "\"id\":1," +
                        "\"name\":\"First task\"," +
                        "\"weight\":5," +
                        "\"completed\":true," +
                        "\"createdAt\":\"2018-07-07\"" +
                        "}" +
                        "]" +
                        "}",
                json);

    }

    @Test
    void serializeTaskEmpty() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonModuleAdd());
        Job job = new Job();
        job.id = Long.valueOf(1);
        job.name = "Job1";
        job.active = true;
        Job parentJob = new Job();
        parentJob.id = Long.valueOf(2);
        parentJob.name = "Second job";
        parentJob.active = false;
        job.parentJob = parentJob;
        String json = objectMapper.writeValueAsString(job);
        assertEquals(
                "{" +
                        "\"id\":1," +
                        "\"name\":\"Job1\"," +
                        "\"active\":true," +
                        "\"parentJob\":{" +
                        "\"id\":2," +
                        "\"name\":\"Second job\"," +
                        "\"active\":false" +
                        "}," +
                        "\"tasks\":[]" +
                        "}",
                json);

    }
}