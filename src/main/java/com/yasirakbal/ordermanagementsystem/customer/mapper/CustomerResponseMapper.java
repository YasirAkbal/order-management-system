package com.yasirakbal.ordermanagementsystem.customer.mapper;

import com.yasirakbal.ordermanagementsystem.common.dto.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.customer.dto.CustomerResponse;
import com.yasirakbal.ordermanagementsystem.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper extends EntityDtoMapper<Customer, CustomerResponse> { }
