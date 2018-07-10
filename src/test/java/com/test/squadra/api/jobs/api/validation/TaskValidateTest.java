package com.test.squadra.api.jobs.api.validation;

import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import org.junit.jupiter.api.Test;

import static com.test.squadra.api.jobs.api.validation.TaskValidate.validTask;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskValidateTest  {

    @Test
    void testValidjobNull() {
        Task task = null;
        TaskExeception jobException = assertThrows(TaskExeception.class,()->{
            validTask(task);
        });
        assertEquals("task null",jobException.getMessage());

    }

    @Test
    void testValidjobNameNull() {
        Task task = new Task();
        task.id = Long.valueOf(1);
        TaskExeception jobException = assertThrows(TaskExeception.class,()->{
            validTask(task);
        });
        assertEquals("name required",jobException.getMessage());

    }
    @Test
    void testValidjobActiveNull() {
        Task task = new Task();
        task.id = Long.valueOf(1);
        task.name = "task 1";
        TaskExeception jobException = assertThrows(TaskExeception.class,()->{
            validTask(task);
        });
        assertEquals("weight required",jobException.getMessage());

    }

}