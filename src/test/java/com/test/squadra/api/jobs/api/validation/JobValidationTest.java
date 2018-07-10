package com.test.squadra.api.jobs.api.validation;

import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.utils.exceptions.JobException;
import org.junit.jupiter.api.Test;

import static com.test.squadra.api.jobs.api.validation.JobValidation.validJob;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JobValidationTest {

    @Test
    void testValidjobNull() {
        Job job = null;
        JobException jobException = assertThrows(JobException.class,()->{
            validJob(job);
        });
        assertEquals("job null",jobException.getMessage());

    }

    @Test
    void testValidjobNameNull() {
        Job job = new Job();
        job.id = Long.valueOf(1);
        JobException jobException = assertThrows(JobException.class,()->{
            validJob(job);
        });
        assertEquals("name required",jobException.getMessage());

    }
    @Test
    void testValidjobActiveNull() {
        Job job = new Job();
        job.id = Long.valueOf(1);
        job.name = "job 1";
        JobException jobException = assertThrows(JobException.class,()->{
            validJob(job);
        });
        assertEquals("active required",jobException.getMessage());

    }
}