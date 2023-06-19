package com.pubsubdemo.inventory.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pubsubdemo.inventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}