package com.test.squadra.api.jobs.api.services;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import com.test.squadra.api.jobs.api.repositorys.JobRepository;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import com.test.squadra.api.jobs.api.utils.exceptions.TaskExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    public final JobRepository jobRepository;

    public final TaskService taskService;

    @Autowired
    public JobService(JobRepository jobRepository, TaskService taskService) {
        this.jobRepository = jobRepository;
        this.taskService = taskService;
    }


    public Job getJobByName(String name) {
        return jobRepository.findByName(name);
    }

    public List<Job> listAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> getJobs(Boolean sortByWeight) {
        if (sortByWeight != null)
            return listAllSortWeight();
        return listAllJobs();
    }

    private List<Job> listAllSortWeight() {
        return jobRepository.findAllByOrderByWeightJobDesc();
    }

    public Job saveJob(Job job) {
        job.weightJob = 0;
        if (job.tasks != null) {
            job.tasks.forEach(task -> job.weightJob += task.weight);
        }
        jobRepository.save(job);
        return job;
    }

    public void addJob(Job job) throws JobException, TaskExeception {
        taskService.saveTaskOnJob(job);
        addParentJob(job);
        saveJob(job);
    }



    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public void updateJob(Job job, Long jobId) throws JobException, TaskExeception {
        Job jobDb = getJobDb(jobId);
        setJobInJobDB(job, jobDb);
        setTask(job,jobDb);
        addParentJob(jobDb);
        saveJob(jobDb);

    }

    public Job getJobDb(Long jobId) throws JobException {
        Optional<Job> jobDbOptional = jobRepository.findById(Long.valueOf(jobId));
        if (!jobDbOptional.isPresent()) {
            throw new JobException("Job not found");
        }
        return jobDbOptional.get();
    }

    public void setJobInJobDB(Job job, Job jobDb){
        jobDb.name = job.name;
        jobDb.active = job.active;
        jobDb.parentJob = job.parentJob;
    }

    private void setTask(Job job, Job jobDb) throws TaskExeception {
        clearTasks(jobDb);
        taskService.saveTaskOnJob(jobDb,job.tasks);
    }

    public void clearTasks(Job jobDb) {
        jobDb.tasks.clear();
        jobRepository.save(jobDb);
        taskService.deleteListTasks(jobDb.tasks);
    }

    public void addParentJob(Job job) throws JobException {
        if (job.parentJob != null) {
            if(job.parentJob.id.equals(job.id)){
                throw new JobException("can not reference itself");
            }
            Optional<Job> parentJob = jobRepository.findById(job.parentJob.id);
            if (!parentJob.isPresent()) {
                throw new JobException("parentJob not found");
            }
            job.parentJob = parentJob.get();
        }
    }

    public int countJobs() {
        return Math.toIntExact(jobRepository.count());
    }

    public void deleteRefenceTaksOnjob(Task task) {
        Job job = jobRepository.findByIn_Tasks(task.id);
        job.tasks.remove(task);
        jobRepository.saveAndFlush(job);

    }

}
