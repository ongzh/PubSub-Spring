package com.pubsubdemo.order.model;


import lombok.Data;


@Data
public class OrderItemDTO {
    private Long productId;
    private int quantity;
}

