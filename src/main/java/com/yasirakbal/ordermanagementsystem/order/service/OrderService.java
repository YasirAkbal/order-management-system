package com.yasirakbal.ordermanagementsystem.order.service;

import com.yasirakbal.ordermanagementsystem.common.exception.ResourceNotFoundException;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import com.yasirakbal.ordermanagementsystem.customer.repository.CustomerRepository;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.entity.OrderItem;
import com.yasirakbal.ordermanagementsystem.order.enums.OrderStatus;
import com.yasirakbal.ordermanagementsystem.order.exception.CustomerNotActiveException;
import com.yasirakbal.ordermanagementsystem.order.exception.InvalidOrderStatusTransitionException;
import com.yasirakbal.ordermanagementsystem.order.exception.OrderCannotBeCancelledException;
import com.yasirakbal.ordermanagementsystem.order.exception.OrderValidationException;
import com.yasirakbal.ordermanagementsystem.order.repository.OrderRepository;
import com.yasirakbal.ordermanagementsystem.order.specification.OrderSpecification;
import com.yasirakbal.ordermanagementsystem.order.utils.OrderNumberGenerator;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import com.yasirakbal.ordermanagementsystem.product.repository.ProductRepository;
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

        if(!customer.getIsActive()) {
            throw new CustomerNotActiveException(customerId);
        }

        order.setCustomer(customer);

        List<OrderItem> orderItems = order.getOrderItems();
        List<Long> productIds = orderItems.stream()
                .map(orderItem -> orderItem.getProduct().getId())
                .toList();

        Map<Long, Product> availableProductsMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<OrderValidationException.OrderItemError> validationErrors = validateOrderItems(orderItems, availableProductsMap);
        if(!validationErrors.isEmpty()) {
            throw new OrderValidationException(validationErrors);
        }

        String orderCode = orderNumberGenerator.generate();
        BigDecimal totalAmount = orderItems.stream()
                .map(orderItem ->   {
                   Product product = availableProductsMap.get(orderItem.getProduct().getId());
                   return product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal customerTypeDiscountRate = customer.getCustomerType().getDiscountRate();
        BigDecimal orderPriceDiscountRate = totalAmount.compareTo(BigDecimal.valueOf(500)) > 0
                ? BigDecimal.valueOf(0.05)
                : BigDecimal.ZERO;
        BigDecimal totalDiscountRate = customerTypeDiscountRate.add(orderPriceDiscountRate);
        BigDecimal totalDiscountAmount = totalAmount.multiply(totalDiscountRate);

        BigDecimal finalAmount = totalAmount.subtract(totalDiscountAmount);

        orderItems.forEach(orderItem -> {
            long productId = orderItem.getProduct().getId();
            int quantity = orderItem.getQuantity();

            Product product = availableProductsMap.get(productId);
            int newStockQuantity = product.getStockQuantity() - quantity;
            product.setStockQuantity(newStockQuantity);
            productRepository.save(product);
        });

        order.setOrderNumber(orderCode);
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(totalDiscountAmount);
        order.setFinalAmount(finalAmount);

        orderItems.forEach(orderItem -> {
            long productId = orderItem.getProduct().getId();
            Product product = availableProductsMap.get(productId);

            orderItem.setUnitPrice(product.getPrice());
            orderItem.calculateSubtotal();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
        });

        return orderRepository.save(order);
    }

    private List<OrderValidationException.OrderItemError> validateOrderItems(List<OrderItem> orderItems,
                                                                             Map<Long, Product> availableProductsMap) {
        List<OrderValidationException.OrderItemError> errors = new ArrayList<>();

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

    @Transactional
    public Order getOrder(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }

    @Transactional(readOnly = true)
    public Page<Order> getAllOrders(
            String orderNumber,
            OrderStatus status,
            BigDecimal totalAmountMin,
            BigDecimal totalAmountMax,
            BigDecimal finalAmountMin,
            BigDecimal finalAmountMax,
            LocalDateTime orderDateFrom,
            LocalDateTime orderDateTo,
            String shippingAddress,
            String notes,
            Integer page,
            Integer size,
            String sortBy,
            String direction
    ) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Specification<Order> spec = OrderSpecification.filterBy(
                orderNumber, status, totalAmountMin, totalAmountMax, finalAmountMin, finalAmountMax, orderDateFrom,
                orderDateTo, shippingAddress, notes
        );

        return orderRepository.findAll(spec, pageable);
    }

    @Transactional
    public void changeOrderStatus(long orderId, OrderStatus newOrderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        if(!order.getStatus().canTransitionTo(newOrderStatus)) {
            throw new InvalidOrderStatusTransitionException(order.getStatus(), newOrderStatus);
        }

        if(newOrderStatus.equals(OrderStatus.CANCELLED)) {
            handleOrderCancellation(order);
        }

        order.setStatus(newOrderStatus);
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        if(!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new OrderCannotBeCancelledException(orderId, order.getStatus());
        }

        handleOrderCancellation(order);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public void handleOrderCancellation(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();

        orderItems.forEach(orderItem -> {
            Product product = orderItem.getProduct();
            int productStockQuantity = product.getStockQuantity();
            product.setStockQuantity(productStockQuantity + orderItem.getQuantity());
            productRepository.save(product);
        });
    }
}
