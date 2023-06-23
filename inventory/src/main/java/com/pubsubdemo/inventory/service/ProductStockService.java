package com.pubsubdemo.inventory.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubsubdemo.inventory.model.ProductStock;
import com.pubsubdemo.inventory.repository.ProductStockRepository;

@Service
public class ProductStockService {
    @Autowired
    private ProductStockRepository productStockRepository;



    public ProductStock updateProductStockByProductId(Long productId, Integer quantity) {
        ProductStock productStock = productStockRepository.findByProductId(productId);
        if (productStock == null) {
            throw new IllegalArgumentException("Product not found");
        }
        productStock.setQuantity(quantity);
        return productStockRepository.save(productStock);
    }

    public int getProductStockCount(Long productStockId){
        ProductStock productStock = productStockRepository.findById(productStockId).orElse(null);
        if (productStock == null) {
            throw new IllegalArgumentException("ProductStock not found");
        }
        return productStock.getQuantity();
    
    }

    public ProductStock createProductStock(ProductStock productStock) {
        return productStockRepository.save(productStock);
    }

}
