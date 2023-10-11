package com.jackal.stockmanagement.productservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {

    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private Long productCreatedDate;
    private Long productUpdatedDate;

}
