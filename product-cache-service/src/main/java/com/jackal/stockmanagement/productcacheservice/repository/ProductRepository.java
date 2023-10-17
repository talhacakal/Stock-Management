package com.jackal.stockmanagement.productcacheservice.repository;

import com.jackal.stockmanagement.productcacheservice.repository.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
