package com.yasirakbal.ordermanagementsystem.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateStockRequest {
    @NotNull
    @PositiveOrZero
    private Integer quantity;
}