package com.pubsubdemo.order.model;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private int quantity;
}

