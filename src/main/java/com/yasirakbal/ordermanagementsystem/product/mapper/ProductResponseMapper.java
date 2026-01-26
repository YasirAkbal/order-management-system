package com.yasirakbal.ordermanagementsystem.product.mapper;

import com.yasirakbal.ordermanagementsystem.common.mapper.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductResponse;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper extends EntityDtoMapper<Product, ProductResponse> {
}
