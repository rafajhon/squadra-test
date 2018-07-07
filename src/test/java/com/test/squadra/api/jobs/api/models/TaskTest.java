package com.test.squadra.api.jobs.api.models;

import com.test.squadra.api.jobs.api.ApplicationTests;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

public class TaskTest extends ApplicationTests {

    @Autowired
    TaskRepository taskRepository;

    @Test
    @Transactional
    public void testModelTest(){
        Task task = new Task();
        task.name = "tesk teste";
        task.completed = true;
        task.createdAt = LocalDate.now();
        taskRepository.save(task);
        Assert.assertNotNull(taskRepository.findAll());
    }

}
