package com.pubsubdemo.inventory.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pubsubdemo.inventory.model.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    
    ProductStock findByProductId(Long productId);
}