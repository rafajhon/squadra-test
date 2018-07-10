package com.test.squadra.api.jobs.api.validation;

import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.services.TaskService;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskValidate {
    private final TaskService taskService;

    @Autowired
    public TaskValidate(TaskService taskService) {
        this.taskService = taskService;
    }

    public void validTask(Task task) throws TaskExeception {
        if(taskService.existById(task.id)){
            throw  new TaskExeception("task exist");
        }
    }
}
