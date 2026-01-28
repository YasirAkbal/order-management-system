package com.yasirakbal.ordermanagementsystem.customer.specification;

import com.yasirakbal.ordermanagementsystem.customer.enums.CustomerType;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> filterBy(
            String email,
            String firstName,
            String lastName,
            CustomerType customerType,
            Boolean isActive,
            String city,
            String country) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (email != null && !email.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        )
                );
            }

            if (firstName != null && !firstName.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("firstName")),
                                "%" + firstName.toLowerCase() + "%"
                        )
                );
            }

            if (lastName != null && !lastName.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("lastName")),
                                "%" + lastName.toLowerCase() + "%"
                        )
                );
            }

            if (customerType != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("customerType"), customerType)
                );
            }

            if (isActive != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("isActive"), isActive)
                );
            } else {
                predicates.add(
                        criteriaBuilder.equal(root.get("isActive"), true)
                );
            }

            if (city != null && !city.isBlank()) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("city")),
                                city.toLowerCase()
                        )
                );
            }

            if (country != null && !country.isBlank()) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("country")),
                                country.toLowerCase()
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}