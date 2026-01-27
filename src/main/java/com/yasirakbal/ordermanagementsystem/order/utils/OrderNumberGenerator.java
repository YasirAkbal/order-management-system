package com.yasirakbal.ordermanagementsystem.order.utils;

import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderNumberGenerator {

    private final OrderRepository orderRepository;

    public String generate() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int sequence = getNextSequenceForToday();
        String sequencePart = String.format("%04d", sequence);

        return "ORD-" + datePart + "-" + sequencePart;
    }

    private int getNextSequenceForToday() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String pattern = "ORD-" + today + "-%";

        Optional<String> latestOrderNumber = orderRepository.findLastOrderNumberByPattern(pattern);

        return latestOrderNumber.map(s -> extractSequenceNumber(s) + 1).orElse(1);

    }

    private int extractSequenceNumber(String orderNumber) {
        String sequence = orderNumber.substring(orderNumber.lastIndexOf("-") + 1);
        return Integer.parseInt(sequence);
    }
}