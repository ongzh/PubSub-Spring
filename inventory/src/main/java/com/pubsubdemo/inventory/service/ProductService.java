package com.pubsubdemo.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pubsubdemo.inventory.model.Product;
import com.pubsubdemo.inventory.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll(Sort.by("price"));
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }
    

    
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product updateProductPrice(Long productId, double newPrice) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        product.setPrice(newPrice);
        return productRepository.save(product);
        
    }
}
