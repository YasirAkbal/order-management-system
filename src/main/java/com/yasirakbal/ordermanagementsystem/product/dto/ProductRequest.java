package com.yasirakbal.ordermanagementsystem.product.dto;

import com.yasirakbal.ordermanagementsystem.product.ProductCategoryType;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;

@Data
@Getter
@Setter
public class ProductRequest {
    @NotBlank
    @Length(min = 3, max = 100)
    private String name;

    @NotBlank
    @Length(min = 10, max = 1000)
    private String description;

    @NotBlank
    private String sku;

    @NotNull
    @Positive
    @Digits(
            integer = 10,
            fraction = 2,
            message = "Amount must have max 2 decimal places"
    )
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Integer stockQuantity = 0;

    @NotNull
    private ProductCategoryType category;
}
