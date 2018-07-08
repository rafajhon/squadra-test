package com.test.squadra.api.jobs.api;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;
import com.test.squadra.api.jobs.api.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UtilsTest {

    TaskRepository taskRepository;
    JobService jobService;

    @Autowired
    public UtilsTest(TaskRepository taskRepository, JobService jobService) {
        this.taskRepository = taskRepository;
        this.jobService = jobService;

    }

    public String getJsonJobCompare(Job job) {
        return "{" +
                "\"id\":"+job.id+"," +
                "\"name\":\""+job.name+"\"," +
                "\"active\":"+job.active+"," +
                getJsonParentJobCompare(job.parentJob) +
                "\"tasks\":["+ getJsonTaksCompare(job.tasks.get(0))+"]" +
                "}";
    }

    private  String getJsonParentJobCompare(Job parentJob){
        if(parentJob != null){
            return "\"parentJob\":{" +
                    "\"id\":"+parentJob.id+"," +
                    "\"name\":\""+parentJob.name+"\"," +
                    "\"active\":"+parentJob.active+"" +
                    "},";
        }
        return "";
    }

    private String getJsonTaksCompare(Task task){
        return "{" +
                "\"id\":"+task.id+"," +
                "\"name\":\""+task.name+"\"," +
                "\"weight\":"+task.weight+"," +
                "\"completed\":"+task.completed+"," +
                "\"createdAt\":\""+task.createdAt+"\"" +
                "}";
    }


    public Job criaJob(String name, int weightTash) {
        Job job = new Job();
        job.name = name;
        job.active = true;
        job.id = Long.valueOf(weightTash);
        job.tasks.add(criaTask(weightTash));
        jobService.saveJob(job);
        return job;

    }

    private Task criaTask(int weightTash) {
        Task task = new Task();
        task.name = "First task";
        task.weight = weightTash;
        task.completed = true;
        task.createdAt = LocalDate.now();
        task.id = Long.valueOf(weightTash);
        taskRepository.save(task);
        return task;
    }
}
