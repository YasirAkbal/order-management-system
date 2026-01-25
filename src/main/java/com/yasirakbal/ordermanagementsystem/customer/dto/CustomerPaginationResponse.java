package com.yasirakbal.ordermanagementsystem.customer.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class CustomerPaginationResponse {
    private List<CustomerResponse> content;
    private Integer totalElements;
    private Integer totalPages;
    private Integer pageSize;
    private Integer pageNumber;
    private Boolean isFirst;
    private Boolean isLast;
}
