package com.pubsubdemo.inventory.model;


import lombok.Data;


@Data
public class OrderItemDTO {
    private Long productId;
    private int quantity;
}

