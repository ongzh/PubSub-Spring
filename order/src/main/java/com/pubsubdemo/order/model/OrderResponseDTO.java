package com.pubsubdemo.order.model;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private long orderId;
    private String orderStatus;
    
}
