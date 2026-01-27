package com.yasirakbal.ordermanagementsystem.order.service;

import com.yasirakbal.ordermanagementsystem.common.exception.ResourceCollectionNotFoundException;
import com.yasirakbal.ordermanagementsystem.common.exception.ResourceNotFoundException;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import com.yasirakbal.ordermanagementsystem.customer.repository.CustomerRepository;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.entity.OrderItem;
import com.yasirakbal.ordermanagementsystem.order.exception.OrderValidationException;
import com.yasirakbal.ordermanagementsystem.order.repository.OrderRepository;
import com.yasirakbal.ordermanagementsystem.order.utils.OrderNumberGenerator;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import com.yasirakbal.ordermanagementsystem.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderNumberGenerator orderNumberGenerator;

    @Transactional
    public Order createOrder(Order order) {
        long customerId = order.getCustomer().getId();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", customerId));

        List<OrderValidationException.OrderItemError> validationErrors = validateOrderItems(order.getOrderItems());
        if(validationErrors.isEmpty()) {
            throw new OrderValidationException(validationErrors);
        }

        String orderCode = orderNumberGenerator.generate();

        return null;
    }

    private List<OrderValidationException.OrderItemError> validateOrderItems(List<OrderItem> orderItems) {
        List<OrderValidationException.OrderItemError> errors = new ArrayList<>();

        List<Long> productIds = orderItems.stream()
                .map(OrderItem::getId)
                .toList();

        Map<Long, Product> availableProductsMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        orderItems.forEach(item -> validateSingleOrderItem(item, availableProductsMap, errors));

        return errors;
    }

    private void validateSingleOrderItem(OrderItem orderItem, Map<Long, Product> availableProducts, List<OrderValidationException.OrderItemError> errors) {
        long productId = orderItem.getProduct().getId();
        Product product = availableProducts.get(productId);

        if(product == null) {
            errors.add(OrderValidationException.OrderItemError.builder()
                    .productId(productId)
                    .errorCode("PRODUCT_NOT_FOUND")
                    .errorMessage("Product not found")
                    .requestedQuantity(orderItem.getQuantity())
                    .build());
            return;
        }

        if(!product.getIsActive()) {
            errors.add(OrderValidationException.OrderItemError.builder()
                    .productId(productId)
                    .errorCode("PRODUCT_IS_NOT_ACTIVE")
                    .errorMessage("Product is not active")
                    .requestedQuantity(orderItem.getQuantity())
                    .build());
        }

        if(orderItem.getQuantity() > product.getStockQuantity()) {
            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("shortage", orderItem.getQuantity() - product.getStockQuantity());

            errors.add(OrderValidationException.OrderItemError.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .errorCode("INSUFFICIENT_STOCK")
                    .errorMessage("Insufficient stock for this product")
                    .requestedQuantity(orderItem.getQuantity())
                    .availableQuantity(product.getStockQuantity())
                    .additionalInfo(additionalInfo)
                    .build());
        }
    }
}
