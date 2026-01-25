package com.yasirakbal.ordermanagementsystem.customer.dto;

import com.yasirakbal.ordermanagementsystem.common.dto.PaginationResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;


public class CustomerPaginationResponse extends PaginationResponse<CustomerResponse> {
    public CustomerPaginationResponse(Page page, List<CustomerResponse> response) {
        super(page, response);
    }
}
