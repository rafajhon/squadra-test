package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.services.TaskService;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import com.test.squadra.api.jobs.api.validation.TaskValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;
    private final TaskValidate taskValidate;

    @Autowired
    public TaskController(TaskService taskService, TaskValidate taskValidate) {
        this.taskService = taskService;
        this.taskValidate = taskValidate;
    }

    @GetMapping(value = "/tasks",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Task> listar(@RequestParam(value = "createdAt",required= false)String
                                         createdAt){
        return taskService.getTasks(createdAt);
    }

    @PostMapping(value = "/tasks",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> addTask(@RequestBody Task task) throws TaskExeception {
        try {
            taskValidate.validTask(task);
            taskService.addTask(task);
            return ResponseEntity.ok().body("created");
        }catch (TaskExeception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping( value= "/tasks/{taskId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task getTask(@PathVariable(value = "taskId") String taskId) throws TaskExeception {
        try {
            return taskService.getTaskById(Long.valueOf(taskId));
        }catch (TaskExeception e){
            return null;
        }
    }

}
