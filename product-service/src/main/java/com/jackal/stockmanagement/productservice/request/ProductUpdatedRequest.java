package com.jackal.stockmanagement.productservice.request;

import lombok.Data;

@Data
public class ProductUpdatedRequest {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;

}
