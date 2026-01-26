package com.yasirakbal.ordermanagementsystem.product.specification;

import com.yasirakbal.ordermanagementsystem.product.ProductCategoryType;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> filterBy(
            String name,
            String sku,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            ProductCategoryType category,
            Boolean isActive,
            LocalDateTime createdFrom,
            LocalDateTime createdTo
    ) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(name != null && !name.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }

            if(sku != null && !sku.isBlank()) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("sku")),
                                sku.toLowerCase()
                        )
                );
            }

            if(minPrice != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("price"),
                                minPrice
                        )
                );
            }

            if(maxPrice != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("price"),
                                maxPrice
                        )
                );
            }

            if(inStock != null) {
                var inStockCriteria = inStock
                        ? criteriaBuilder.greaterThan(root.get("stockQuantity"), 0)
                        : criteriaBuilder.equal(root.get("stockQuantity"), 0);
                predicates.add(
                        inStockCriteria
                );
            }

            if(category != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("category"),
                                category
                        )
                );
            }

            if (isActive != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("isActive"), isActive)
                );
            }

            if (createdFrom != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                createdFrom
                        )
                );
            }

            if (createdTo != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("createdAt"),
                                createdTo
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
