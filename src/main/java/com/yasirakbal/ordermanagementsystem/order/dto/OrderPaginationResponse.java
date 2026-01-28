package com.yasirakbal.ordermanagementsystem.order.dto;

import com.yasirakbal.ordermanagementsystem.common.dto.PaginationResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class OrderPaginationResponse extends PaginationResponse<OrderResponse> {
    public OrderPaginationResponse(Page page, List<OrderResponse> content) {
        super(page, content);
    }
}
