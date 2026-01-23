package com.yasirakbal.ordermanagementsystem.order.entity;

import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import com.yasirakbal.ordermanagementsystem.order.OrderStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatusType status = OrderStatusType.PENDING;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount = BigDecimal.ZERO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false, length = 250)
    private String shippingAddress = "";

    @Column(length = 150)
    private String notes = "";

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }
}
