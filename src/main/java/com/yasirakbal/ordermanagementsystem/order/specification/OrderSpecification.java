package com.yasirakbal.ordermanagementsystem.order.specification;

import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.enums.OrderStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<Order> filterBy(
            String orderNumber,
            OrderStatus status,
            BigDecimal totalAmountMin,
            BigDecimal totalAmountMax,
            BigDecimal finalAmountMin,
            BigDecimal finalAmountMax,
            LocalDateTime orderDateFrom,
            LocalDateTime orderDateTo,
            String shippingAddress,
            String notes
    ) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(orderNumber != null && !orderNumber.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("orderNumber")),
                                "%" + orderNumber.toLowerCase() + "%"
                        )
                );
            }

            if(status != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("status"), status)
                );
            }

            if(totalAmountMin != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("totalAmount"), totalAmountMin)
                );
            }

            if(totalAmountMax != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("totalAmount"), totalAmountMax)
                );
            }

            if(finalAmountMin != null) {
                predicates.add(
                  criteriaBuilder.greaterThanOrEqualTo(root.get("finalAmount"), finalAmountMin)
                );
            }

            if(finalAmountMax != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("finalAmount"), finalAmountMax)
                );
            }

            if(orderDateFrom != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), orderDateFrom)
                );
            }

            if(orderDateTo != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("orderDate"), orderDateTo)
                );
            }

            if(shippingAddress != null && !shippingAddress.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("shippingAddress")),
                                "%" + shippingAddress.toLowerCase() + "%"
                        )
                );
            }

            if(notes != null && !notes.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("notes")),
                                "%" + notes.toLowerCase() + "%"
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
