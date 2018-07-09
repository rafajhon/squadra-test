package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/tasks",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Task> listar(@RequestParam(value = "createdAt",required= false)String
                                         createdAt){
        return taskService.getTasks(createdAt);
    }
}
