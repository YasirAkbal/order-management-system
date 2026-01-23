package com.yasirakbal.ordermanagementsystem.customer.repository;

import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsCustomersByEmail(String email);
}
