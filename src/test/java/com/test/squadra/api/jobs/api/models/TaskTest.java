package com.test.squadra.api.jobs.api.models;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskTest extends ApplicationTests {

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void testModelTest(){
        Task task = new Task();
        task.name = "tesk teste";
        task.completed = true;
        task.createdAt = LocalDate.now();
        taskRepository.save(task);
        assertNotNull(taskRepository.findAll());
    }

}
