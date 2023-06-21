package com.pubsubdemo.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_stock")

public class ProductStock {

    
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private int quantity;
    
    
}
