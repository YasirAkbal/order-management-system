package com.yasirakbal.ordermanagementsystem.product.exception;

import com.yasirakbal.ordermanagementsystem.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class NegativeStockQuantityException extends BusinessException {
    public NegativeStockQuantityException() {
        super("Product stock quantity must be positive", HttpStatus.CONFLICT);
    }

    public NegativeStockQuantityException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
