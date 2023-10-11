package com.jackal.stockmanagement.productservice.service;

import com.jackal.stockmanagement.productservice.enums.Language;
import com.jackal.stockmanagement.productservice.repository.entitiy.Product;
import com.jackal.stockmanagement.productservice.request.ProductCreateRequest;
import com.jackal.stockmanagement.productservice.request.ProductUpdatedRequest;

import java.util.List;

public interface IProductRepositoryService {

    Product createProduct(Language language, ProductCreateRequest productCreateRequest);
    Product getProduct(Language language, Long productId);
    List<Product> getProducts(Language language);
    Product updateProduct(Language language, Long productId, ProductUpdatedRequest productUpdatedRequest);
    Product deleteProduct(Language language, Long productId);

}
