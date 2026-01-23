package com.yasirakbal.ordermanagementsystem.customer.controller;

import com.yasirakbal.ordermanagementsystem.customer.dto.CreateCustomerRequest;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import com.yasirakbal.ordermanagementsystem.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/customers")
@AllArgsConstructor
public class CustomerController {
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateCustomerRequest request) {
        Customer customer = Customer.builder()
                        .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                                .email(request.getEmail())
                                        .phone(request.getPhone())
                                                .address(request.getAddress())
                                                        .city(request.getCity())
                                                                .country(request.getCountry())
                                                                        .customerType(request.getCustomerType())
                                                                                .build();
        customer = customerService.createCustomer(customer);

        return ResponseEntity.ok("Success, customer id = " + customer.getId());
    }

}
