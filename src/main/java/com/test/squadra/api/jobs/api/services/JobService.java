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
}
