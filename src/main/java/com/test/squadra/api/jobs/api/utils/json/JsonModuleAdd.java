package com.test.squadra.api.jobs.api.utils.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;
import org.springframework.stereotype.Service;

@Service
public class JsonModuleAdd extends SimpleModule {
    public JsonModuleAdd(){
        this.addSerializer(Job.class,new JobSerialize());
        this.addSerializer(Task.class,new TaksSerialize());
    }

}
