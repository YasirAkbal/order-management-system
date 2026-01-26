package com.yasirakbal.ordermanagementsystem.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateStockRequest {
    @NotNull
    @PositiveOrZero
    private Integer quantity;
}