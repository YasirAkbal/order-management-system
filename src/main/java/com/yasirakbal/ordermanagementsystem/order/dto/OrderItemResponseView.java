package com.yasirakbal.ordermanagementsystem.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseView {
    private OrderItemProductResponseView product;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}
