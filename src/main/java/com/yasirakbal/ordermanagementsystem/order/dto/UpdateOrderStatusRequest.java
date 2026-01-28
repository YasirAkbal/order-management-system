package com.yasirakbal.ordermanagementsystem.order.dto;

import com.yasirakbal.ordermanagementsystem.order.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    @NotBlank
    private OrderStatus status;
}
