package com.yasirakbal.ordermanagementsystem.product.exception;

import com.yasirakbal.ordermanagementsystem.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuplicateSKUException extends BusinessException {
    public DuplicateSKUException() {
        super("Product SKU already exists.", HttpStatus.CONFLICT);
    }

    public DuplicateSKUException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
