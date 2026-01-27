package com.yasirakbal.ordermanagementsystem.order.exception;

import com.yasirakbal.ordermanagementsystem.common.exception.BusinessException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class OrderValidationException extends BusinessException {

    @Getter
    private final List<OrderItemError> itemErrors;

    public OrderValidationException(List<OrderItemError> itemErrors) {
        super("Order validation failed", determineHttpStatus(itemErrors));
        this.itemErrors = itemErrors;
        addDetail("errorCount", itemErrors.size());
        addDetail("items", itemErrors);
    }

    @Data
    @Builder
    public static class OrderItemError {
        private Long productId;
        private String productName;
        private String errorCode;
        private String errorMessage;
        private Integer requestedQuantity;
        private Integer availableQuantity;
        private Map<String, Object> additionalInfo;
    }

    private static HttpStatus determineHttpStatus(List<OrderItemError> errors) {
        boolean hasNotFound = errors.stream()
                .anyMatch(e -> "PRODUCT_NOT_FOUND".equals(e.getErrorCode()));

        if (hasNotFound) {
            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.BAD_REQUEST;
    }
}