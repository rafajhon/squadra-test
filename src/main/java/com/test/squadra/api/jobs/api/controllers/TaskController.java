package com.test.squadra.api.jobs.api.controllers;

import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.services.JobService;
import com.test.squadra.api.jobs.api.services.TaskService;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import com.test.squadra.api.jobs.api.validation.TaskValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.test.squadra.api.jobs.api.validation.TaskValidate.validTask;

@RestController
public class TaskController {
    private final TaskService taskService;
    private final JobService jobService;

    @Autowired
    public TaskController(TaskService taskService, JobService jobService) {
        this.taskService = taskService;
        this.jobService = jobService;
    }

    @GetMapping(value = "/tasks",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Task> listar(@RequestParam(value = "createdAt",required= false)String
                                         createdAt){
        return taskService.getTasks(createdAt);
    }

    @PostMapping(value = "/tasks",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> addTask(@RequestBody Task task) throws TaskExeception {
        try {
            validTask(task);
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
    @DeleteMapping( value= "/tasks/{taskId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteTask(@PathVariable(value = "taskId") String taskId) throws
    TaskExeception {
        try {
            Task task = taskService.getTaskById(Long.valueOf(taskId));
            jobService.deleteRefenceTaksOnjob(task);
            taskService.deleteTask(task);
            return ResponseEntity.ok().body("deleted");
        }catch (TaskExeception e){
            return null;
        }
    }
    @PutMapping( value= "/tasks/{taskId}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE )
    public ResponseEntity<String> atulizaJob(@PathVariable(value = "taskId") String taskId,
                                             @RequestBody Task task)  {
        try {
            validTask(task);
            taskService.updateTask(task, Long.valueOf(taskId));
            return ResponseEntity.ok().body("update");
        }catch (TaskExeception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
