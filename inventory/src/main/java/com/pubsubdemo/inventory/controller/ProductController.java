package com.pubsubdemo.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(createdProduct);
    }

    @PutMapping("/updateProductPrice/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody double newPrice) {
        Product updatedProduct = productService.updateProductPrice(productId, newPrice);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }


    
    
}
