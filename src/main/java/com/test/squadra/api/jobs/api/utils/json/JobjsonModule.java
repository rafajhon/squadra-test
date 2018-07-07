package com.test.squadra.api.jobs.api.utils.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.test.squadra.api.jobs.api.models.Job;
import org.springframework.stereotype.Service;

@Service
public class JobjsonModule extends SimpleModule {
    public JobjsonModule(){
        this.addSerializer(Job.class,new JobSerialize());
    }
}
