package com.test.squadra.api.jobs.api.services;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.repositorys.JobRepository;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.test.squadra.api.jobs.api.utils.Date.LocalDateUtils.stringToLocalDate;

@Service
public class TaskService {

    public final TaskRepository taskRepository;
    public final JobRepository jobRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, JobRepository jobRepository) {
        this.taskRepository = taskRepository;
        this.jobRepository = jobRepository;
    }


    public void saveTaskOnJob(Job job) throws TaskExeception {
        List<Task> tasks = job.tasks;
        job.tasks = new ArrayList<>();
        saveTaskOnJob(job,tasks);
    }

    @Transactional(rollbackFor = TaskExeception.class)
    public void saveTaskOnJob(Job job,List<Task> tasks) throws TaskExeception {
        if (tasks != null) {
            for (Task task : tasks) {
                if(taskRepository.existsById(task.id)){
                    throw new TaskExeception("task exists");
                }
                job.tasks.add(taskRepository.save(task));
            }
        }

    }

    public void deleteListTasks(List<Task>tasks) {
        if (tasks != null) {
            for (Task task : tasks) {
                taskRepository.deleteById(task.id);
            }
            tasks.clear();
        }
        taskRepository.flush();
    }


    public int countTasks() {
        return Math.toIntExact(taskRepository.count());
    }

    public List<Task> getTasks(String createdAt) {
        if(createdAt != null && !createdAt.isEmpty()){
            LocalDate localDate = stringToLocalDate(createdAt);
            return taskRepository.findByCreatedAt(localDate);
        }
        return taskRepository.findAll();
    }

    public Task saveTask(Task taskPersist) {
        return taskRepository.save(taskPersist);
    }
}
