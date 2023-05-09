package com.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;

public class Json {
    private static ObjectMapper objectMapper = defaultObjectMapper();

    public static ObjectMapper defaultObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper;
    }
    public static JsonNode parse(String jsonSrc)throws IOException {
        return objectMapper.readTree(jsonSrc);
    }
    public static <A> A fromJson(JsonNode node,Class<A>clazz)throws JsonProcessingException {
        return objectMapper.treeToValue(node,clazz);
    }
    public static JsonNode toJson(Object o)throws JsonProcessingException {
        return objectMapper.valueToTree(o);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node,false);
    }
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node,true);
    }
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if(pretty){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(o);
    }
    /**
     Update code with new version of Jackson
     **/
    public static <T> T readFile(String filePath,Class<T> clazz) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, clazz);
    }
}
