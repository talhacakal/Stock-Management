package com.jackal.stockmanagement.productcacheservice.service;

import com.jackal.stockmanagement.productcacheservice.enums.Language;
import com.jackal.stockmanagement.productcacheservice.repository.entity.Product;

public interface ProductRepositoryService {

    Product getProduct(Language language, Long productId);
    void deleteProducts(Language language);

}
