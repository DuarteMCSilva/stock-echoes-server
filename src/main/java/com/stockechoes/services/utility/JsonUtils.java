package com.stockechoes.services.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;


public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static final <T> T fromJson(String jsonResponse, Class<T> type) {
        T values;

        try {
            values = objectMapper.readValue(jsonResponse, new TypeReference<T>() { });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid response format!");
        }

        return values;
    }
    public static final <T> List<T> listFromJson(String jsonResponse, Class<T> type) {
        List<T> values;

        try {
            values = objectMapper.readValue(jsonResponse, new TypeReference<List<T>>() { });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid response format!");
        }

        return values;
    }
}
