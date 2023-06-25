package com.pubsubdemo.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pubsubdemo.inventory.model.Product;
import com.pubsubdemo.inventory.service.ProductService;

@RestController
@RequestMapping("/inventory/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products =  productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/addProduct")
    public Object addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.ok(createdProduct);
    }
    
    
}
