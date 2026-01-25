package com.yasirakbal.ordermanagementsystem.customer.controller;

import com.yasirakbal.ordermanagementsystem.customer.enums.CustomerType;
import com.yasirakbal.ordermanagementsystem.customer.dto.CustomerPaginationResponse;
import com.yasirakbal.ordermanagementsystem.customer.dto.CustomerRequest;
import com.yasirakbal.ordermanagementsystem.customer.mapper.CustomerRequestMapper;
import com.yasirakbal.ordermanagementsystem.customer.dto.CustomerResponse;
import com.yasirakbal.ordermanagementsystem.customer.mapper.CustomerResponseMapper;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import com.yasirakbal.ordermanagementsystem.customer.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/customers")
@AllArgsConstructor
@Validated
public class CustomerController {
    private CustomerService customerService;
    private CustomerRequestMapper customerRequestMapper;
    private CustomerResponseMapper customerResponseMapper;

    @PostMapping
    public ResponseEntity<CustomerResponse> createUser(@RequestBody @Valid CustomerRequest request) {
        Customer customer = customerRequestMapper.dtoToEntity(request);
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerResponse customerResponse = customerResponseMapper.entityToDto(createdCustomer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getUser(@PathVariable @Min(1) long id) {
        Customer customer = customerService.findCustomer(id);
        CustomerResponse customerResponse = customerResponseMapper.entityToDto(customer);

        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping
    public ResponseEntity<CustomerPaginationResponse> getAllCustomers(
            // Filter parameters (all optional)
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) CustomerType customerType,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,

            // Pagination & Sort parameters
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<Customer> customerPage = customerService.getAllCustomers(
                email, firstName, lastName, customerType, isActive, city, country,
                page, size, sortBy, direction
        );

        List<CustomerResponse> customerResponses = customerPage.stream().map(c -> customerResponseMapper.entityToDto(c)).toList();
        CustomerPaginationResponse paginationResponse = new CustomerPaginationResponse(customerPage, customerResponses);

        return ResponseEntity.ok(paginationResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateUser(@PathVariable @Min(1) long id, @RequestBody @Valid CustomerRequest request) {
        Customer customer = customerRequestMapper.dtoToEntity(request);
        customer.setId(id);

        Customer updatedCustomer = customerService.updateCustomer(customer);
        CustomerResponse customerResponse = customerResponseMapper.entityToDto(updatedCustomer);

        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateUser(@PathVariable @Min(1) long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
