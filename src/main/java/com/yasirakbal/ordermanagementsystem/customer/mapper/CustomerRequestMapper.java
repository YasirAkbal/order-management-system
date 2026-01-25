package com.yasirakbal.ordermanagementsystem.customer.mapper;

import com.yasirakbal.ordermanagementsystem.common.mapper.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.customer.dto.CustomerRequest;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper extends EntityDtoMapper<Customer, CustomerRequest> { }