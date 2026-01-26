package com.yasirakbal.ordermanagementsystem.product.mapper;

import com.yasirakbal.ordermanagementsystem.common.mapper.EntityDtoMapper;
import com.yasirakbal.ordermanagementsystem.product.dto.ProductResponse;
import com.yasirakbal.ordermanagementsystem.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductResponseMapper extends EntityDtoMapper<Product, ProductResponse> {
}
