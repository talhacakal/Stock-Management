package com.jackal.stockmanagement.productservice.repository;

import com.jackal.stockmanagement.productservice.repository.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> getByProductIdAndDeletedFalse(Long productId);
    List<Product> getAllByDeletedFalse();

}
