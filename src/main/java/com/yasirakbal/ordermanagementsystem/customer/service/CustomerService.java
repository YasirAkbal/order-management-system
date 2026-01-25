package com.yasirakbal.ordermanagementsystem.customer.service;

import com.yasirakbal.ordermanagementsystem.common.exception.ResourceNotFoundException;
import com.yasirakbal.ordermanagementsystem.common.exception.ValidationException;
import com.yasirakbal.ordermanagementsystem.customer.CustomerType;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import com.yasirakbal.ordermanagementsystem.customer.exception.DuplicateEmailException;
import com.yasirakbal.ordermanagementsystem.customer.repository.CustomerRepository;
import com.yasirakbal.ordermanagementsystem.customer.specification.CustomerSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        if(customerRepository.existsCustomersByEmail(customer.getEmail())) {
            throw new DuplicateEmailException("Customer email already exists.");
        }

        return customerRepository.save(customer);
    }

    public Customer findCustomer(long id) {
        Optional<Customer> customerResponse = customerRepository.findById(id);

        return customerResponse.orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }

    @Transactional(readOnly = true)
    public Page<Customer> getAllCustomers(
            String email,
            String firstName,
            String lastName,
            CustomerType customerType,
            Boolean isActive,
            String city,
            String country,
            int page,
            int size,
            String sortBy,
            String direction) {


        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Specification<Customer> spec = CustomerSpecification.filterBy(
                email, firstName, lastName, customerType, isActive, city, country
        );

        return customerRepository.findAll(spec, pageable);
    }
}
