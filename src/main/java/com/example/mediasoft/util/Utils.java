package com.example.mediasoft.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Данный класс содержит общий метод для тестирующих классов
 */
public class Utils {
    /**
     * Метод конвертирует переданный объект в JSON-строку
     *
     * @param obj объект, который необходимо конвертировать в JSON-строку
     * @return JSON-представление объекта
     * @throws RuntimeException Если не удается конвертировать объект в JSON-строку
     */
    public static String asJsonString(final Object obj) throws RuntimeException {
        try {
            ObjectMapper mapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
