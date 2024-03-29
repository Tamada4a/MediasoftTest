package com.example.mediasoft.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Данный класс содержит сообщение об ошибке
 */
@AllArgsConstructor
@Data
@Builder
public class ErrorDTO {
    /**
     * Сообщение об ошибке
     */
    private String message;
}
