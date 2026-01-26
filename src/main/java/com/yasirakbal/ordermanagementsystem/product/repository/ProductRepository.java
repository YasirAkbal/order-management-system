package com.yasirakbal.ordermanagementsystem.product.repository;

import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsProductBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, Long productId);
}
