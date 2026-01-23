package com.yasirakbal.ordermanagementsystem.product.entity;

import com.yasirakbal.ordermanagementsystem.order.entity.OrderItem;
import com.yasirakbal.ordermanagementsystem.product.ProductCategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 50, unique = true)
    private String sku;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ProductCategoryType category;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItem;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
