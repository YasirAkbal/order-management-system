package com.yasirakbal.ordermanagementsystem.customer.dto;

import com.yasirakbal.ordermanagementsystem.common.dto.PaginationResponse;
import org.springframework.data.domain.Page;
import java.util.List;

public class CustomerPaginationResponse extends PaginationResponse<CustomerResponse> {
    public CustomerPaginationResponse(Page page, List<CustomerResponse> response) {
        super(page, response);
    }
}
