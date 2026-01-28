package com.yasirakbal.ordermanagementsystem.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Set;
import java.util.Collections;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PENDING("Pending", "Order is waiting for confirmation"),
    CONFIRMED("Confirmed", "Order has been confirmed"),
    SHIPPED("Shipped", "Order has been shipped"),
    DELIVERED("Delivered", "Order has been delivered"),
    CANCELLED("Cancelled", "Order has been cancelled");

    private final String displayName;
    private final String description;

    public Set<OrderStatus> getAllowedTransitions() {
        return switch (this) {
            case PENDING -> Set.of(CONFIRMED, CANCELLED);
            case CONFIRMED -> Set.of(SHIPPED, CANCELLED);
            case SHIPPED -> Set.of(DELIVERED);
            case DELIVERED, CANCELLED -> Collections.emptySet();
        };
    }

    public boolean canTransitionTo(OrderStatus target) {
        return getAllowedTransitions().contains(target);
    }

    public boolean isFinalState() {
        return getAllowedTransitions().isEmpty();
    }

    public boolean isActiveOrder() {
        return this.equals(PENDING) || this.equals(CONFIRMED);
    }
}