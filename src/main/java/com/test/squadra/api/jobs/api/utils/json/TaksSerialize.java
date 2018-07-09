package com.test.squadra.api.jobs.api.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.test.squadra.api.jobs.api.models.Task;

import java.io.IOException;

public class TaksSerialize extends JsonSerializer<Task> {
    @Override
    public void serialize(Task task, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", task.id);
        jsonGenerator.writeStringField("name", task.name);
        jsonGenerator.writeNumberField("weight",task.weight);
        jsonGenerator.writeBooleanField("completed", task.completed);
        jsonGenerator.writeStringField("createdAt", task.createdAt.toString());
        jsonGenerator.writeEndObject();
    }
}
