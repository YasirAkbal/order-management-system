package com.yasirakbal.ordermanagementsystem.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategoryType {

    ELECTRONICS(
            "Electronics",
            "Electronic devices and accessories"
    ),

    CLOTHING(
            "Clothing",
            "Apparel and fashion items"
    ),

    FOOD(
            "Food & Beverages",
            "Food products and drinks"
    ),

    BOOKS(
            "Books",
            "Books and publications"
    ),

    HOME(
            "Home & Garden",
            "Home decor and garden supplies"
    ),

    TOYS(
            "Toys",
            "Children's toys and games"
    );

    private final String displayName;
    private final String description;
}