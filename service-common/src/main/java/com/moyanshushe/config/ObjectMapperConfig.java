package com.moyanshushe.config;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyanshushe.constant.DatePattern;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.moyanshushe.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.jackson.ImmutableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ImmutableModule());
        objectMapper.registerModule(javaTimeModule());
        try {
            objectMapper.writeValueAsString(new Result());
        } catch (JsonProcessingException e) {
            log.error("ObjectMapper init error", e);
        }
        return objectMapper;
    }


    private JavaTimeModule javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        module.addSerializer(new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        module.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        return module;
    }
}
