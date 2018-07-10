package com.test.squadra.api.jobs.api.validation;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;


public class JobValidation {
    public static void validJob(Job job) throws JobException {
        if (job == null) {
            throw new JobException("job null");
        }
        if(job.id == null ){
            throw  new JobException("id required");
        }
        if(job.name == null || job.name.isEmpty()){
            throw new JobException("name required");
        }
        if(job.active == null){
            throw new JobException("active required");
        }
    }
}
