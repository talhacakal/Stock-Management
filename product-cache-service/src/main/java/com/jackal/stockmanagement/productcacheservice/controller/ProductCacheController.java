package com.jackal.stockmanagement.productcacheservice.controller;

import com.jackal.stockmanagement.productcacheservice.enums.Language;
import com.jackal.stockmanagement.productcacheservice.repository.entity.Product;
import com.jackal.stockmanagement.productcacheservice.response.InternalApiResponse;
import com.jackal.stockmanagement.productcacheservice.response.ProductResponse;
import com.jackal.stockmanagement.productcacheservice.service.ProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/1.0/product-cache")
@RequiredArgsConstructor
public class ProductCacheController {

    private final ProductRepositoryService productRepositoryService;

    @GetMapping("/{language}/get/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InternalApiResponse<ProductResponse> getProduct(@PathVariable("language") Language language, @PathVariable("productId") Long productId){
        log.debug("[{}][getProduct] -> request productId: {}", this.getClass().getSimpleName(), productId);
        Product product = this.productRepositoryService.getProduct(language,productId);
        ProductResponse productResponse = convertProductResponse(product);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/removeProducts")
    public void removeProducts(@PathVariable("language") Language language){
        this.productRepositoryService.deleteProducts(language);
    }

    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .productCreatedDate(product.getProductCreatedDate())
                .productUpdatedDate(product.getProductUpdatedDate())
                .build();
    }

}
