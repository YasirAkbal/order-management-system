package com.yasirakbal.ordermanagementsystem.customer.dto;

import com.yasirakbal.ordermanagementsystem.customer.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Data
@Getter
@Setter
public class CreateCustomerRequest {
    @NotBlank
    @Length(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    @Length(min = 10, max = 150)
    private String address;

    @NotBlank
    @Length(min = 2, max = 50)
    private String city;

    @NotBlank
    @Length(min = 2, max = 50)
    private String country;

    @NotNull
    private CustomerType customerType;
}
