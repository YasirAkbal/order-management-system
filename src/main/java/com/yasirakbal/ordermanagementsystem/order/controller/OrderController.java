package com.yasirakbal.ordermanagementsystem.order.controller;

import com.yasirakbal.ordermanagementsystem.order.dto.OrderPaginationResponse;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderRequest;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderResponse;
import com.yasirakbal.ordermanagementsystem.order.dto.UpdateOrderStatusRequest;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.enums.OrderStatus;
import com.yasirakbal.ordermanagementsystem.order.mappers.OrderRequestMapper;
import com.yasirakbal.ordermanagementsystem.order.mappers.OrderResponseMapper;
import com.yasirakbal.ordermanagementsystem.order.service.OrderService;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductPaginationResponse;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductResponse;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path="/api/orders")
@AllArgsConstructor
@Validated
public class OrderController {
    private OrderService orderService;
    private OrderRequestMapper orderRequestMapper;
    private OrderResponseMapper orderResponseMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        Order order = orderRequestMapper.dtoToEntity(request);
        Order createdOrder = orderService.createOrder(order);
        OrderResponse response = orderResponseMapper.entityToDto(createdOrder);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable @Positive long id) {
        Order order = orderService.getOrder(id);
        OrderResponse response = orderResponseMapper.entityToDto(order);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<OrderPaginationResponse> getOrders(
            @RequestParam(required = false) String orderNumber,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) BigDecimal totalAmountMin,
            @RequestParam(required = false) BigDecimal totalAmountMax,
            @RequestParam(required = false) BigDecimal finalAmountMin,
            @RequestParam(required = false) BigDecimal finalAmountMax,
            @RequestParam(required = false) LocalDateTime orderDateFrom,
            @RequestParam(required = false) LocalDateTime orderDateTo,
            @RequestParam(required = false) String shippingAddress,
            @RequestParam(required = false) String notes,

            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        Page<Order> orderPage = orderService.getAllOrders(
                orderNumber, status, totalAmountMin, totalAmountMax, finalAmountMin, finalAmountMax, orderDateFrom, orderDateTo,
                shippingAddress, notes, page, size, sortBy, direction
        );

        List<OrderResponse> orderResponses = orderPage.stream().map(c -> orderResponseMapper.entityToDto(c)).toList();
        OrderPaginationResponse paginationResponse = new OrderPaginationResponse(orderPage, orderResponses);

        return ResponseEntity.ok(paginationResponse);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable @Positive long id, @Valid @RequestBody UpdateOrderStatusRequest request) {
        orderService.changeOrderStatus(id, request.getStatus());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable @Positive long id) {
        orderService.cancelOrder(id);

        return ResponseEntity.noContent().build();
    }
}
