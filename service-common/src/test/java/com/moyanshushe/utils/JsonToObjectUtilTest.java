package com.moyanshushe.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.moyanshushe.constant.DatePattern;
import com.moyanshushe.model.dto.item.ItemForUpdate;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Author: Napbad
 * Version: 1.0
 */
class JsonToObjectUtilTest {

    @Test
    void testJsonToObject() {
        ItemForUpdate itemForUpdate = JsonToObjectUtil.jsonToObject("{\n" +
                "  \"id\": 2,\n" +
                "  \"name\": \"name-nap\",\n" +
                "  \"price\": 180.00,\n" +
                "  \"description\": \"test-Napbad\",\n" +
                "  \"status\": 1,\n" +
                "  \"user\": {\n" +
                "    \"id\": 1\n" +
                "  },\n" +
                "  \"categories\": [\n" +
                "    {\n" +
                "      \"id\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"updatePerson\": {\n\"id\": 1 \n}" +
                "}", ItemForUpdate.class);

        System.out.println(itemForUpdate);
    }

    @Test
    void testJsonFileToObject() {
        ItemForUpdate itemForUpdate = JsonToObjectUtil.jsonFileToObject(new File("src/test/resources/json/ItemForUpdate.json"), ItemForUpdate.class);

        System.out.println(itemForUpdate);
    }

    @Test
    void testTimeFormat() throws JsonProcessingException {
//        String time = "2021-04-01 09:09:09";
//
//        ObjectMapper mapper = new ObjectMapper();
////        mapper.registerModule(new JavaTimeModule());
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        JavaTimeModule module = new JavaTimeModule();
//        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
//        mapper.registerModule(module);
//
//        LocalDateTime localDateTime = mapper.readValue(time, LocalDateTime.class);
//        System.out.println(localDateTime);
    }
}
