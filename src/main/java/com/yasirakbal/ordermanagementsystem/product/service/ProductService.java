package com.yasirakbal.ordermanagementsystem.product.service;

import com.yasirakbal.ordermanagementsystem.common.exception.ResourceNotFoundException;
import com.yasirakbal.ordermanagementsystem.product.ProductCategoryType;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import com.yasirakbal.ordermanagementsystem.product.exception.DuplicateSKUException;
import com.yasirakbal.ordermanagementsystem.product.exception.NegativeStockQuantityException;
import com.yasirakbal.ordermanagementsystem.product.repository.ProductRepository;
import com.yasirakbal.ordermanagementsystem.product.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product product) {
        if(productRepository.existsProductBySku(product.getSku())) {
            throw new DuplicateSKUException();
        }

        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findProduct(long id) {
        Optional<Product> productResult = productRepository.findById(id);

        return productResult.orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(
            String name,
            String sku,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            boolean inStock,
            ProductCategoryType category,
            boolean isActive,
            LocalDateTime createdFrom,
            LocalDateTime createdTo,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Specification<Product> spec = ProductSpecification.filterBy(
                name, sku, minPrice, maxPrice, inStock, category, isActive, createdFrom, createdTo
        );

        return productRepository.findAll(spec, pageable);
    }

    @Transactional
    public Product updateProduct(Product product) {
        long productId = product.getId();
        Product productToUpdate = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        if(!product.getSku().equals(productToUpdate.getSku())) {
            validateUniqueSku(
                    product.getSku(),
                    productId
            );
        }

        return productRepository.save(product);
    }

    @Transactional
    public void updateStockQuantity(long productId, int newStockQuantity) {
        Product productToUpdate = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        if(newStockQuantity < 0) {
            throw new NegativeStockQuantityException();
        }

        productToUpdate.setStockQuantity(newStockQuantity);

        productRepository.save(productToUpdate);
    }

    @Transactional
    public void deleteProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        product.setIsActive(false);

        productRepository.save(product);
    }

    private void validateUniqueSku(String sku, Long productId) {
        if (productRepository.existsBySkuAndIdNot(sku, productId)) {
            throw new DuplicateSKUException();
        }
    }
}
