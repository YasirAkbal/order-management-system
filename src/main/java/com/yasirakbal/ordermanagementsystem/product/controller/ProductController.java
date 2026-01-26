package com.yasirakbal.ordermanagementsystem.product.controller;

import com.yasirakbal.ordermanagementsystem.product.ProductCategoryType;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductPaginationResponse;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductRequest;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductResponse;
import com.yasirakbal.ordermanagementsystem.product.dto.UpdateStockRequest;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import com.yasirakbal.ordermanagementsystem.product.mapper.ProductRequestMapper;
import com.yasirakbal.ordermanagementsystem.product.mapper.ProductResponseMapper;
import com.yasirakbal.ordermanagementsystem.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path="/api/products")
@AllArgsConstructor
@Validated
public class ProductController {
    private ProductService productService;
    private ProductRequestMapper productRequestMapper;
    private ProductResponseMapper productResponseMapper;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        Product product = productRequestMapper.dtoToEntity(request);
        Product createdProduct = productService.createProduct(product);
        ProductResponse productResponse = productResponseMapper.entityToDto(createdProduct);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getUser(@PathVariable @Positive long id) {
        Product product = productService.findProduct(id);
        ProductResponse productResponse = productResponseMapper.entityToDto(product);

        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<ProductPaginationResponse> getAllProducts(
            // Filter parameters (all optional)
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) ProductCategoryType category,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,

            // Pagination & Sort parameters
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Page<Product> productPage = productService.getAllProducts(
                name, sku, minPrice, maxPrice, inStock, category, isActive, createdFrom, createdTo,
                page, size, sortBy, direction
        );

        List<ProductResponse> productResponses = productPage.stream().map(c -> productResponseMapper.entityToDto(c)).toList();
        ProductPaginationResponse paginationResponse = new ProductPaginationResponse(productPage, productResponses);

        return ResponseEntity.ok(paginationResponse);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Void> updateStockQuantity(@PathVariable @Positive long id,
                                                    @Valid @RequestBody UpdateStockRequest request) {
        productService.updateStockQuantity(id, request.getQuantity());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @Positive long id) {
        productService.deleteProduct(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
