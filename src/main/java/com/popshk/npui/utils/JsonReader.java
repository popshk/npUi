package com.popshk.npui.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;

public class JsonReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static JsonNode parseFile(String path) {
        return objectMapper.readTree(new File(path));
    }

    @SneakyThrows
    public static JsonNode parseResponse(String path) {
        return objectMapper.readTree(path);
    }

    @SneakyThrows
    public static JsonNode readJsonFile(Resource resource)  {
        return objectMapper.readTree(resource.getInputStream());
    }
}