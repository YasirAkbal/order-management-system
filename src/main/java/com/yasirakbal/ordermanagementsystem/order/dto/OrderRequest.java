package com.yasirakbal.ordermanagementsystem.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class OrderRequest {
    @NotNull
    @Positive
    private Long customerId;

    @NotBlank
    @Length(min = 15)
    private String address;

    @NotNull
    @Length(min = 1, max = 50)
    private List<OrderItemRequestView> items;

    private String notes;
}
