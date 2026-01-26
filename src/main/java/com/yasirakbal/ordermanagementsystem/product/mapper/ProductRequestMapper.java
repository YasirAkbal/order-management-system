package com.yasirakbal.ordermanagementsystem.product.mapper;

import com.yasirakbal.ordermanagementsystem.common.mapper.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductRequest;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper extends EntityDtoMapper<Product, ProductRequest> {
}
