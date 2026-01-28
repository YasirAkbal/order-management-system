package com.yasirakbal.ordermanagementsystem.order.controller;

import com.yasirakbal.ordermanagementsystem.order.dto.OrderRequest;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderResponse;
import com.yasirakbal.ordermanagementsystem.order.dto.UpdateOrderStatusRequest;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.mappers.OrderRequestMapper;
import com.yasirakbal.ordermanagementsystem.order.mappers.OrderResponseMapper;
import com.yasirakbal.ordermanagementsystem.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        return null;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @RequestParam @Positive long customerId,
            @RequestParam boolean status,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return null;
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable @Positive long id, @Valid @RequestBody UpdateOrderStatusRequest request) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable @Positive long id) {
        return null;
    }
}
