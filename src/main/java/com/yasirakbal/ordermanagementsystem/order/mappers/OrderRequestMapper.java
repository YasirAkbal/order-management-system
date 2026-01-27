package com.yasirakbal.ordermanagementsystem.order.mappers;

import com.yasirakbal.ordermanagementsystem.common.mapper.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderItemRequestView;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderRequest;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper extends EntityDtoMapper<Order, OrderRequest> {
    @Override
    OrderRequest entityToDto(Order order);

    @Override
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "orderItems", source = "items")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "finalAmount", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    Order dtoToEntity(OrderRequest orderRequest);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    OrderItem toOrderItem(OrderItemRequestView itemView);
}
