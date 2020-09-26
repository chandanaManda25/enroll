package com.health.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {
    ObjectMapper mapper = new ObjectMapper();

    public Object convert(Object from, Class<?> clazz) {
        try {
            return mapper.readValue(mapper.writeValueAsString(from), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
