package com.example.mediasoft.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Данный класс отвечает за конфигурацию Open API
 */
@OpenAPIDefinition
@Configuration
public class SpringDocConfig {
    /**
     * Метод конфигурирует заголовок и описание спецификации
     */
    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Склад - OpenAPI 3.0")
                        .version("1.0.11")
                        .description("""
                                CRUD-приложение для склада товаров. Данный проект позволяет:
                                                                    
                                - Получать товары по параметру.
                                                                    
                                - Изменять товары по параметру.
                                                                    
                                - Удалять товары по параметру.
                                                                    
                                - Создавать новые товары.
                                                                    
                                - Получить все товары.
                                                                    
                                - Удалить все товары."""));
    }
}