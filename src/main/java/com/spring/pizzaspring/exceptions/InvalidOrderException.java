package com.spring.pizzaspring.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidOrderException extends AppException {
    public InvalidOrderException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
