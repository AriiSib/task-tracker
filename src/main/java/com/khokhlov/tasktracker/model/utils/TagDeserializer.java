package com.khokhlov.tasktracker.model.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.khokhlov.tasktracker.model.entity.Tag;

import java.io.IOException;

public class TagDeserializer extends JsonDeserializer<Tag> {

    @Override
    public Tag deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        String tagName = jsonParser.getText();
        return new Tag(tagName);
    }
}