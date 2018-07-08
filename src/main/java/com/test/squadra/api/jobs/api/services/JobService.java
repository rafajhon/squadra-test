package com.test.squadra.api.jobs.api.services;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.repositorys.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    public  final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
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
        job.tasks.forEach(task -> job.weightJob +=task.weight );
        jobRepository.save(job);
        return job;
    }

    public Job findJobByName(String name) {
        return jobRepository.findByName(name);
    }
}
