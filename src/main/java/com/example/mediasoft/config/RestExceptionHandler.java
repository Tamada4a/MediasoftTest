package com.example.mediasoft.config;

import com.example.mediasoft.exception.CustomException;
import com.example.mediasoft.exception.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Глобальный обработчик ошибок. Ошибка заворачивается в
 * ResponseEntity и возвращается пользователю с указанием сообщения и кодом ошибки
 */
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlerException(CustomException exception) {
        return ResponseEntity.status(exception.getCode())
                .body(ErrorDTO.builder().message(exception.getMessage()).build());
    }
}
