package com.yasirakbal.ordermanagementsystem.customer.service;

import com.yasirakbal.ordermanagementsystem.common.exception.ValidationException;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import com.yasirakbal.ordermanagementsystem.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        if(customerRepository.existsCustomersByEmail(customer.getEmail())) {
            throw new ValidationException("Email already exists.");
        }

        return customerRepository.save(customer);
    }

}
