package com.example.mediasoft.exception;

import org.springframework.http.HttpStatus;

/**
 * Данный класс используется при возникновении ошибок в ходе работы программы. Содержит в себе сообщение
 * ошибки, а также ее HTTP-код. Является расширением {@link RuntimeException}
 */
public class CustomException extends RuntimeException {
    /**
     * HTTP-код, с которым выбрасывается ошибка
     */
    private final HttpStatus code;


    /**
     * Конструктор исключения
     *
     * @param code    HTTP-код ошибки
     * @param message сообщение ошибки
     */
    public CustomException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }


    /**
     * Геттер для получения кода ошибки
     *
     * @return HTTP-код ошибки
     */
    public HttpStatus getCode() {
        return code;
    }
}
