package com.pubsubdemo.inventory.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id 
    private String id;
    private String name;
    private double price;
    private String imageUrl;

    public Product(double price){
        this.price = Math.floor(price*100)/100;
    }

    public void setPrice(double price){
        this.price = Math.floor(price*100)/100;
    }
}