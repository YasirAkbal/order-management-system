package com.yasirakbal.ordermanagementsystem.customer.exception;

import com.yasirakbal.ordermanagementsystem.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CustomerHasActiveOrdersException extends BusinessException {
    public CustomerHasActiveOrdersException(long customerId) {
        super("Customers with active orders cannot be deleted, customer id = %d".formatted(customerId),
                HttpStatus.BAD_REQUEST);
    }

    public CustomerHasActiveOrdersException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
