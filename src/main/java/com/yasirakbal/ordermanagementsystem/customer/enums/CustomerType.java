package com.yasirakbal.ordermanagementsystem.customer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum CustomerType {

    STANDARD(
            "Standard",
            "Regular customer with no special benefits",
            BigDecimal.ZERO
    ),

    PREMIUM(
            "Premium",
            "VIP customer with 10% discount on all orders",
            new BigDecimal("0.10")
    );

    private final String displayName;
    private final String description;
    private final BigDecimal discountRate;

    public BigDecimal calculateDiscount(BigDecimal totalAmount) {
        return totalAmount.multiply(discountRate);
    }

    public boolean isPremium() {
        return this == PREMIUM;
    }
}
