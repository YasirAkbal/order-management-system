package com.yasirakbal.ordermanagementsystem.order.mappers;

import com.yasirakbal.ordermanagementsystem.common.mapper.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderItemResponseView;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderResponse;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import com.yasirakbal.ordermanagementsystem.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper extends EntityDtoMapper<Order, OrderResponse> {
    @Override
    @Mapping(target = "items", source = "orderItems")
    OrderResponse entityToDto(Order order);

    OrderItemResponseView toOrderItemResponse(OrderItem orderItem);
}
