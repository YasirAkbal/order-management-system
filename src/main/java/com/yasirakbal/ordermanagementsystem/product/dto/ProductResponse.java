package com.yasirakbal.ordermanagementsystem.product.dto;

import com.yasirakbal.ordermanagementsystem.product.ProductCategoryType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private String sku;

    private BigDecimal price;

    private Integer stockQuantity;

    private ProductCategoryType category;

    private Boolean isActive;

    private LocalDateTime createdAt;
}
