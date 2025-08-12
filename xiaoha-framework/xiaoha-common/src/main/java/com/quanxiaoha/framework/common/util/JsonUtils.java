package com.quanxiaoha.framework.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.quanxiaoha.framework.common.constants.DateConstants;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonUtils {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static{
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static void init(ObjectMapper objectMapper){
        OBJECT_MAPPER = objectMapper;
    }


    @SneakyThrows
    public static String toJsonString(Object object){
        return OBJECT_MAPPER.writeValueAsString(object);
    }


}
