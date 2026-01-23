package com.yasirakbal.ordermanagementsystem.common.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends BusinessException {

    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ValidationException(String field, String reason) {
        super(
                String.format("Validation failed for field '%s': %s", field, reason),
                HttpStatus.BAD_REQUEST
        );
        addDetail("field", field);
        addDetail("reason", reason);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, cause);
    }
}