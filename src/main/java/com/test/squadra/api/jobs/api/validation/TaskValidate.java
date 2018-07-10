package com.test.squadra.api.jobs.api.validation;

import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;

public class TaskValidate {
    public static void validTask(Task task) throws TaskExeception {
        if (task == null) {
            throw new TaskExeception("task null");
        }
        if(task.id == null ){
            throw  new TaskExeception("id required");
        }
        if(task.name == null || task.name.isEmpty()){
            throw new TaskExeception("name required");
        }
        if(task.weight == null){
            throw new TaskExeception("weight required");
        }

    }
}
