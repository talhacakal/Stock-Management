package com.jackal.stockmanagement.productservice.service.impl;

import com.jackal.stockmanagement.productservice.enums.Language;
import com.jackal.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.jackal.stockmanagement.productservice.exception.exceotions.ProductAlreadyDeletedException;
import com.jackal.stockmanagement.productservice.exception.exceotions.ProductNotCreatedException;
import com.jackal.stockmanagement.productservice.exception.exceotions.ProductNotFoundException;
import com.jackal.stockmanagement.productservice.repository.ProductRepository;
import com.jackal.stockmanagement.productservice.repository.entitiy.Product;
import com.jackal.stockmanagement.productservice.request.ProductCreateRequest;
import com.jackal.stockmanagement.productservice.request.ProductUpdatedRequest;
import com.jackal.stockmanagement.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        try {
            Product product = Product.builder()
                    .productName(productCreateRequest.getProductName())
                    .quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice())
                    .deleted(false)
                    .build();
            Product productResponse = this.productRepository.save(product);
            log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        } catch (Exception ex) {
            throw new ProductNotCreatedException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION, "product request: "+productCreateRequest.toString());
        }
    }

    @Override
    public Product getProduct(Language language, Long productId) {
        log.debug("[{}][getProduct] -> request productId: {}", this.getClass().getSimpleName(), productId);
        Product product = this.productRepository.getByProductIdAndDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION, "Product not found for product id: "+productId));
        log.debug("[{}][getProduct] -> response: {}", this.getClass().getSimpleName(), product);
        return product;
    }

    @Override
    public List<Product> getProducts(Language language) {
        log.debug("[{}][updateProduct]", this.getClass().getSimpleName());
        var products = this.productRepository.getAllByDeletedFalse();
        if (products.isEmpty()) throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "Products not found");
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), products);
        return products;
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdatedRequest productUpdatedRequest) {
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productUpdatedRequest);
        var product = getProduct(language, productId);
        product.setProductName(productUpdatedRequest.getProductName());
        product.setQuantity(productUpdatedRequest.getQuantity());
        product.setPrice(productUpdatedRequest.getPrice());
        product.setProductUpdatedDate(new Date());
        var productResponse = this.productRepository.save(product);
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return productResponse;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        log.debug("[{}][deleteProduct] -> request productId: {}", this.getClass().getSimpleName(), productId);
        Product product;
        try {
            product = getProduct(language, productId);
            product.setDeleted(true);
            product.setProductUpdatedDate(new Date());
            var productResponse = this.productRepository.save(product);
            log.debug("[{}][deleteProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        } catch (ProductNotFoundException ex) {
            throw new ProductAlreadyDeletedException(language, FriendlyMessageCodes.PRODUCT_ALREADY_DELETED, "Product already deleted product id: " + productId);
        }
    }
}
