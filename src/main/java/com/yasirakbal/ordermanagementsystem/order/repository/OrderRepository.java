package com.yasirakbal.ordermanagementsystem.order.repository;

import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Query("SELECT o.orderNumber FROM Order o WHERE o.orderNumber LIKE :pattern ORDER BY o.orderNumber DESC LIMIT 1")
    Optional<String> findLastOrderNumberByPattern(@Param("pattern") String pattern);

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.customer c " +
            "LEFT JOIN FETCH o.orderItems oi " +
            "LEFT JOIN FETCH oi.product p " +
            "WHERE o.id = :id")
    Optional<Order> findByIdWithDetails(@Param("id") Long id);
}
