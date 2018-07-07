package com.test.squadra.api.jobs.api.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.test.squadra.api.jobs.api.models.Job;
import com.test.squadra.api.jobs.api.models.Task;

import java.io.IOException;

public class JobSerialize extends JsonSerializer<Job> {


    @Override
    public void serialize(Job job, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", job.id);
        jsonGenerator.writeStringField("name", job.name);
        jsonGenerator.writeBooleanField("active", job.active);
        if(job.parentJob != null){
            addParentJob(jsonGenerator,job.parentJob);
        }
        jsonGenerator.writeArrayFieldStart("tasks");
        for (Task task:job.tasks) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", task.id);
            jsonGenerator.writeStringField("name", task.name);
            jsonGenerator.writeNumberField("weight",task.weight);
            jsonGenerator.writeBooleanField("completed", task.completed);
            jsonGenerator.writeStringField("createdAt", task.createdAt.toString());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

    private void addParentJob(JsonGenerator jsonGenerator, Job parentJob) throws IOException {
        jsonGenerator.writeObjectFieldStart("parentJob");
        jsonGenerator.writeNumberField("id", parentJob.id);
        jsonGenerator.writeStringField("name", parentJob.name);
        jsonGenerator.writeBooleanField("active", parentJob.active);
        jsonGenerator.writeEndObject();
    }
}
