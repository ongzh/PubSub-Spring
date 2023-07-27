package com.pubsubdemo.inventory.model;

import lombok.Data;

@Data
public class OrderResponseDTO {
    
    private long orderId;
    private boolean isSuccessful;
    private String message;
    
}
