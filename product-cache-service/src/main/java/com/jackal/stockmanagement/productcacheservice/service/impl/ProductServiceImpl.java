package com.jackal.stockmanagement.productcacheservice.service.impl;

import com.jackal.stockmanagement.productcacheservice.enums.Language;
import com.jackal.stockmanagement.productcacheservice.feign.product.ProductServiceFeignClient;
import com.jackal.stockmanagement.productcacheservice.repository.ProductRepository;
import com.jackal.stockmanagement.productcacheservice.repository.entity.Product;
import com.jackal.stockmanagement.productcacheservice.response.ProductResponse;
import com.jackal.stockmanagement.productcacheservice.service.ProductRepositoryService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductRepositoryService {

    private final ProductRepository productRepository;
    private final ProductServiceFeignClient productServiceFeignClient;

    @Override
    public Product getProduct(Language language, Long productId) {
        Product product;
        try{
            Optional<Product> optionalProduct = this.productRepository.findById(productId);
            if (optionalProduct.isPresent()){
                product = optionalProduct.get();
                log.info("Data came from redis");
            } else {
                log.info("Data came from product-service");
                product = new Product();
                ProductResponse response = productServiceFeignClient.getProduct(language, productId).getPayload();

                product.setProductId(response.getProductId());
                product.setProductName(response.getProductName());
                product.setPrice(response.getPrice());
                product.setQuantity(response.getQuantity());
                product.setProductCreatedDate(response.getProductCreatedDate());
                product.setProductUpdatedDate(response.getProductUpdatedDate());
                this.productRepository.save(product);

            }
        } catch (FeignException.FeignClientException.NotFound ex){
            throw new NotFoundException("Product not found for product id: " + productId);
        }
        return product;
    }

    @Override
    public void deleteProducts(Language language) {
        this.productRepository.deleteAll();
        log.info("Deleted All Product");
    }
}
