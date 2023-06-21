package com.pubsubdemo.inventory.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pubsubdemo.inventory.model.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, String> {

    @Query("SELECT ps FROM ProductStock ps WHERE ps.product.id = :productId")
    ProductStock findByProductId(String productId);
}