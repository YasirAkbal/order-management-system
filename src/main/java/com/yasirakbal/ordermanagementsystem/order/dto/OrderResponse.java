package com.yasirakbal.ordermanagementsystem.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;

    private String orderNumber;

    private OrderCustomerResponseView customer;

    private List<OrderItemResponseView> items;

    private String status;

    private BigDecimal totalAmount;

    private BigDecimal discountAmount;

    private BigDecimal finalAmount;

    private LocalDateTime orderDate;

    private String shippingAddress;
}

