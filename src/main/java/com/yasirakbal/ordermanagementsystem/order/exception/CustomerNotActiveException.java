package com.yasirakbal.ordermanagementsystem.order.exception;

import com.yasirakbal.ordermanagementsystem.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CustomerNotActiveException extends BusinessException {
    public CustomerNotActiveException(long customerId) {
        super("Customer with customer id = %d is inactive.".formatted(customerId), HttpStatus.CONFLICT);
    }

    public CustomerNotActiveException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
