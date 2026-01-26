package com.yasirakbal.ordermanagementsystem.order.mappers;

import com.yasirakbal.ordermanagementsystem.common.mapper.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.order.dto.OrderResponse;
import com.yasirakbal.ordermanagementsystem.order.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper extends EntityDtoMapper<Order, OrderResponse> {
}
