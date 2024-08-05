package com.moyanshushe.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;

/*
 * Author: Napbad
 * Version: 1.0
 */
public class JsonToObjectUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DEFAULT_PATH = "src/test/resources/json/";
    private static final String SUFFIX = ".json";

    public static final TypeFactory JSON_TYPE_FACTORY = mapper.getTypeFactory();

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonFileToObject(String name, Class<T> clazz) {
        File file = new File(DEFAULT_PATH + name + SUFFIX);

        return jsonFileToObject(file, clazz);
    }

    public static <T> T jsonFileToObject(File file, Class<T> clazz) {
        if (!file.exists()) {
            return null;
        }

        try {
            return mapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonToObject(File file, Class<T> clazz) {
        if (!file.exists()) {
            return null;
        }

        try {

            return mapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static<T> T jsonFileToObject(String name, CollectionType collectionType) {
        File file = new File(DEFAULT_PATH + name + SUFFIX);

        try {
            return mapper.readValue(file, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
