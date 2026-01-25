package com.yasirakbal.ordermanagementsystem.customer.dto;

import com.yasirakbal.ordermanagementsystem.customer.CustomerType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CustomerResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String country;

    private CustomerType customerType;

    private Boolean isActive;

    private LocalDateTime registeredAt;
}
