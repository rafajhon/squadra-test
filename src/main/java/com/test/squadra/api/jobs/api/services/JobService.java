package com.test.squadra.api.jobs.api.services;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.repositorys.JobRepository;
import com.test.squadra.api.jobs.api.repositorys.TaskRepository;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    public  final JobRepository jobRepository;

    public final TaskRepository taskRepository;

    @Autowired
    public JobService(JobRepository jobRepository, TaskRepository taskRepository) {
        this.jobRepository = jobRepository;
        this.taskRepository = taskRepository;
    }



    public List<Job> listAllJobs(){
        return jobRepository.findAll();
    }

    public List<Job> getJobs(Boolean sortByWeight) {
        if(sortByWeight != null)
            return listAllSortWeight();
        return listAllJobs();
    }

    private List<Job> listAllSortWeight() {
        return jobRepository.findAllByOrderByWeightJobDesc();
    }

    public Job saveJob(Job job){
        job.weightJob = 0;
        if(job.tasks != null){
            job.tasks.forEach(task -> job.weightJob+=task.weight );
        }
        jobRepository.save(job);
        return job;
    }

    public Job findJobByName(String name) {
        return jobRepository.findByName(name);
    }

    public void validJob(Job job) throws JobException {
        if(job == null){
            throw new JobException("job NUll");
        }
    }

    public void addJob(Job job) {
        if(job.tasks != null){
            job.tasks.forEach(task ->taskRepository.save(task));
        }
        if(job.parentJob != null){
            Job parentJob = jobRepository.findByName(job.parentJob.name);
            if(parentJob != null) {
                job.parentJob = parentJob;
            }else
                job.parentJob = jobRepository.save(job.parentJob);
        }
        saveJob(job);
    }

    public Job getJobsById(Long alertaId) {
        Optional<Job> jobOptional = jobRepository.findById(alertaId);
        if(jobOptional.isPresent())
            return jobOptional.get();
        return null;
    }

    public void deleteJob(Long id) {
       jobRepository.deleteById(id);
    }
}
