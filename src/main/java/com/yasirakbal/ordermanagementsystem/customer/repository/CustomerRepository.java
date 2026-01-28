package com.yasirakbal.ordermanagementsystem.customer.repository;

import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    boolean existsCustomersByEmail(String email);

    Optional<Customer> findById(long id);

    boolean existsById(long customerId);
}
