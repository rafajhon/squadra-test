package com.test.squadra.api.jobs.api.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.UtilsTest;
import com.test.squadra.api.jobs.api.models.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskJobSerializeTest extends ApplicationTests {
    @Autowired
    private UtilsTest utilsTest;

    @Test
    public void testSereializeTaks() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JsonModuleAdd());
        Task task = new Task();
        task.id = Long.valueOf(1);
        task.name = "First task";
        task.weight = 5;
        task.completed = true;
        task.createdAt = LocalDate.now();
        String json = objectMapper.writeValueAsString(task);
        assertEquals(utilsTest.getJsonTaksCompare(task),json);
    }
}
