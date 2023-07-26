package com.pubsubdemo.inventory.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDTO{
    private String productName;
    private double price;
    private MultipartFile image;
    
}
