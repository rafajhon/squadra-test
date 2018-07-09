package com.test.squadra.api.jobs.api.validation;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import org.springframework.stereotype.Component;

@Component
public class JobValidation {
    public void validJob(Job job) throws JobException {
        if (job == null) {
            throw new JobException("job NUll");
        }
    }
}
