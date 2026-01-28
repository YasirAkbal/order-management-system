package com.yasirakbal.ordermanagementsystem.order.exception;

import com.yasirakbal.ordermanagementsystem.common.exception.BusinessException;
import com.yasirakbal.ordermanagementsystem.order.enums.OrderStatus;
import org.springframework.http.HttpStatus;

public class OrderCannotBeCancelledException extends BusinessException {
    public OrderCannotBeCancelledException(long orderId, OrderStatus orderStatus) {
        super("Only the status of pending orders can be changed, the status of the order id = %d is %s.".formatted(orderId, orderStatus),
                HttpStatus.CONFLICT);
    }

    public OrderCannotBeCancelledException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
