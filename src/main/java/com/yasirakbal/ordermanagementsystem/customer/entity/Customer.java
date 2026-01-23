package com.yasirakbal.ordermanagementsystem.customer.entity;

import com.yasirakbal.ordermanagementsystem.customer.CustomerType;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 250)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerType customerType;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
    }
}